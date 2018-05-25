package com.example.admin.wellrounder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/17/2017.
 *
 * This will be used for the actual brokerage of the application
 *
 */

public class NavInvest extends android.support.v4.app.Fragment{

    private TextView tvTakeToPage;

    private static final String TAG = "NavHomePage";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    private SwipeRefreshLayout swipeRefreshLayout;





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Invest");




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.nav_invest,container
                ,false);

       // tvTakeToPage = (TextView) view.findViewById(R.id.tvMenu1Text3);

       // tvTakeToPage.setOnClickListener(onClickListener);


        ReadRss readRss = new ReadRss(getContext());
        readRss.execute();

        Log.d(TAG, "onCreate: Starting.");

        mSectionPageAdapter = new SectionPageAdapter(getFragmentManager());

        mViewPager = (ViewPager) view.findViewById(R.id.container);
        setUpViewPager(mViewPager);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return view;

    }


    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

           // String printThis = "";

            switch(view.getId()){
                case R.id.tvMenu1Text3:
                  //  printThis = tvInviteFriendsNavSettings.toString();
                    //Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), StockTest.class));
                    break;

            }

        }
    };

    public void setUpViewPager(ViewPager viewPager) {
        SectionPageAdapter mAdapter = new SectionPageAdapter(getChildFragmentManager());
        mAdapter.addFragment(new Tab1Fragment(), "Search Stock");
        mAdapter.addFragment(new Tab2Fragment(), "Search Crypto");
        mAdapter.addFragment(new Tab3Fragment(), "Invest");
        viewPager.setAdapter(mAdapter);
    }



}