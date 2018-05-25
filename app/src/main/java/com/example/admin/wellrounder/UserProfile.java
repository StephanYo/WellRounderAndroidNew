package com.example.admin.wellrounder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * This class is the UserProfile page, if clicked on form the news feed or anywhere else on a users username, it will take u here.
 */

public class UserProfile extends AppCompatActivity {


    public static final String id = "id";
    public static final String usernameUP = "username";
    public static final String emailUP = "email";
    public static final String phonenumberUP = "phonenumber";
    public static final String birthdateUP = "";
    public static final String lastnameUP = "lastname";
    public static final String firstnameUp = "firstname";
    public static final String middlenameUP = "middlename";
    public static final String JSON_ARRAY = "results";

    public static Integer mId;

    public boolean isCurrentUser = false;


    private TextView tvUsernameProf, tvFirstLastProf, tvBioProf;
    private Button btnEditProfileProf;
   // public Bundle b = getIntent().getExtras();

    public String usernameProf = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvUsernameProf = (TextView) findViewById(R.id.tvUsernameProf);
        tvFirstLastProf = (TextView) findViewById(R.id.tvFirstLastProf);
        tvBioProf = (TextView) findViewById(R.id.tvBioProf);
        btnEditProfileProf = (Button) findViewById(R.id.btnEditProfileProf);



        Bundle b = getIntent().getExtras();
        String usernameProf = b.getString("keyUsername");
        String currentUsername = SharedPrefManager.getInstance(this).getUsername();

        if(usernameProf.equals(currentUsername)){
            btnEditProfileProf.setVisibility(View.VISIBLE);
        }

        Toast.makeText(this, usernameProf, Toast.LENGTH_SHORT).show();
        loadUser(usernameProf);

        btnEditProfileProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserProfile.this, UpdateAccount.class));

            }
        });

    }


    public void loadUser(String usernameProf){

      //  String usersUsername = SharedPrefManager.getInstance(this).getUsername();
        String url = Constants.URL_GET_SPECIFIC_USER + usernameProf;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);


    }

    private void showJSON(String response) {
        String id = "";
        String username = "";
        String email = "";
        String phonenumber = "";
        String lastname = "";
        String firstname = "";
        String middlename = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject userData = result.getJSONObject(0);

            username = userData.getString(usernameUP);
            email = userData.getString(emailUP);
            phonenumber = userData.getString(phonenumberUP);
            lastname = userData.getString(lastnameUP);
            firstname = userData.getString(firstnameUp);
            middlename = userData.getString(middlenameUP);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvUsernameProf.setText(username);
        tvFirstLastProf.setText(firstname + " " + lastname);
        tvBioProf.setText(email + " " + phonenumber);


        // Toast.makeText(this, firstnameUp, Toast.LENGTH_SHORT).show();


    }

}
