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
public class Mosola_Fragment extends Fragment {
    ImageView imageView;
    JustifiedTextView textView;


    public Mosola_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.fragment_mosola_, container, false);

        imageView = view.findViewById(R.id.mosolaitemImageId);
        textView = view.findViewById(R.id.mosolaitemTextViewId);

        int value = getArguments().getInt("position");
        if (value == 0) {
            imageView.setImageResource(R.drawable.jeera);
            textView.setText(R.string.jira);
        }
        if (value == 1) {
            imageView.setImageResource(R.drawable.elach);
            textView.setText(R.string.alach);
        }
        if (value == 2) {
            imageView.setImageResource(R.drawable.daruchini);
            textView.setText(R.string.daruchini);
        }
        if (value == 3) {
            imageView.setImageResource(R.drawable.holud);
            textView.setText(R.string.holud);
        }

      return view;
    }
}
