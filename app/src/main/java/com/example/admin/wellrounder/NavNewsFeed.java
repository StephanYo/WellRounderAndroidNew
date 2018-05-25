package com.example.admin.wellrounder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 9/17/2017.
 *
 * This class is the news feed class. It holds all the user posts. A post is
 *
 */

public class NavNewsFeed extends android.support.v4.app.Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;
    private LinearLayout mLinearLayout;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("News Feed");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_news_feed, container
                , false);



        recyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerView);
       // recyclerView.setHasFixedSize(true); //means every one has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        listItems = new ArrayList<>();


//        for (int i = 0; i < 10; i++) {
//            ListItem item = new ListItem(
//                    "Header" + (i + 1),
//                    "Body",
//                    "username",
//                    "Time",
//                    "date"
//            );
//            listItems.add(item);
//        }
//        adapter = new MyAdapter(listItems, getContext());


        //this will load the news feed
         loadRecyclerViewData();



        //recyclerView.setAdapter(adapter);



        return view;

    }


    public void loadRecyclerViewData() {
        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();
        //Toast.makeText(getContext(), "1 :D", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_ALL_POSTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                       // Toast.makeText(getContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(getContext(), "3:D", Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray array = jsonObject.getJSONArray("post");
                            listItems.clear();
                            for (int i = array.length()-1; i >= 0; i--) {
                                JSONObject mJson = array.getJSONObject(i);
                                ListItem listItem = new ListItem(
                                        mJson.getString("id"),
                                        mJson.getString("post"),
                                        mJson.getString("username"),
                                        mJson.getString("timeofpost"),
                                        mJson.getString("dateofpost")
                                        //mJson.getString("post_id")
                                );
                                listItems.add(listItem);
                            }

                            adapter = new MyAdapter(listItems, getContext());

                            recyclerView.setAdapter(adapter);

                            //Toast.makeText(getContext(), "4 :D", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                //Toast.makeText(getContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        //Toast.makeText(getContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }



}