package com.example.krishibondhuapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Info_of_Agriculture_Officer extends Fragment {
    FloatingActionButton fabbutton;

    AgriOfficerAdapter agriOfficerAdapter;
    List<AgriOfficerPosoClass> agriOfficerListItem;
    RecyclerView recyclerView;


    public Info_of_Agriculture_Officer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_of__agriculture__officer, container, false);


        recyclerView = view.findViewById(R.id.agriOfficerListViewid);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        agriOfficerListItem = new ArrayList<>();

        displayPost();

        fabbutton = view.findViewById( R.id.fabButtonId );
        fabbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment dialog = AgriOficerDialog.newInstance();
                dialog.show(getFragmentManager(), "tag");
            }
        });


        return view;
    }

    private void displayPost() {

        FirebaseDatabase.getInstance().getReference().child("Agriculture Officer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data = "";
                agriOfficerListItem.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){

                        AgriOfficerPosoClass agriOfficerPosoClass = itemSnapshot.getValue(AgriOfficerPosoClass.class);

                        agriOfficerListItem.add(agriOfficerPosoClass);

                }

                agriOfficerAdapter = new AgriOfficerAdapter(agriOfficerListItem,getContext());

                agriOfficerAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(agriOfficerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Found to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
