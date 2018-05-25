package com.example.admin.wellrounder;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TabbedTest extends AppCompatActivity {

    private static final String TAG = "TabbedTest";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_test);
        Log.d(TAG, "onCreate: Starting.");

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        //set up the ViewPager with the sectinos adapter

        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public void setUpViewPager(ViewPager viewPager){
        SectionPageAdapter mAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new Tab1Fragment(), "TAB1");
        mAdapter.addFragment(new Tab2Fragment(), "TAB2");
        mAdapter.addFragment(new Tab3Fragment(), "TAB3");
        viewPager.setAdapter(mAdapter);
    }
}

