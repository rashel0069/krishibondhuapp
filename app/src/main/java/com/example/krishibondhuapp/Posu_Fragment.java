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
public class Posu_Fragment extends Fragment {

    ImageView imageView;
    JustifiedTextView textView;


    public Posu_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posu_, container, false);

        imageView = view.findViewById(R.id.posuitemImageId);
        textView = view.findViewById(R.id.posuitemTextViewId);

        int value = getArguments().getInt("position");
        if (value == 0) {
            imageView.setImageResource(R.drawable.cowone);
            textView.setText(R.string.goru);
        }
        if (value == 1) {
            imageView.setImageResource(R.drawable.sagol);
            textView.setText(R.string.chagol);
        }
        if (value == 2) {
            imageView.setImageResource(R.drawable.mohish);
            textView.setText(R.string.mohish);
        }
        if (value == 3) {
            imageView.setImageResource(R.drawable.hash);
            textView.setText(R.string.hash);
        }
        if (value == 4) {
            imageView.setImageResource(R.drawable.chicken);
            textView.setText(R.string.murgi);
        }


        return view;
    }
}
