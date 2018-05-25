package com.example.admin.wellrounder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by admin on 11/18/2017.
 */

public class Tab3Fragment extends Fragment {

    private static final String TAG = "Tab3Fragment";

    private Button btnTab3Test;
    private TextView tvTab3Test;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.tab3_fragment, container
                , false);

        tvTab3Test = (TextView) view.findViewById(R.id.tvTabTest3);


        return view;
    }
}
