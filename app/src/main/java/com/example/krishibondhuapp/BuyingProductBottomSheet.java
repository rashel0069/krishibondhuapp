package com.example.krishibondhuapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class BuyingProductBottomSheet extends BottomSheetDialogFragment {

    Uri uri;
    String imageUrl;
    String saveCurrentDate, saveCurrentTime;

    EditText quantityEditText, priceEditText, jatEditText, placeNameEditText;
    Button pickImageButton, postButton;
    ImageView previewImage;

    Spinner categorySpinner, productnameSpinner, unitSpinner;
    String[] category, productName, unit;

    String user_id;
    String userPic, userName;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buying_products_bottom_sheet, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();


        pickImageButton = view.findViewById(R.id.buyingpick_Images_ButtonId);
        postButton = view.findViewById(R.id.buying_PostButton_Id);
        previewImage = view.findViewById(R.id.imagePreviewId);
        quantityEditText = view.findViewById(R.id.buyingQuantityEtId);
        priceEditText = view.findViewById(R.id.buyingPriceEtId);
        jatEditText = view.findViewById(R.id.buyingJatEtId);
        placeNameEditText = view.findViewById(R.id.buyingPlaceName);

        categorySpinner = view.findViewById(R.id.buyingCategorySpinnerId);
        productnameSpinner = view.findViewById(R.id.buyingProductNameSpinnerId);
        unitSpinner = view.findViewById(R.id.buyingUnitSpinnerId);

        category = getResources().getStringArray(R.array.foshol);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>( getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, category );
        categorySpinner.setAdapter(categoryAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Position :" + i, Toast.LENGTH_SHORT).show();

                if (i == 0) {
                    productName = getResources().getStringArray(R.array.mathfosol);
                    ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, productName);
                    productnameSpinner.setAdapter(productNameAdapter);

                    unit = getResources().getStringArray(R.array.quantity1);
                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unit);
                    unitSpinner.setAdapter(unitAdapter);

                } else if (i == 1) {
                    productName = getResources().getStringArray(R.array.fols);
                    ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, productName);
                    productnameSpinner.setAdapter(productNameAdapter);

                    unit = getResources().getStringArray(R.array.quantity1);
                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unit);
                    unitSpinner.setAdapter(unitAdapter);

                } else if (i == 2) {
                    productName = getResources().getStringArray(R.array.fuls);
                    ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, productName);
                    productnameSpinner.setAdapter(productNameAdapter);

                    unit = getResources().getStringArray(R.array.quantity2);
                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unit);
                    unitSpinner.setAdapter(unitAdapter);

                } else if (i == 3) {
                    productName = getResources().getStringArray(R.array.saksobji);
                    ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, productName);
                    productnameSpinner.setAdapter(productNameAdapter);

                    unit = getResources().getStringArray(R.array.quantity1);
                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unit);
                    unitSpinner.setAdapter(unitAdapter);

                } else if (i == 4) {
                    productName = getResources().getStringArray(R.array.moshla);
                    ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, productName);
                    productnameSpinner.setAdapter(productNameAdapter);

                    unit = getResources().getStringArray(R.array.quantity1);
                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unit);
                    unitSpinner.setAdapter(unitAdapter);

                } else {
                    productName = getResources().getStringArray(R.array.home_pets_animals);
                    ArrayAdapter<String> productNameAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, productName);
                    productnameSpinner.setAdapter(productNameAdapter);

                    unit = getResources().getStringArray(R.array.quantity1);
                    ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, unit);
                    unitSpinner.setAdapter(unitAdapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, 1);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!quantityEditText.equals("") && !priceEditText.equals("") && !jatEditText.equals("") && !placeNameEditText.equals("") && uri != null) {
                    uploadImages();
                } else {
                    Toast.makeText(getContext(), "Check your all field and pic image... !", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            previewImage.setImageURI(uri);
        } else {
            Toast.makeText(getContext(), "You haven't picked images", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImages() {
        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("Buying Image").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Image Uploading.....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            UploadTask image_path = storageReference.putFile(uri);
            image_path.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadPost();
                    progressDialog.dismiss();

                    postButton.setEnabled(false);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPost() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Post Uploading.....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        String postkeyid = FirebaseDatabase.getInstance().getReference().child("Buying Products").child(user_id).push().getKey();

        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currenTime.format(calForTime.getTime());


        String pCategory = categorySpinner.getSelectedItem().toString();
        String pName = productnameSpinner.getSelectedItem().toString();
        String pUnit = unitSpinner.getSelectedItem().toString();
        String pQntity = quantityEditText.getText().toString();
        String pPrice = priceEditText.getText().toString();
        String pJat = jatEditText.getText().toString();
        String sPlace = placeNameEditText.getText().toString();

        SellingPosoClass sellingPosoClass = new SellingPosoClass(pCategory, pName, pUnit, pQntity, pPrice, pJat, sPlace, imageUrl, saveCurrentDate, saveCurrentTime, userPic, userName, postkeyid, user_id);

        FirebaseDatabase.getInstance().getReference("Buying Products")
                .child(user_id).child(postkeyid).setValue(sellingPosoClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Upload Post done !", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStart() {
        super.onStart();

        loadInfo();
    }

    private void loadInfo() {
        firebaseDatabase.getInstance().getReference().child("Farmer").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    FarmerRegisterPosoClass data = dataSnapshot.getValue(FarmerRegisterPosoClass.class);

                    userPic = data.getfImageurl();
                    userName = data.getfName();

                } else {
                    Toast.makeText(getContext(), "No data added !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error" + databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }


}
