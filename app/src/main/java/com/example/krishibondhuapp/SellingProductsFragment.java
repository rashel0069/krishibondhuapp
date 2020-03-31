package com.example.krishibondhuapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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
public class SellingProductsFragment extends Fragment {

    FloatingActionButton fab;

    SellingAdapter sellingAdapter;
    List<SellingPosoClass> sellingListItem;
    RecyclerView recyclerView;

    FirebaseDatabase firebaseDatabase;


    public SellingProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_selling, container, false);

        recyclerView = view.findViewById(R.id.sellingRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sellingListItem = new ArrayList<>();

        fab = view.findViewById(R.id.sell_fabButtonId);

        displayPost();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialogFragment bottomSheetDialogFragment = new SellingProductBottomSheet();
                bottomSheetDialogFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });

        return view;
    }

    private void displayPost() {

        FirebaseDatabase.getInstance().getReference().child("Selling Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data = "";
                sellingListItem.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    String userNumber = itemSnapshot.getKey();

                    for (DataSnapshot item : itemSnapshot.getChildren()) {


                        SellingPosoClass sellingPosoClass = item.getValue(SellingPosoClass.class);

                        sellingListItem.add(sellingPosoClass);
                    }


                }

                sellingAdapter = new SellingAdapter(sellingListItem, getContext());

                sellingAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(sellingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Found to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
