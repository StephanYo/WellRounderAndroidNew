package com.example.admin.wellrounder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

public class MessagingMainScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MessageMainList> messageMainList;

    public static final String userID = "id";
    public static final String usernameUP = "username";
    public static final String JSON_ARRAY = "results";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerViewMessagesMain);
        // recyclerView.setHasFixedSize(true); //means every one has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        messageMainList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        loadRecyclerViewData();


    }


    public void loadRecyclerViewData() {



        //String currentUsername = SharedPrefManager.getInstance(this).getUsername();
        //UserCurrent userCurrent = new UserCurrent(currentUsername, this);
        //final String currentUserID = userCurrent.getId2();

        //String user_idURL = SharedPrefManager.getInstance(this).getKeyUserId();

        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();
        //Toast.makeText(getContext(), "1 :D", Toast.LENGTH_SHORT).show();

        final String currentUserIdString = SharedPrefManager.getInstance(this).getCurrtUserIDString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_CHAT_ROOMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        // Toast.makeText(getContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(getContext(), "3:D", Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray array = jsonObject.getJSONArray("chat_ids");
                            messageMainList.clear();
                            for (int i = array.length() - 1; i >= 0; i--) {
                                JSONObject mJson = array.getJSONObject(i);
                                //change to current id in .equals section
                                if (mJson.getString("id_1").equals(currentUserIdString) || mJson.getString("id_2").equals(currentUserIdString)) {
                                    MessageMainList listItem = new MessageMainList(
                                            mJson.getString("id"),
                                            mJson.getString("chat_id"),
                                            mJson.getString("id_1"),
                                            mJson.getString("id_2"),
                                            mJson.getString("rec_username"),
                                            mJson.getString("send_username"),
                                            mJson.getString("last_message"),
                                            mJson.getString("messages_amt"),
                                            mJson.getString("time_stamp")

                                    );

                                    messageMainList.add(listItem);
                                }

                            }

                            adapter = new MessageMainAdapter(messageMainList, getApplicationContext());
                            //  adapter2 = new MessageAdapterRightSide(messageListRight, getApplicationContext());

                            recyclerView.setAdapter(adapter);
                            //recyclerView2.setAdapter(adapter2);
                            //mDialog.dismiss();

                            //Toast.makeText(getContext(), "4 :D", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mDialog.dismiss();
                //progressDialog.dismiss();
                //Toast.makeText(getContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        //Toast.makeText(getContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }



}
