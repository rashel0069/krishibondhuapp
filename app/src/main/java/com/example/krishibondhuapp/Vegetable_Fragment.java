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
public class Vegetable_Fragment extends Fragment {

    ImageView imageView;
    JustifiedTextView textView;

    public Vegetable_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_vegetable, container, false);

      imageView=view.findViewById(R.id.vegetableitemImageId);
      textView=view.findViewById(R.id.vegetableitemTextViewId);
        int value = getArguments().getInt("position");
        if (value==0){
            imageView.setImageResource(R.drawable.seem);
            textView.setText(R.string.shim);
        }if (value==1){
            imageView.setImageResource(R.drawable.alu);
            textView.setText(R.string.alu);
        }if (value==2){
            imageView.setImageResource(R.drawable.begun);
            textView.setText(R.string.begun);
        }if (value==3){
            imageView.setImageResource(R.drawable.tomato);
            textView.setText(R.string.tomato);
        }
      return view;
    }
}
