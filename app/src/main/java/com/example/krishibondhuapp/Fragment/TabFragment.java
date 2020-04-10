package com.example.krishibondhuapp.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.krishibondhuapp.R;
import com.example.krishibondhuapp.Adaptar.SectionPaserAdapter;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);

        viewPager = view.findViewById(R.id.container);
        tabLayout = view.findViewById(R.id.tabs);


        setupViewPager();

        return view;
    }


    private void setupViewPager(){
        SectionPaserAdapter adapter = new SectionPaserAdapter(getChildFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new BuyingProductsFragment());
        adapter.addFragment(new SellingProductsFragment());


        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(R.string.help);
        tabLayout.getTabAt(1).setText(R.string.buy);
        tabLayout.getTabAt(2).setText(R.string.sell);
    }

}
