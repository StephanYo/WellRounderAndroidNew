package com.example.admin.wellrounder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by admin on 9/17/2017.
 * <p>
 * This is the explore class, it will be used to search for investing tems as well the entire application for certain data.
 */

public class NavExplore extends android.support.v4.app.Fragment {


    private static final String TAG = "NavExplore";

    private SwipeRefreshLayout mSwipeLayout;


    private ViewPager mViewPager;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Explore");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_explore, container
                , false);


        Log.d(TAG, "onCreate: Starting.");


        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        mSwipeLayout =  (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshNavExploreMain);

//        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//
//
//            }
//        });


        return view;

    }

    public void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter mAdapter = new SectionPageAdapter(getChildFragmentManager());
        mAdapter.addFragment(new ExploreTab2(), "What's New?");
        mAdapter.addFragment(new ExploreTab3(), "Stock Analysis");
        mAdapter.addFragment(new ExploreTab1(), "Search for Ticker");
        viewPager.setAdapter(mAdapter);
    }





}