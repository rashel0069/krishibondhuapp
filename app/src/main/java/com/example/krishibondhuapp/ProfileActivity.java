package com.example.krishibondhuapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProfileActivity extends AppCompatActivity {
    TextView changePic;
    EditText fNameEditText, fPhoneEditText, fNidEditText;
    CircleImageView circleImageView;
    Button updateProfileButton;
    String name, phone, nid;
    Uri uri;
    String imageUrl;

    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;

    int checker = 2;
    public static final String KEY = "KEY";
    public static final String USER_PREF = "USER_PREF";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        changePic = findViewById(R.id.fclick_tochange_picid2);
        circleImageView = findViewById(R.id.fprofile_image_id);
        fNameEditText = findViewById(R.id.fname_editText_Id);
        fPhoneEditText = findViewById(R.id.fPhone_EditText_Id);
        fNidEditText = findViewById(R.id.fNid_EditText_Id);

        updateProfileButton = findViewById(R.id.fRegister_Button_Id);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();


//        loadSettings();
        fPhoneEditText.setText(user_id);

        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, 1);
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = fNameEditText.getText().toString();
                phone = fNameEditText.getText().toString();
                nid = fNameEditText.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(nid)) {

                    uploadImages();
                    sharePrefValueUpdate();

                } else {
                    Toast.makeText(ProfileActivity.this, "প্রোফাইল তথ্য প্রদান করুন", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void uploadProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("আপলোড হচ্ছে...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String name = fNameEditText.getText().toString();
        String phone = fPhoneEditText.getText().toString();
        String nid = fNidEditText.getText().toString();

        FarmerRegisterPosoClass fposoclass = new FarmerRegisterPosoClass(name, phone, nid, imageUrl);

        FirebaseDatabase.getInstance().getReference("Farmer")
                .child(user_id).setValue(fposoclass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "প্রোফাইল আপলোড হয়েছে", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, "বাতিল ", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void uploadImages() {
        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Image").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("আপলোড হচ্ছে...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            UploadTask image_path = storageReference.putFile(uri);
            image_path.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    uploadProfile();
                    progressDialog.dismiss();

                    Intent intent = new Intent(ProfileActivity.this, DrawerActivity.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileActivity.this, "বাতিল ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(ProfileActivity.this, "ছবি প্রদান করুন ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            circleImageView.setImageURI(uri);
        } else {
            Toast.makeText(ProfileActivity.this, "ছবি প্রদান করা হয়নি", Toast.LENGTH_SHORT).show();
        }
    }

//    private void loadSettings() {
//        firebaseDatabase.getInstance().getReference().child("Farmer").child(user_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if (dataSnapshot.exists()) {
//
//                    FarmerRegisterPosoClass data = dataSnapshot.getValue(FarmerRegisterPosoClass.class);
//
//                    String proPic = data.getfImageurl();
//                    String name = data.getfName();
//                    String phone = data.getfPhone();
//                    String nid = data.getfNid();
//
//                    Glide.with(ProfileActivity.this).load(proPic).into(circleImageView);
//                    fNameEditText.setText(name);
//                    fPhoneEditText.setText(phone);
//                    fNidEditText.setText(nid);
//
//                    HomeFragment homeFragment = new HomeFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("Name", name);
//
//                    homeFragment.setArguments(bundle);
//
//
//                } else {
//                    Toast.makeText(ProfileActivity.this, "সংরক্ষন হয়নি ", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(ProfileActivity.this, "বাতিল ", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//    }

    public void sharePrefValueUpdate() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, checker);
        editor.commit();
    }
}
