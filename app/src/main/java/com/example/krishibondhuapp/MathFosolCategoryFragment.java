package com.example.krishibondhuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class MathFosolCategoryFragment extends Fragment {


    public MathFosolCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mathfosol_category, container, false);

//        Toast.makeText(getContext(), "MathFosol again", Toast.LENGTH_SHORT).show();

        GridView gridView = view.findViewById(R.id.mathFosolGridId);

        final String array[] = getResources().getStringArray(R.array.mathfosol);
        int flag[] = {
                R.drawable.dhan,
                R.drawable.jute,
                R.drawable.gom,
                R.drawable.vutta,
                R.drawable.sorisa

        };

        //This is built-in Adapter of android
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);

        //This is custom Adapter
        CropsCategoryAdapter adapter = new CropsCategoryAdapter(getContext(),array,flag);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = null;
                if(position==0){
                    fragment = new MathfosolItemFragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    fragment.setArguments(args);
//                    Toast.makeText(getContext(), "Possition "+position, Toast.LENGTH_SHORT).show();
                }if(position==1){
                    fragment = new MathfosolItemFragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    fragment.setArguments(args);
//                    Toast.makeText(getContext(), "Possition "+position, Toast.LENGTH_SHORT).show();
                }if(position==2){
                    fragment = new MathfosolItemFragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    fragment.setArguments(args);
//                    Toast.makeText(getContext(), "Possition "+position, Toast.LENGTH_SHORT).show();
                }if(position==3){
                    fragment = new MathfosolItemFragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    fragment.setArguments(args);
//                    Toast.makeText(getContext(), "Possition "+position, Toast.LENGTH_SHORT).show();
                }if(position==4){
                    fragment = new MathfosolItemFragment();
                    Bundle args = new Bundle();
                    args.putInt("position", position);
                    fragment.setArguments(args);
//                    Toast.makeText(getContext(), "Possition "+position, Toast.LENGTH_SHORT).show();
                }

                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayoutId,fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        return view;
    }

}
