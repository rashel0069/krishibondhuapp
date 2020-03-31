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
public class MathfosolItemFragment extends Fragment {

    ImageView imageView;
    JustifiedTextView textView;

    public MathfosolItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mathfosol_item, container, false);

        imageView = view.findViewById(R.id.itemImageId);
        textView = view.findViewById(R.id.itemTextViewId);

        int value = getArguments().getInt("position");
        if (value==0){
            imageView.setImageResource(R.drawable.dhan);
            textView.setText(R.string.dhan);
        }if (value==1){
            imageView.setImageResource(R.drawable.jute);
            textView.setText(R.string.pat);
        }if (value==2){
            imageView.setImageResource(R.drawable.gom);
            textView.setText(R.string.gom);
        }if (value==3){
            imageView.setImageResource(R.drawable.vutta);
            textView.setText(R.string.vutta);
        }if (value==4){
            imageView.setImageResource(R.drawable.sorisa);
            textView.setText(R.string.sorisha);
        }


        return view;
    }
}
