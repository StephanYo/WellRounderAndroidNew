package com.example.admin.wellrounder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;

public class TestUserProfile extends AppCompatActivity {

    private TextView tvUsernameUP, tvEmailUP, tvPhonenumberUP, tvFirstnameUP, tvMiddlenameUP, tvLastnameUP, tvIdUP;

    public ArrayList<Users> listItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_test_user_profile);

        tvUsernameUP = (TextView) findViewById(R.id.tvUserNameUP);
        tvEmailUP = (TextView) findViewById(R.id.tvEmailUP);
        tvPhonenumberUP = (TextView) findViewById(R.id.tvPhoneNumberUP);
        tvFirstnameUP = (TextView) findViewById(R.id.tvFirstUP);
        tvMiddlenameUP = (TextView) findViewById(R.id.tvMiddleUP);
        tvLastnameUP = (TextView) findViewById(R.id.tvLastNameUP);
        tvIdUP = (TextView) findViewById(R.id.tvIdUP);


        getSpecificUser();
    }

    public void getSpecificUser() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_ALL_POSTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("users");
                            Toast.makeText(TestUserProfile.this, "1st Try", Toast.LENGTH_SHORT).show();

                            String holderUsername = SharedPrefManager.getInstance(getApplicationContext()).getUsername();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject mJson = array.getJSONObject(i);
                                if (holderUsername.equals(mJson.getString("username"))) {
                                    Users users = new Users(
                                            mJson.getInt("id"),
                                            mJson.getString("username"),
                                            mJson.getString("email"),
                                            mJson.getString("phonenumber"),
                                            mJson.getString("lastname"),
                                            mJson.getString("firstname"),
                                            mJson.getString("middlename")
                                    );
                                    listItems.add(users);



                               }


                            }


                            Toast.makeText(TestUserProfile.this, "Did you even fucking make it here?", Toast.LENGTH_SHORT).show();
                            Toast.makeText(TestUserProfile.this, listItems.get(0).getId() , Toast.LENGTH_SHORT).show();





                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
