package com.example.admin.wellrounder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 12/21/2017.
 */


public class NavMessagingMain extends android.support.v4.app.Fragment{


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<MessageMainList> messageMainList;


    private ProgressDialog progressDialog;

    private Button btnToSpecific;

    public static final String userID = "id";
    public static final String usernameUP = "username";
    public static final String JSON_ARRAY = "results";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Messages");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.nav_messaging_main, container
                , false);


        progressDialog = new ProgressDialog(getContext());


        recyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerViewMessagesMain);
        // recyclerView.setHasFixedSize(true); //means every one has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        btnToSpecific = (Button) view.findViewById(R.id.btnToNewMessage);

        btnToSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialog();
            }
        });
        messageMainList = new ArrayList<>();

        loadRecyclerViewData();


        return view;

    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch(view.getId()){
                case R.id.tvMenu1Text3ayo:
                    //  printThis = tvInviteFriendsNavSettings.toString();
                    //Toast.makeText(getContext(), "You Clicked "  + printThis, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), TabbedTest.class));
                   break;
           }
        }
    };

    public void loadRecyclerViewData() {



        //String currentUsername = SharedPrefManager.getInstance(this).getUsername();
        //UserCurrent userCurrent = new UserCurrent(currentUsername, this);
        //final String currentUserID = userCurrent.getId2();

        //String user_idURL = SharedPrefManager.getInstance(this).getKeyUserId();

        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();
        //Toast.makeText(getContext(), "1 :D", Toast.LENGTH_SHORT).show();

        final String currentUserIdString = SharedPrefManager.getInstance(getContext()).getCurrtUserIDString();

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

                            adapter = new MessageMainAdapter(messageMainList, getContext());
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
                Toast.makeText(getContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        //Toast.makeText(getContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }

    public void displayDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        final View inflator = layoutInflater.inflate(R.layout.dialog_send_message, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        alert.setTitle("     Send a message");
        //alert.setMessage("Ayo this the message");
        alert.setView(inflator);


        final EditText etSendMessageTo = (EditText) inflator.findViewById(R.id.etSendMessageTo);
        final EditText etSendMessageText = (EditText) inflator.findViewById(R.id.etSendMessageToText);



        final String rec_username = etSendMessageTo.getText().toString().trim();
        final String send_username = SharedPrefManager.getInstance(getContext()).getUsername();
        final String time_stamp = getTime();

        alert.setPositiveButton("Send Message", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getContext(), "Pewp" + timeofpost, Toast.LENGTH_SHORT).show();

                final String message = etSendMessageText.getText().toString().trim();

                sendMessage(rec_username, send_username, time_stamp, message);


                //final  String etNewPostDialog = etNewDialogPost.getText().toString().trim();
               // sendUserPost(username,  etNewPostDialog, timeofpost, dateofpost );
                //isClicked = true;

                //startActivity(new Intent(getBaseContext(), NavNewsFeed.class));


                //Toast.makeText(HomePage.this, timeofpost , Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alert.show();
    }

    public String getTime(){
        // String currentTime = DateFormat.getDateTimeInstance().format(new Date());

        SimpleDateFormat formateDate = new SimpleDateFormat("hh:mm a");
        String formattedDate = formateDate.format(new Date()).toString();



        return formattedDate;
    }

    private void sendMessage(final String send_username, final String rec_username, final String time_stamp, final String message) {




        progressDialog.setMessage("Message is being Sent!");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_SEND_MESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), "this is the correct user register?", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Do not leave the post blank" + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("rec_username", rec_username );
                params.put("send_username", send_username);
                params.put("time_stamp", time_stamp);
                params.put("message", message);


                return params;

            }
        };

        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);


    }


}
