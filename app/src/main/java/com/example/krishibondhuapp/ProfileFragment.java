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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    TextView changePic;
    TextView fNameEditText,fNidEditText;
    TextView fPhoneEditText;
    CircleImageView circleImageView;
    Button updateProfileButton;
    Uri uri;
    String imageUrl;

    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

       //changePic = view.findViewById(R.id.image_change_id);
        circleImageView = view.findViewById(R.id.fProfileImageId);
        fNameEditText = view.findViewById(R.id.fNameEditTextId);
        fPhoneEditText = view.findViewById(R.id.fPhoneEditTextId);
        fNidEditText = view.findViewById(R.id.fNidEditTextId);

        updateProfileButton = view.findViewById(R.id.fRegisterButtonId);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();



        loadSettings();



//        changePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent photoPicker = new Intent(Intent.ACTION_PICK);
//                photoPicker.setType("image/*");
//                startActivityForResult(photoPicker,1);
//            }
//        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fNameEditText.getText().toString();
                String phone = fNameEditText.getText().toString();
                String nid = fNameEditText.getText().toString();

                if (!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(phone)&& !TextUtils.isEmpty(nid)){

//                    uploadImages();
                    Toast.makeText(getContext(), "Profile information initially fixed!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getContext(), "Enter Profile Information !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void uploadProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Profile Updating.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String name = fNameEditText.getText().toString();
        String phone = fPhoneEditText.getText().toString();
        String nid = fNidEditText.getText().toString();

        FarmerRegisterPosoClass fposoclass  = new FarmerRegisterPosoClass(name,phone,nid,imageUrl);

        FirebaseDatabase.getInstance().getReference("Farmer")
                .child(user_id).setValue(fposoclass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Upload Profile done !", Toast.LENGTH_SHORT).show();
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
    }

    private void uploadImages() {
        if (uri != null){
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("Image").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Image Uploading.....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            UploadTask image_path = storageReference.putFile(uri);
            image_path.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadProfile();
                    progressDialog.dismiss();

                    Intent intent = new Intent(getContext(), DrawerActivity.class);
                    startActivity(intent);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            uri = data.getData();
            circleImageView.setImageURI(uri);
        }else {
            Toast.makeText(getContext(), "You haven't picked images", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSettings() {
        firebaseDatabase.getInstance().getReference().child("Farmer").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    FarmerRegisterPosoClass data = dataSnapshot.getValue(FarmerRegisterPosoClass.class);

                    String proPic = data.getfImageurl();
                    String name = data.getfName();
                    String phone  = data.getfPhone();
                    String nid = data.getfNid();

                    Glide.with(getContext()).load(proPic).into(circleImageView);
                    fNameEditText.setText(name);
                    fPhoneEditText.setText(phone);
                    fNidEditText.setText(nid);

                    HomeFragment homeFragment = new HomeFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("Name",name);

                    homeFragment.setArguments(bundle);




                }else{
//                    Toast.makeText(getContext(), "Profile not updated ! !", Toast.LENGTH_SHORT).show();
                    fPhoneEditText.setText(user_id);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error"+databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }

}
