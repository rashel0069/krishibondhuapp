package com.example.krishibondhuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//
//import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    CircleImageView profileImageView;
    TextView profileNameTextView;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String user_id;
    String pic, name;
    int checker = 1;
    public static final String KEY = "KEY";
    String userExterNumber;

    public static final String USER_PREF = "USER_PREF";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

//        Bundle newbundel = getIntent().getExtras();
//        if (newbundel!=null){
//            userExterNumber = newbundel.getString( "UserMobile" );
//        }

        drawerLayout = findViewById(R.id.drawerLayoutId);
        navigationView = findViewById(R.id.nav_viewId);
        View nabView = navigationView.inflateHeaderView(R.layout.drawer_header);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profileImageView = nabView.findViewById(R.id.proPicId);
        profileNameTextView = nabView.findViewById(R.id.proNameId);
//        ProfileImageView = findViewById(R.id.ProPicId);
//        ProfileNameTextView = findViewById(R.id.ProNameId);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth != null) {
            user_id = firebaseAuth.getCurrentUser().getPhoneNumber();
        } else {
            Toast.makeText(this, "অনুমোদন করা হয়নি", Toast.LENGTH_SHORT).show();
        }
        loadProfile();

//        if (firebaseAuth != null) {
//            if (!firebaseAuth.getCurrentUser().getPhoneNumber().isEmpty()){
//
//            }else {
//                    user_id = userExterNumber;
//                     loadProfile();
//            }
//        } else {
//            Toast.makeText(this, "অনুমোদন করা হয়নি", Toast.LENGTH_SHORT).show();
//        }

        if (sp.contains(KEY)) {

            int SPValue = sp.getInt(KEY, 0);
            Toast.makeText(this, "স্বাগতম", Toast.LENGTH_SHORT).show();
            if (SPValue == checker) {
                checkDialog();
            } else {
                Toast.makeText(this, "স্বাগতম", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "পরিচয় পাওয়া যায়নি", Toast.LENGTH_SHORT).show();
        }




        TabFragment tabFragment = new TabFragment();
        loadFragment(tabFragment);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.profileItemId:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.homeItemId:
                        fragment = new TabFragment();
                        break;
                    case R.id.cropDetailsIemId:
                        fragment = new CropsCategoryFragment();
                        break;
                    case R.id.aoItemId:
                        fragment = new Info_of_Agriculture_Officer();
                        break;
                    case R.id.hospitalItemId:
                        fragment = new Info_of_vetenary_hospital();
                        break;
                    case R.id.historyId:
                        fragment = new HistoryFragment();
                        break;
                    case R.id.noticeId:
                        fragment = new Announcement();
                        break;
                    case R.id.logout_id:
                        firebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;


                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayoutId, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }

                //close navigation drawer
//        drawerLayout.closeDrawer(GravityCompat.START);
//        return true;
                menuItem.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void loadProfile() {
        firebaseDatabase.getInstance().getReference().child("Farmer").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    FarmerRegisterPosoClass data = dataSnapshot.getValue(FarmerRegisterPosoClass.class);

                    pic = data.getfImageurl();
                    name = data.getfName();

                    Glide.with(DrawerActivity.this).load(pic).into(profileImageView);
                    profileNameTextView.setText(name);


                } else {
                    Toast.makeText(DrawerActivity.this, "প্রোফাইল আপডেট হয়নি", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DrawerActivity.this, "তথ্য পাওয়া যায়নি", Toast.LENGTH_SHORT).show();

            }
        });


//        if (firebaseAuth != null) {
//            user_id = firebaseAuth.getCurrentUser().getPhoneNumber();
//            firebaseDatabase.getInstance().getReference().child("Farmer").child(user_id).addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    if (dataSnapshot.exists()) {
//
//                        FarmerRegisterPosoClass data = dataSnapshot.getValue(FarmerRegisterPosoClass.class);
//
//                        pic = data.getfImageurl();
//                        name = data.getfName();
//
//                        Glide.with(DrawerActivity.this).load(pic).into(profileImageView);
//                        profileNameTextView.setText(name);
//
//
//                    } else {
//                        Toast.makeText(DrawerActivity.this, "প্রোফাইল আপডেট হয়নি", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(DrawerActivity.this, "তথ্য পাওয়া যায়নি", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        } else {
//            Toast.makeText(this, "অনুমোদন করা হয়নি", Toast.LENGTH_SHORT).show();
//        }

    }


//    private void setPropic() {
//        Glide.with(this).load(pic).into(ProfileImageView);
//        ProfileNameTextView.setText(name);
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutId, fragment);
        fragmentTransaction.commit();

    }


    public void checkDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DrawerActivity.this);
        builder.setTitle("দয়া করে প্রোফাইল সংযুক্ত করুন" + "\n" + "অন্যথায় আপনি শুধু পোষ্ট দেখতে পাবেন। ");


        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Intent intent = new Intent(DrawerActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}