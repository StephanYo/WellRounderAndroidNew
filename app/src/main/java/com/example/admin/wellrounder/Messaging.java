package com.example.admin.wellrounder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Messaging extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adapter2;
    private RecyclerView recyclerView2;
    private List<MessageList> messageList;
    private List<MessageList> messageListRight;

    private EditText etMessageBoxMessasing;

    private TextView tvCurrentUserChatting;

    private Button btnSendMessageMessaging;


    public static final String userID = "id";
    public static final String usernameUP = "username";
    public static final String JSON_ARRAY = "results";

    public String id_1 = "";
    public String id_2 = "";

    public String currentChatRoom = "";

    private int mId_1;
    private int mId_2;

    private Integer mSender_id;
    private Integer mReciver_id;

    private String mReciverUsername;

    private String mTime_stamp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        tvCurrentUserChatting = (TextView) findViewById(R.id.tvCurrentUserChatMessaging);

        btnSendMessageMessaging = (Button) findViewById(R.id.btnSendMessageMessaging);
        etMessageBoxMessasing = (EditText) findViewById(R.id.etTypeMessageHereMessaging);

        SimpleDateFormat formateDate = new SimpleDateFormat("hh:mm a");
        String formattedDate = formateDate.format(new Date()).toString();
        mTime_stamp = formattedDate;




        Bundle b = getIntent().getExtras();
        id_1 = b.getString("id_1");
        id_2 = b.getString("id_2");

        mId_1 = Integer.parseInt(id_1);
        mId_2 = Integer.parseInt(id_2);

        if (mId_1 > mId_2) {
            currentChatRoom = id_2 + ":" + id_1;
        } else {
            currentChatRoom = id_1 + ":" + id_2;
        }

        /**
         * Change mId to get the current user name. good stuff eh
         */

        Integer mCurrentIdInt = SharedPrefManager.getInstance(this).getCurretUserIDint();

        if(mId_1 == mCurrentIdInt ){

            mSender_id = mId_1;
            mReciver_id = mId_2;
            tvCurrentUserChatting.setText(mReciver_id.toString());
            setTitle("Chatting With" + mReciver_id.toString());

        }else{
            mSender_id = mId_2;
            tvCurrentUserChatting.setText(mSender_id.toString());
            setTitle("Chatting With" + mSender_id.toString());
            mReciver_id = mId_1;
        }


        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerViewMessages);
        // recyclerView2 = (RecyclerView) findViewById(R.id.rvRecyclerViewMessagesRight);
        // recyclerView.setHasFixedSize(true); //means every one has a
        // fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //recyclerView.scrollToPosition(adapter.getItemCount()-1);

        messageList = new ArrayList<>();
        messageListRight = new ArrayList<>();

        loadRecyclerViewData();

        btnSendMessageMessaging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
                etMessageBoxMessasing.getText().clear();
                adapter.notifyDataSetChanged();
                loadRecyclerViewData();
            }
        });



    }

    public void loadRecyclerViewData() {

        //final ProgressDialog mDialog = new ProgressDialog(getApplicationContext());
       // mDialog.setMessage("Loading...");
        //mDialog.show();

        String currentUsername = SharedPrefManager.getInstance(this).getUsername();
        // UserCurrent userCurrent = new UserCurrent(currentUsername, this);
        //final String currentUserID = userCurrent.getId2();

        //String user_idURL = SharedPrefManager.getInstance(this).getKeyUserId();

        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();
        //Toast.makeText(getContext(), "1 :D", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_ALL_SPECIFIC_MESSAGES + currentChatRoom,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        // Toast.makeText(getContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(getContext(), "3:D", Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray array = jsonObject.getJSONArray("messages");
                            messageList.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject mJson = array.getJSONObject(i);
//
                                String reciver_id = mJson.getString("reciver_id");
                                String sender_id = mJson.getString("sender_id");

                                //Toast.makeText(Messaging.this, reciver_id, Toast.LENGTH_SHORT).show();
                                //Toast.makeText(Messaging.this, sender_id, Toast.LENGTH_SHORT).show();


                                MessageList listItem = new MessageList(
                                        mJson.getString("id"),
                                        mJson.getString("chat_id"),
                                        mJson.getString("reciver_id"),
                                        mJson.getString("sender_id"),
                                        mJson.getString("message"),
                                        mJson.getString("time_stamp")

                                );

//                                if(mJson.getString("reciver_id").toString().equals(currentUserID)  || mJson.getString("sender_id").toString().equals(currentUserID)) {
//                                messageListRight.add(listItem);
//                                  //  Toast.makeText(Messaging.this, "right", Toast.LENGTH_SHORT).show();
//
//
//                                }else{
                                messageList.add(listItem);
                                //   Toast.makeText(Messaging.this, "left", Toast.LENGTH_SHORT).show();
//                                }


                            }

                            adapter = new MessageAdapter(messageList, getApplicationContext());
                            //  adapter2 = new MessageAdapterRightSide(messageListRight, getApplicationContext());

                            recyclerView.setAdapter(adapter);
                         //   mDialog.dismiss();
                            //recyclerView2.setAdapter(adapter2);

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
              //  mDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        //Toast.makeText(getContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }

    private void sendMessage() {

        final String chat_id = currentChatRoom;
        final String sender_id =  mSender_id.toString();
        final String reciver_id = mReciver_id.toString();
        final String message = etMessageBoxMessasing.getText().toString().trim();
        final String time_stamp = mTime_stamp;





        //final String post_id = mPost_ID.toString();
        // Toast.makeText(this, mPost_ID, Toast.LENGTH_SHORT).show();

        //progressDialog.setMessage("Registering User");
        //  progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_SEND_MESSAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);


                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    //Toast.makeText(getApplicationContext(), "this is the correct user register?", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Required Fields are Missing" + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("chat_id", chat_id);
                params.put("sender_id", sender_id);
                params.put("reciver_id", reciver_id);
                params.put("message", message);
                params.put("time_stamp", time_stamp);

                //Toast.makeText(Comments.this, comment + " " + username + " " + post_id, Toast.LENGTH_SHORT).show();
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void getSender(){
        String usersUsername = SharedPrefManager.getInstance(this).getUsername();
        String url = Constants.URL_GET_SPECIFIC_USER_BY_ID + mSender_id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSONSender(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Messaging.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);
    }

    public void getReciver() {

        String usersUsername = SharedPrefManager.getInstance(this).getUsername();
        String url = Constants.URL_GET_SPECIFIC_USER_BY_ID + mReciver_id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Messaging.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);
    }

    private void showJSON(String response) {
        String id = "";
        String username = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject userData = result.getJSONObject(0);
            id = userData.getString(userID);
            username = userData.getString(usernameUP);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //userID2 = id.toString();
        //username2 = username.toString();

        mReciverUsername = username;

    }

    private void showJSONSender(String response) {
        String id = "";
        String username = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject userData = result.getJSONObject(0);
            id = userData.getString(userID);
            username = userData.getString(usernameUP);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //userID2 = id.toString();
        //username2 = username.toString();

        SharedPrefManager.getInstance(this).setCurrtUserID(id);
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

    }

}
