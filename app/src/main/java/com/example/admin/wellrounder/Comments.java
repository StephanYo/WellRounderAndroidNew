package com.example.admin.wellrounder;

import android.app.ProgressDialog;
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
import org.w3c.dom.Comment;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.admin.wellrounder.R.id.tvBioProf;
import static com.example.admin.wellrounder.R.id.tvFirstLastProf;
import static com.example.admin.wellrounder.UserProfile.JSON_ARRAY;


public class Comments extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<CommentList> mCommentList;
    public String mPost_ID = "";

    public TextView postCMT, usernameCMT, timeCMT, dateCMT, tvNoCommentsCMT;
    private EditText etCreateCommentCMT;
    private Button btnCreateCommentCMT;

    private ProgressDialog progressDialog;


    /*  public static final String id = "id";
    public static final String postCMT = "post";
    public static final String usernameCMT = "username";
    public static final String timeofpostCMT = "timeofpost";
    public static final String dateofpostCMT = "dateofpost";
    //public static final String lastnameUP = "lastname";
    //public static final String firstnameUp = "firstname";
    //public static final String middlenameUP = "middlename";
    //public static final String JSON_ARRAY = "results";

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        postCMT = (TextView) findViewById(R.id.tvPostCMTpage);
        usernameCMT = (TextView) findViewById(R.id.tvUsernameCMT);
        timeCMT = (TextView) findViewById(R.id.tvTimeCMT);
        dateCMT = (TextView) findViewById(R.id.tvDateCMT);
        tvNoCommentsCMT = (TextView) findViewById(R.id.tvNoCommentsCMTS);
        etCreateCommentCMT = (EditText) findViewById(R.id.etPostCommentCMT);
        btnCreateCommentCMT = (Button) findViewById(R.id.btnPostCommentCMT);

        Bundle b = getIntent().getExtras();
        String keyPost_id = b.getString("keyPost_ID");
        String userPost = b.getString("userPost");
        String timeOfPost = b.getString("timeOfPost");
        String dateOfPost = b.getString("dateOfPost");
        String getUser = b.getString("getUser");

        //postCMT.setText(keyPost_id);
        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerViewComments);
        recyclerView.setHasFixedSize(true); //means every one has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        progressDialog = new ProgressDialog(this);


        mPost_ID = keyPost_id;

        postCMT.setText(userPost);
        usernameCMT.setText(getUser);
        timeCMT.setText(timeOfPost);
        dateCMT.setText(dateOfPost);

        //Toast.makeText(this, keyPost_id, Toast.LENGTH_SHORT).show();

        mCommentList = new ArrayList<>();


        loadRecyclerViewData();

        //this is to test if the recyclver view is working with out recieving data
     /* for (int i = 0; i < 10; i++) {
          CommentList item = new CommentList(
                  "Comment" + (i + 1),
                  "username",
                  "post_id"

          );
          mCommentList.add(item);
      }
      adapter = new MyAdapterComment(mCommentList, getApplicationContext());

      recyclerView.setAdapter(adapter);*/

        btnCreateCommentCMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if(btnCreateCommentCMT.getText().toString().isEmpty()){

                //     Toast.makeText(Comments.this, "Do not leave Comment empty", Toast.LENGTH_SHORT).show();

                //  }
                // else{
                postComment();
                etCreateCommentCMT.getText().clear();
                mAdapter.notifyDataSetChanged();
                loadRecyclerViewData();

                //}
            }
        });

    }



    public void loadRecyclerViewData() {

        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();
        //Toast.makeText(getContext(), "1 :D", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_POST_COMMENTS + mPost_ID.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        // Toast.makeText(getApplicationContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {
                            // Toast.makeText(getApplicationContext(), "3:D", Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("results");
                            mCommentList.clear();
                            if(array.length() == 0){
                                tvNoCommentsCMT.setVisibility(View.VISIBLE);
                            }
                            for (int i = array.length()-1 ; i >= 0; i--) {
                                JSONObject mJson = array.getJSONObject(i);
                                CommentList commentList = new CommentList(
                                        mJson.getString("comment"),
                                        mJson.getString("username"),
                                        mJson.getString("post_id")


                                        //mJson.getString("post_id")
                                );
                                mCommentList.add(commentList);
                                // Toast.makeText(Comments.this, mJson.getString("comment"), Toast.LENGTH_SHORT).show();
                            }

                            //adapter = new MyAdapterComment(mCommentList, getApplicationContext());
                            mAdapter = new MyAdapterComment(mCommentList, getApplicationContext());
                            recyclerView.setAdapter(mAdapter);

                            //Toast.makeText(getApplicationContext(), "4 :D", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                            // Toast.makeText(getApplicationContext(), "5 :D", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                // Toast.makeText(getApplicationContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        //Toast.makeText(getApplicationContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }


    private void postComment() {

        final String comment = etCreateCommentCMT.getText().toString().trim();
        final String username = SharedPrefManager.getInstance(this).getUsername();
        final String post_id = mPost_ID.toString();
       // Toast.makeText(this, mPost_ID, Toast.LENGTH_SHORT).show();

        //progressDialog.setMessage("Registering User");
        //  progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_POST_COMMENT, new Response.Listener<String>() {
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
                params.put("comment", comment);
                params.put("username", username);
                params.put("post_id", post_id);

                //Toast.makeText(Comments.this, comment + " " + username + " " + post_id, Toast.LENGTH_SHORT).show();
                return params;


            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


}


