package com.example.krishibondhuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class NewsFeedActivity extends AppCompatActivity {

    Button imageButton, voiceRecordButtonId;
    ImageView imageView;
    Uri imageURI = null;
    Button postButton;

    ImageView proPic;
    TextView proName;

    FirebaseDatabase firebaseDatabase;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // news feed
        setContentView(R.layout.activity_news_feed);
////        imageButton = findViewById(R.id.imageLoadButtonId);
////        voiceRecordButtonId = findViewById(R.id.voiceRecordButtonId);
////        imageView = findViewById(R.id.showImgNewsfeedId);
////        postButton = findViewById(R.id.feedPostButtonId);
//
//        voiceRecordButtonId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(NewsFeedActivity.this, "this is image load button", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
////        voiceRecordButtonId.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////           Toast.makeText(NewsFeedActivity.this,"this is voice record button clicked",Toast.LENGTH_LONG).show();
////
////                if (checkPermission()) {
////
////                    AudioSavePathInDevice =
////                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
////                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";
////
////                    ShowPopup(view);
////
////
////                } else {
////                    requestPermission();
////                }
////            }
////        });
//
//        loadProfile();
//
//        postButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(NewsFeedActivity.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
////        imageButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                Toast.makeText(NewsFeedActivity.this, "this is image load button", Toast.LENGTH_SHORT).show();
////                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                    if (ContextCompat.checkSelfPermission(NewsFeedActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
////                        Toast.makeText(NewsFeedActivity.this, "Permission Denied !", Toast.LENGTH_SHORT).show();
////                        ActivityCompat.requestPermissions(NewsFeedActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
////                    } else {
////
////                        Toast.makeText(NewsFeedActivity.this, "Clicked !", Toast.LENGTH_SHORT).show();
////                    }
////                } else {
////
////                    Toast.makeText(NewsFeedActivity.this, "Clicked else..... !", Toast.LENGTH_SHORT).show();
////
////                }
////            }
////        });
//    }
//
//    private void loadProfile() {
//        firebaseDatabase.getInstance().getReference().child("Farmer").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
//                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
//                        FarmerRegisterPosoClass data = itemSnapshot.getValue(FarmerRegisterPosoClass.class);
//
//                        String pic = data.getfImageurl();
//                        String name = data.getfName();
//
//
////                        Toast.makeText(DrawerActivity.this, "Pic :"+pic +"\n"+"name :"+name, Toast.LENGTH_SHORT).show();
//                        Glide.with(NewsFeedActivity.this).load(pic).into(proPic);
//                        proName.setText(name);
//                    }
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Something wrong !", Toast.LENGTH_SHORT).show();
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }
}