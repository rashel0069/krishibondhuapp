package com.example.krishibondhuapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AudioPost extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {
    ImageButton pickImage, voiceRecordButtonId;
    Button postButton;
    EditText pickText;
    ImageView showImage;
    TextView audioUrI;
    Uri uri;
    String imageUrl;
    String userPic, userName;
    String saveCurrentDate, saveCurrentTime;
    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    public static final int RequestPermissionCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_post);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();
        //myDialog = new Dialog(getContext());
//        Bundle bundle = getArguments();
//        String string = bundle.getString("getURIResult");

        pickText = findViewById(R.id.writehere_edittext_id);
        pickImage = findViewById(R.id.imageload_buttonid);
        showImage = findViewById(R.id.show_imgnewsfeed_id);
        audioUrI = findViewById(R.id.voice_upoload);
        postButton = findViewById(R.id.feedpostbuttonid);
        voiceRecordButtonId = findViewById(R.id.voicerecordbuttonid);

        voiceRecordButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //needPermission();

              needPermission();
            }
        });

        //image upload
        pickImage.setOnClickListener(new View.OnClickListener() {
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
                String text = pickText.getText().toString();

                if (!TextUtils.isEmpty(text) && uri != null) {

                    uploadImages();

                } else {
                    Toast.makeText(getApplicationContext(), "Write your problem or pick image or give voice.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void needPermission() {

        Dexter.withActivity(this).withPermissions(READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE,RECORD_AUDIO,CALL_PHONE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                openDialog();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog() ;
        exampleDialog.setCancelable(false);
        exampleDialog.show(getSupportFragmentManager(),"example dialog");
    }
    @Override
    public void applyText(Uri audioURL) {
        audioUrI.setText(audioURL.toString());
    }

    private void uploadImages() {
        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("Post Image").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Image Uploading.....");
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
                    uploadPost();
                    progressDialog.dismiss();

                    Intent intent = new Intent(AudioPost.this, DrawerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Select Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPost() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Post Uploading.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String audioCheck = String.valueOf(audioUrI.getText());
        if (audioCheck.isEmpty()){
            audioCheck = "No Audio File";
        }
        String postkeyid = FirebaseDatabase.getInstance().getReference().child("Post").child(user_id).push().getKey();

        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currenTime.format(calForTime.getTime());


        String text = pickText.getText().toString();

        PostPosoClass fposoclass = new PostPosoClass(text, imageUrl, userName, userPic, saveCurrentDate, saveCurrentTime, postkeyid,audioCheck);

        FirebaseDatabase.getInstance().getReference("Post")
                .child(user_id).child(postkeyid).setValue(fposoclass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Upload Post done !", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failed !", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            showImage.setImageURI(uri);

        } else {
            Toast.makeText(getApplicationContext(), "You haven't picked images", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onStart() {
        super.onStart();

        loadInfo();

        //loadData();

//        if (getArguments() != null){
//            audioUrl = getArguments().getString("URIText");
////            audioUrI.setText(audioUrl);
//            Toast.makeText(getContext(), audioUrl, Toast.LENGTH_SHORT).show();
//
//        }else {
//            Toast.makeText(getContext(), audioUrl, Toast.LENGTH_SHORT).show();
//        }
//        audioUrI.setText(audioUrl);

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
                    Toast.makeText(getApplicationContext(), "No data added !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Error" + databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }


//    private void requestPermission() {
//        ActivityCompat.requestPermissions(this, new
//                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case RequestPermissionCode:
//                if (grantResults.length > 0) {
//                    boolean StoragePermission = grantResults[0] ==
//                            PackageManager.PERMISSION_GRANTED;
//                    boolean RecordPermission = grantResults[1] ==
//                            PackageManager.PERMISSION_GRANTED;
//
//                    if (StoragePermission && RecordPermission) {
//                        Toast.makeText(getApplicationContext(), "Permission Granted",
//                                Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//        }
//    }
//
//    public boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(this,
//                WRITE_EXTERNAL_STORAGE);
//        int result1 = ContextCompat.checkSelfPermission(this,
//                RECORD_AUDIO);
//        return result == PackageManager.PERMISSION_GRANTED &&
//                result1 == PackageManager.PERMISSION_GRANTED;
//    }
}

