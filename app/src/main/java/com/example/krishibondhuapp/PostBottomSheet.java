package com.example.krishibondhuapp;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

public class PostBottomSheet extends BottomSheetDialogFragment {

    ImageButton pickImage, voiceRecordButtonId;
    Button postButton;
    EditText pickText;
    ImageView showImage;
    TextView audioUrI;
    Uri uri;
    String imageUrl,audioUrl;
    String userPic, userName;
    String saveCurrentDate, saveCurrentTime;
    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseDatabase postkeyRefference;

    /////MEDIA RECORD

    Dialog myDialog;
    int i = 0;
    Button start, stop, play;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "VOICE_RECORD_SAVE";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_bottom_sheet, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();
        myDialog = new Dialog(getContext());
//        Bundle bundle = getArguments();
//        String string = bundle.getString("getURIResult");
        random = new Random();
        pickText = view.findViewById(R.id.writeHereEditTextId2);
        pickImage = view.findViewById(R.id.imageLoadButtonId2);
        showImage = view.findViewById(R.id.showImgNewsfeedId2);
        audioUrI=view.findViewById(R.id.voice_upoload2);
//        audioUrI.setText(string);
        voiceRecordButtonId = view.findViewById(R.id.voiceRecordButtonId2);

        //getAudi


//////media voice record button
        voiceRecordButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "this is voice record button", Toast.LENGTH_SHORT).show();
                if (checkPermission()) {
//                    openDialog();

                } else {
                    requestPermission();
                }

            }

        });


        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                startActivityForResult(photoPicker, 1);
            }
        });

        postButton = view.findViewById(R.id.feedPostButtonId2);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = pickText.getText().toString();

                if (!TextUtils.isEmpty(text) && uri != null) {

                    uploadImages();

                } else {
                    Toast.makeText(getContext(), "Enter Title and Recipe Description", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }




    private void uploadImages() {
        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance()
                    .getReference().child("Post Image").child(uri.getLastPathSegment());

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
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

                    Intent intent = new Intent(getContext(), HomeFragment.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String postkeyid = FirebaseDatabase.getInstance().getReference().child("Post").child(user_id).push().getKey();

        Calendar calFordate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currenTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currenTime.format(calForTime.getTime());


        String text = pickText.getText().toString();

        PostPosoClass fposoclass = new PostPosoClass(text, imageUrl, userName, userPic, saveCurrentDate, saveCurrentTime, postkeyid,audioUrl);

        FirebaseDatabase.getInstance().getReference("Post")
                .child(user_id).child(postkeyid).setValue(fposoclass).addOnCompleteListener(new OnCompleteListener<Void>() {
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            uri = data.getData();
            showImage.setImageURI(uri);

        } else {
            Toast.makeText(getContext(), "You haven't picked images", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "No data added !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error" + databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) getContext(), new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getContext(), "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

//    public void loadData(){
//        SharedPreferences preferences = getContext().getSharedPreferences("saveCounter",MODE_PRIVATE);
//        audioUrl = preferences.getString("Value", String.valueOf(MODE_PRIVATE));
//        audioUrI.setText(audioUrl);
//    }

}
