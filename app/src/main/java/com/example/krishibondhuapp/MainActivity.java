package com.example.krishibondhuapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViewPager();
    }

    private void setupViewPager() {
        SectionPaserAdapter adapter = new SectionPaserAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentFarmer());
        adapter.addFragment(new FragmentAgriOfficer());
        adapter.addFragment(new FragmentGeneral());

        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(R.string.help);
        tabLayout.getTabAt(1).setText(R.string.buy);
        tabLayout.getTabAt(2).setText(R.string.sell);
    }
}