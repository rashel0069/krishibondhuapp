package com.example.krishibondhuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.codesgood.views.JustifiedTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FruitsFragments extends Fragment {
    ImageView imageView;
    JustifiedTextView textView;

    public FruitsFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_fruits_fragments, container, false);

       imageView=view.findViewById(R.id.fruitsitemImageId);
       textView=view.findViewById(R.id.fruitsitemTextViewId);

        int value = getArguments().getInt("position");
        if (value==0){
            imageView.setImageResource(R.drawable.aam);
            textView.setText(R.string.aam);
        }if (value==1){
            imageView.setImageResource(R.drawable.kathal);
            textView.setText(R.string.kathal);
        }if (value==2){
            imageView.setImageResource(R.drawable.lichu);
            textView.setText(R.string.lichu);
        }if (value==3){
            imageView.setImageResource(R.drawable.orange);
            textView.setText(R.string.komola);
        }if (value==4){
            imageView.setImageResource(R.drawable.papaya);
            textView.setText(R.string.pepe);
        }


        return view;
    }
}
