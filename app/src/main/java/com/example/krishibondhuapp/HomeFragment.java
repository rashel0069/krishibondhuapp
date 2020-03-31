package com.example.krishibondhuapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    TextView writeHereTextView;
    CircleImageView circleImageView;
    FloatingActionButton fab;

    MyAdapter myAdapter;
    List<PostPosoClass> listItem;
    RecyclerView recyclerView;

    String proPic,proName;

    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

//        circleImageView = view.findViewById(R.id.nfeegImV);
        fab = view.findViewById(R.id.home_fabButtonId);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();

        displayPost();


//        writeHereTextView = view.findViewById(R.id.feedTapToWriteTV);

        recyclerView = view.findViewById(R.id.recyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItem = new ArrayList<>();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AudioPost.class);
                startActivity(intent);


//                BottomSheetDialogFragment bottomSheetDialogFragment = new PostBottomSheet();
//                bottomSheetDialogFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
//
            }
        });


        return view;
    }

    private void displayPost() {

        firebaseDatabase.getInstance().getReference().child("Post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listItem.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){

                    for (DataSnapshot item : itemSnapshot.getChildren()) {


                        PostPosoClass postData = item.getValue(PostPosoClass.class);

                        String postUniqueKey = item.getKey();

                        listItem.add(postData);
                    }


                }

                myAdapter = new MyAdapter(listItem,getContext());

                myAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Found to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
