package com.example.krishibondhuapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.krishibondhuapp.R.layout.cropsdetails_sample2;


public class CropsCategoryAdapter extends ArrayAdapter {

    private Context context;
    private String[] foshol;
    private int[] flags;
    public CropsCategoryAdapter(Context context, String[] foshol, int[] flags) {
        super(context, cropsdetails_sample2,foshol);

        this.context=context;
        this.foshol=foshol;
        this.flags=flags;

    }
    @SuppressWarnings("unchecked")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate( cropsdetails_sample2,parent,false);

        ImageView imageView = view.findViewById(R.id.imageViewId);
        TextView textView = view.findViewById(R.id.textViewId);

        imageView.setImageResource(flags[position]);
        textView.setText(foshol[position]);

        return view;
    }
}
