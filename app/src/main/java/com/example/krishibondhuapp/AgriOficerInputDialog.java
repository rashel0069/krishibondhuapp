package com.example.krishibondhuapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import static android.app.Activity.RESULT_OK;

public class AgriOficerInputDialog extends DialogFragment {

    ImageView imageView;
    EditText nameEditText,designationEditText,locationEditText,phoneEditText;
    Button saveButton;

    Uri uri;
    String imageUrl;


    static AgriOficerInputDialog newInstance() {
        return new AgriOficerInputDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);

    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agri_officer_inputscreen_dialog, container, false);

        imageView = view.findViewById(R.id.agri_officer_ImageViewId);
        nameEditText = view.findViewById(R.id.agriofficerNameId);
        designationEditText = view.findViewById(R.id.agri_officerDesignation_Id);
        locationEditText = view.findViewById(R.id.agri_officerLocation_Id);
        phoneEditText = view.findViewById(R.id.agriofficer_PhoneId);
        saveButton = view.findViewById(R.id.agriofficer_SaveButton_Id);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker,1);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImages();
            }
        });


        return view;
    }

    private void uploadImages() {
        if (uri != null){
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("Agriculture Officer").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Uploading.....");
            progressDialog.show();

            UploadTask image_path = storageReference.putFile(uri);
            image_path.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadPost();
                    progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }else {
            Toast.makeText(getContext(), "Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPost() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Info Uploading.....");
        progressDialog.show();

        String name = nameEditText.getText().toString();
        String designation = designationEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(designation) && !TextUtils.isEmpty(location) && !TextUtils.isEmpty(phone) ) {

            AgriOfficerPosoClass agriPosoClass = new AgriOfficerPosoClass(imageUrl, name, designation, location, phone);

            FirebaseDatabase.getInstance().getReference("Agriculture Officer").push().setValue(agriPosoClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Upload info done !", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed !", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            nameEditText.setError("Enter Name");
            designationEditText.setError("Enter Designation");
            locationEditText.setError("Enter Location");
            phoneEditText.setError("Enter Phone");
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            uri = data.getData();
            imageView.setImageURI(uri);
        }else {
            Toast.makeText(getContext(), "You haven't picked images", Toast.LENGTH_SHORT).show();
        }
    }


}
