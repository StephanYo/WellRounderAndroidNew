package com.example.admin.wellrounder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by admin on 9/17/2017.
 *
 * This is the help class, that will show a customer support line, as well as basic help functions
 */


public class NavHelp extends android.support.v4.app.Fragment{

    public TextView tvHelpTest, tvToMessagePage, tvToMessageMain;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Help");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.nav_help, container
                , false);

        tvHelpTest = (TextView) view.findViewById(R.id.tvMenu1Text3ayo) ;

        tvToMessagePage = (TextView) view.findViewById(R.id.tvToMessagePage);

        tvToMessageMain = (TextView) view.findViewById(R.id.tvToMessageMain);

        tvToMessageMain.setOnClickListener(onClickListener);

        tvToMessagePage.setOnClickListener(onClickListener);


        tvHelpTest.setOnClickListener(onClickListener);

        return view;

    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // String printThis = "";

            switch(view.getId()){
                case R.id.tvMenu1Text3ayo:
                    //  printThis = tvInviteFriendsNavSettings.toString();
                    //Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), TabbedTest.class));
                    break;

                case R.id.tvToMessagePage:
                    startActivity(new Intent(getContext(), Messaging.class));

                    break;

                case R.id.tvToMessageMain:
                    startActivity(new Intent(getContext(), MessagingMainScreen.class));
            }

        }
    };


}