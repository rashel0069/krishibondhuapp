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
public class BuyingProductsFragment extends Fragment {
    FloatingActionButton fab;

    BuyingAdapter buyingAdapter;
    List<BuyingPosoClass> buyingListItem;
    RecyclerView recyclerView;


    public BuyingProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__buying_profucts, container, false);

        recyclerView = view.findViewById(R.id.buyingRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        buyingListItem = new ArrayList<>();

        fab = view.findViewById(R.id.buy_fabButtonId);

        displayPost();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialogFragment bottomSheetDialogFragment = new BuyingProductBottomSheet();
                bottomSheetDialogFragment.show(((FragmentActivity)getContext()).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });

        return view;
    }

    private void displayPost() {

        FirebaseDatabase.getInstance().getReference().child("Buying Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String data = "";
                buyingListItem.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                    String userNumber = itemSnapshot.getKey();

                    for (DataSnapshot item : itemSnapshot.getChildren()) {


                        BuyingPosoClass buyingPosoClass = item.getValue(BuyingPosoClass.class);

                        buyingListItem.add(buyingPosoClass);
                    }


                }

                buyingAdapter = new BuyingAdapter(buyingListItem,getContext());

                buyingAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(buyingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error Found to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
