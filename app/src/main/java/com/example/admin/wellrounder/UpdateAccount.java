package com.example.admin.wellrounder;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is the Update user profile page, Still need to add the function to actually update the user.
 */

public class UpdateAccount extends AppCompatActivity {

    public static final String mId = "id";
    public static final String usernameUP = "username";
    public static final String emailUP = "email";
    public static final String phonenumberUP = "phonenumber";
    public static final String birthdateUP = "";
    public static final String lastnameUP = "lastname";
    public static final String firstnameUp = "firstname";
    public static final String middlenameUP = "middlename";
    public static final String JSON_ARRAY = "results";

    private EditText etUsernameUP;
    private EditText etEmailUP;
    private EditText etPhoneNumberUP;
    private EditText etBirthDateUP;
    private EditText etLastNameUP;
    private EditText etFirstNameUP;
    private EditText etMiddleNameUp;

    private Button btnUpdateUP;

    public static Integer userID ;

    // 10.19.2017
    //need to test all the comment stuff: Recylcer view and if I can post comments, register user isCorrect(), and try adding the number
    //of comments posted to a post, tbh not really sure on how to achieve this. 

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        etUsernameUP = (EditText) findViewById(R.id.etUserNameUpdateProf);
        etEmailUP = (EditText) findViewById(R.id.etEmailUpdateAccount);
        etPhoneNumberUP = (EditText) findViewById(R.id.etPhoneNumberUpdateProfile);
        etLastNameUP = (EditText) findViewById(R.id.etLastNameUpdateProfile);
        etFirstNameUP = (EditText) findViewById(R.id.etFirstnameUpdateProfile);
        etMiddleNameUp = (EditText) findViewById(R.id.etMiddleNameUpdateProfile);

        btnUpdateUP = (Button) findViewById(R.id.btnUpdateProfileUpdateProf);

        getUser();


        btnUpdateUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
            }
        });

    }


    public void getUser() {

        String usersUsername = SharedPrefManager.getInstance(this).getUsername();
        String url = Constants.URL_GET_SPECIFIC_USER + usersUsername;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateAccount.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);
    }

    private void showJSON(String response) {
        Integer id = 0 ;
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
            id = userData.getInt(mId);
            username = userData.getString(usernameUP);
            email = userData.getString(emailUP);
            phonenumber = userData.getString(phonenumberUP);
            lastname = userData.getString(lastnameUP);
            firstname = userData.getString(firstnameUp);
            middlename = userData.getString(middlenameUP);

        } catch (JSONException e) {
            e.printStackTrace();
        }
            userID = id;
            etUsernameUP.setHint(username);
            etEmailUP.setHint(email);
            etPhoneNumberUP.setHint(phonenumber);
            etLastNameUP.setHint(lastname);
            etFirstNameUP.setHint(firstname);
            etMiddleNameUp.setHint(middlename);

       // Toast.makeText(this, firstnameUp, Toast.LENGTH_SHORT).show();



    }

    private void updateUser() {
        final Integer id = userID;
        final String username = etUsernameUP.getText().toString();
        final String email = etEmailUP.getText().toString();
        final String phonenumber = etPhoneNumberUP.getText().toString();
        final String lastname = etLastNameUP.getText().toString();
        final String firstname = etFirstNameUP.getText().toString();
        final String middlename = etMiddleNameUp.getText().toString();

        Toast.makeText(this, username , Toast.LENGTH_SHORT).show();


            //progressDialog.setMessage("Registering User");
            //  progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constants.URL_UPDATE_USER, new Response.Listener<String>() {
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
                    params.put("username", username);
                    params.put("email" ,email);
                    params.put("phonenumber", phonenumber);
                    params.put("lastname", lastname);
                    params.put("firstname", firstname);
                    params.put("middlename", middlename);
                    //params.put("id", id);
                    //Toast.makeText(Comments.this, comment + " " + username + " " + post_id, Toast.LENGTH_SHORT).show();
                    return params;


                }
            };

            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        }

}






