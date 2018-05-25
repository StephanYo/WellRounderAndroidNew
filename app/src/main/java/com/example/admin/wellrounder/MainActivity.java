package com.example.admin.wellrounder;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is the log in page and also the main activity. User will be able to log in here
 * as well as click the bottom button to create a user account.
 */
public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvCreateAccount;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReadRss readRss = new ReadRss(getApplicationContext());
        readRss.execute();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            finish();

          // displaySelectedScreen(1);

            //Intent i = new Intent(this,ActivityName.class);
            startActivity(new Intent(this, HomePage.class));
            Log.d("HomePabe" , "Opening to correct frag hopefully");
            return;
        }

        etUsername = (EditText) findViewById(R.id.etUserNameLogin);
        etPassword = (EditText) findViewById(R.id.etPasswordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogIn);
        tvCreateAccount = (TextView) findViewById(R.id.tvNewAccount);

        tvCreateAccount.setOnClickListener(onClickListener);
        btnLogin.setOnClickListener(onClickListener);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loggin Ya in bud");

       // if(SharedPrefManager.getInstance(this).isLoggedIn()){
         //   finish();
      //      startActivity(new Intent(this, HomePage.class));
         //   return;
       // }

    }
    private void userLogin() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getBoolean("error")) {
                                //Remember to change the class name to SharedPrefManager from Refrence
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("username"),
                                                jsonObject.getString("email")



                                        );


                                startActivity(new Intent(getApplicationContext(), HomePage.class));
                                finish();

                            } else {
                                Toast.makeText(
                                        getApplicationContext(),
                                        jsonObject.getString("message") + "Something went wrong, please try again",
                                        Toast.LENGTH_LONG).show();


                            }

                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage() + " Please try again.",
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch(view.getId()){
                case R.id.tvNewAccount:
                  startActivity(new Intent(getApplicationContext(), RegisterUser.class));
                    break;
                case R.id.btnLogIn:
                   userLogin();
                    //Toast.makeText(MainActivity.this, "Ayoooo", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    private void displaySelectedScreen(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.nav_homePage:

               // startActivity(new Intent(getApplicationContext(), HomePage.class));
                 fragment = new NavHomePage(); //menu 1

                break;
            case R.id.nav_newsFeed:
                fragment = new NavNewsFeed(); //menu 2

                break;
            case R.id.nav_invest:
                fragment = new NavInvest(); //nav_invest
                break;
            case R.id.nav_explore:
                fragment = new NavExplore(); //menu 4
                break;
            case R.id.nav_share:

                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Hey Invest with your friends on Well Rounder! Click the Icon to take you the the Store Page! @WellRounder #WellRounder";
                //  String shareSub = "Click the link, to take you to the Windows Store";
                //myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share with!"));

                fragment = new NavShare(); //menu 5
                break;
            case R.id.nav_help:
                fragment = new NavHelp(); //menu 6
                break;
            case R.id.nav_settings:
                fragment = new NavSettings(); //menu 7
                break;
        }
        if(fragment !=null) {
            android.support.v4.app.FragmentManager ft = getSupportFragmentManager();

            ft.beginTransaction()
                    .replace(R.id.content_home_page, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

}
