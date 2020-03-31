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
public class FlowerFragments extends Fragment {

    ImageView imageView;
    JustifiedTextView textView;


    public FlowerFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_flower_fragments, container, false);
        imageView = view.findViewById(R.id.floweritemImageId);
        textView = view.findViewById(R.id.floweritemTextViewId);

        int value = getArguments().getInt("position");
        if (value == 0) {
            imageView.setImageResource(R.drawable.rose);
            textView.setText(R.string.golap);
        }
        if (value == 1) {
            imageView.setImageResource(R.drawable.joba);
            textView.setText(R.string.joba);
        }
        if (value == 2) {
            imageView.setImageResource(R.drawable.gadha_ful);
            textView.setText(R.string.gadha);
        }
        if (value == 3) {
            imageView.setImageResource(R.drawable.arkid);
            textView.setText(R.string.arkid);
        }
//        }if (value==4){
//            imageView.setImageResource(R.drawable.papaya);
//            textView.setText(R.string.pepe);
//        }

        return view;
    }
}
