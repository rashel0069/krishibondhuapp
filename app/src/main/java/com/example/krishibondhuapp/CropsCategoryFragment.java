package com.example.krishibondhuapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.example.krishibondhuapp.R.layout.fragment_crops_category;


/**
 * A simple {@link Fragment} subclass.
 */
public class CropsCategoryFragment extends Fragment {




    public CropsCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate( fragment_crops_category, container, false);

        GridView gridView = view.findViewById( R.id.cropsGridId );

        final String[] arraylist = getResources().getStringArray( R.array.foshol);
        int[] flag = {R.drawable.math_fosol, R.drawable.fol, R.drawable.ful, R.drawable.sobji, R.drawable.mosla_demo, R.drawable.posu

        };

        //This is built-in Adapter of android
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);

        //This is custom Adapter
        CropsCategoryAdapter adapter = new CropsCategoryAdapter(getContext(),arraylist,flag);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                if (position == 0){
                    fragment = new MathFosolCategoryFragment();
                }
                if (position == 1){
                    fragment = new FolCategoryFragment();

                }if (position == 2){
                    fragment = new FulCategoryFragment();


                }if (position == 3){
                    fragment = new SakSobjiCategoryFragment();

                }if (position == 4){
                    fragment = new MoslaCategoryFragment();

                }if (position == 5){
                    fragment = new GrihopalitoPosuPakhiCategoryFragment();

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
