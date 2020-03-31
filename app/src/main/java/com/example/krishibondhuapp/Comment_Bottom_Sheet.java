package com.example.krishibondhuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.util.ArrayList;
import java.util.List;

public class Comment_Bottom_Sheet extends BottomSheetDialogFragment {

    ImageView sendCommentButton;
    EditText commentEditText;

    CommentAdapter commentAdapter;
    List<CommentPosoClass> listItem;
    RecyclerView recyclerView;

    String bal;
    String proPic,proName;


    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    @Override
    public void onCreate(@javax.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comment_bottom_sheet, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();

        recyclerView = view.findViewById(R.id.commentRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        
        listItem = new ArrayList<>();

        commentEditText = view.findViewById(R.id.commentFixET);
        sendCommentButton = view.findViewById(R.id.sentCommentId);

        Bundle bundle = getArguments();
        bal = bundle.getString("key");

//        Bundle mArgs = getArguments();
//        myValue = mArgs.getString("key");


        //showComments();

        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmnt = commentEditText.getText().toString();

                CommentPosoClass commentPosoClass = new CommentPosoClass(proPic,proName,cmnt);

                if (!cmnt.equals("")){
                    FirebaseDatabase.getInstance().getReference().child("Comments").child(bal).push().setValue(commentPosoClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "You commented successfully !", Toast.LENGTH_SHORT).show();
                                commentEditText.setText("");
                                showComments();
                            }else {
                                Toast.makeText(getContext(), "Something wrong !", Toast.LENGTH_SHORT).show();
                            }
                            
                        }
                    });

                }else {
                    Toast.makeText(getContext(), "Type comment first !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    public void showComments(){

        FirebaseDatabase.getInstance().getReference().child("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String key = snapshot.getKey();
                    if (key.equals(bal)){
                        listItem.clear();
                        for (DataSnapshot targetSnapshot: snapshot.getChildren()){

                            CommentPosoClass commentData = targetSnapshot.getValue(CommentPosoClass.class);

                            listItem.add(commentData);
                        }
                    }
//                    Toast.makeText(getContext(), "Comments key : "+key, Toast.LENGTH_SHORT).show();
                }

                commentAdapter = new CommentAdapter(listItem,getContext());
                recyclerView.smoothScrollToPosition(listItem.size());
                commentAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(commentAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "No Comments", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        showComments();
        loadSettings();
    }




    private void loadSettings() {
        FirebaseDatabase.getInstance().getReference().child("Farmer").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    FarmerRegisterPosoClass data = dataSnapshot.getValue(FarmerRegisterPosoClass.class);

                    proPic = data.getfImageurl();
                    proName = data.getfName();


                }else{
                    Toast.makeText(getContext(), "No data added !", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Database Error"+databaseError, Toast.LENGTH_SHORT).show();

            }
        });


    }



}
