package com.example.krishibondhuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    MyAdapter myAdapter;
    List<PostPosoClass> listItem;
    RecyclerView recyclerView;

    String user_id;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getPhoneNumber();

        displayPost();

        recyclerView = view.findViewById(R.id.historyRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listItem = new ArrayList<>();

        return view;
    }

    private void displayPost() {
        
        if (!user_id.isEmpty()){
            firebaseDatabase.getInstance().getReference().child("Post").child(user_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data = "";
                    listItem.clear();
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){

                        PostPosoClass postData = itemSnapshot.getValue(PostPosoClass.class);

                        listItem.add(postData);

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
        }else {
            Toast.makeText( getContext(), "your Current Status as a Admin...To see your history login as a user ", Toast.LENGTH_LONG ).show();
        }

        
    }
}
