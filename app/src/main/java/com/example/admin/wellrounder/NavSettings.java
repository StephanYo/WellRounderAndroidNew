package com.example.admin.wellrounder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 9/17/2017.
 *
 * Settings class that will allow user to change certain functions of the application.
 *
 */

public class NavSettings extends android.support.v4.app.Fragment{

    private TextView tvInviteFriendsNavSettings, tvEditProfileNavSettings, tvInvestingNavSettings,
    tvAccountPrivacyNavSettings, tvAboutNavSettings, tvNotificationsNavSettings, tvTermsOfServiceNavSettings;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Settings");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.nav_settings,container
                ,false);
        tvInviteFriendsNavSettings = (TextView) view.findViewById(R.id.tvInviteFriendsNavSettings);
        tvEditProfileNavSettings = (TextView) view.findViewById(R.id.tvEditProfileNavSettings);
        tvInvestingNavSettings =  (TextView) view.findViewById(R.id.tvInvestingNavSettings);
        tvAccountPrivacyNavSettings=  (TextView) view.findViewById(R.id.tvAccountPrivacyNavSettings);
        tvAboutNavSettings =  (TextView) view.findViewById(R.id.tvAboutNavSettings);
        tvNotificationsNavSettings =  (TextView) view.findViewById(R.id.tvNotificationsNavSettings);
        tvTermsOfServiceNavSettings =  (TextView) view.findViewById(R.id.tvTermsOfServiceNavSettings);



        tvInviteFriendsNavSettings.setOnClickListener(onClickListener);
        tvEditProfileNavSettings.setOnClickListener(onClickListener);
        tvInvestingNavSettings.setOnClickListener(onClickListener);
        tvAccountPrivacyNavSettings.setOnClickListener(onClickListener);
        tvAboutNavSettings.setOnClickListener(onClickListener);
        tvNotificationsNavSettings.setOnClickListener(onClickListener);
        tvTermsOfServiceNavSettings.setOnClickListener(onClickListener);



        return view;
    }
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String printThis = "";

            switch(view.getId()){
                case R.id.tvInviteFriendsNavSettings:
                    printThis = tvInviteFriendsNavSettings.toString();
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String shareBody = "Hey Invest with your friends on Well Rounder! Click the Link to take you the the Store Page! @WellRounder #WellRounder https://goo.gl/3xzWVx";
                    //  String shareSub = "Click the link, to take you to the Windows Store";
                    //myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                    myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(myIntent, "Share with!"));                    break;
                case R.id.tvEditProfileNavSettings:
                    startActivity(new Intent(getContext(), UpdateAccount.class));
                    break;
                case R.id.tvInvestingNavSettings:
                    printThis = tvInvestingNavSettings.toString();
                    Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tvAccountPrivacyNavSettings:
                    printThis = tvAccountPrivacyNavSettings.toString();
                    Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tvAboutNavSettings:
                    printThis = tvAboutNavSettings.toString();
                    Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tvNotificationsNavSettings:
                    printThis = tvNotificationsNavSettings.toString();
                    Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                  break;
                case R.id.tvTermsOfServiceNavSettings:
                    printThis = tvTermsOfServiceNavSettings.toString();
                    Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };




}