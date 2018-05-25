package com.example.admin.wellrounder;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.admin.wellrounder.Adapter.RssFeedAdapter;
import com.example.admin.wellrounder.Common.HTTPDataHandler;
import com.example.admin.wellrounder.Model.RSSObject;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is the the home page, where the navigation bar is present. If the user is logged in it will automatically take
 * them here, if not they will be taken to the log in page.
 */

import static com.example.admin.wellrounder.Constants.URL_GET_ALL_POSTS;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<ListItem> listItems;

    private Button btnLoadRSS;

    private TextView tvUsernameNav, tvEmailNav;
    private ProgressDialog progressDialog, progressDialog2;
    //private String mUsername;
    private boolean isClicked = false;

    public Toolbar toolbar;
    public RSSObject rssObject;
    private final String RSS_Link = "http://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
    private final String RSS_to_JSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    public static final String userID = "id";
    public static final String usernameUP = "username";
    public static final String JSON_ARRAY = "results";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if (!SharedPrefManager  .getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
            //Intent i = new Intent(this,ActivityName.class);
            Log.d("HomePabe" , "in the shared pref trying to open correct home page");
            //startActivity(i);

        }

        getTheCurrentUser();
        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home Page ayo");
        setSupportActionBar(toolbar);*/
        ReadRss readRss = new ReadRss(getApplicationContext());
        readRss.execute();


       /* *//**
         * this breaks the home page
         *//*
        btnLoadRSS = (Button) findViewById(R.id.btnLoadRSSData);
        btnLoadRSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadRSS();
            }
        });*/

        progressDialog = new ProgressDialog(this);
        //progressDialog2 = new ProgressDialog(this);
        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerView);
        //recyclerView.setHasFixedSize(true); //means every one has a fixed size


        /**
         * this breaks the home page
         */
/*
       recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL,false));
*/



        listItems = new ArrayList<>();

       // for(int i =0; i<10; i++){
        //    ListItem item = new ListItem(
         //     "Header" + (i+1),
         //           "Body",
         //           "username",
         //           "Time"
         //   );

        //    listItems.add(item);
        //}
       // adapter = new MyAdapter(listItems, this);



            //this will load the news feed
         //  loadRecyclerViewData();




       // recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               displayDialog();
                /*if(isClicked== true){
                    startActivity(new Intent(getApplicationContext(), NavNewsFeed.class));
                    finish();
                }*/



                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvUsernameNav = (TextView)header.findViewById(R.id.tvUsernameNav);
        tvEmailNav = (TextView)header.findViewById(R.id.tvEmailNav);
        tvUsernameNav.setText(SharedPrefManager.getInstance(this).getUsername());
        tvEmailNav.setText(SharedPrefManager.getInstance(this).getUserEmail());







    }

    private void loadRSS() {
        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {
            ProgressDialog mDialog = new ProgressDialog(HomePage.this);

            @Override
            protected void onPreExecute() {
                mDialog.setMessage("Loading Data Bruh...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return result;

            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                RssFeedAdapter adapter = new RssFeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        StringBuilder url_get_data = new StringBuilder(RSS_to_JSON);
        url_get_data.append(RSS_Link);
        loadRSSAsync.execute(url_get_data.toString());
    }



    public void loadRecyclerViewData(){
      //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_ALL_POSTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                   // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("post");

                            for(int i = 0; i<array.length(); i++){
                               JSONObject mJson = array.getJSONObject(i);
                                ListItem listItem = new ListItem(
                                        mJson.getString("id"),
                                        mJson.getString("post"),
                                        mJson.getString("username"),
                                        mJson.getString("timeofpost"),
                                        mJson.getString("dateofpost")
                                );
                                listItems.add(listItem);
                            }

                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                SharedPrefManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
        }

        return true;
    }

    public String getTime(){
       // String currentTime = DateFormat.getDateTimeInstance().format(new Date());

        SimpleDateFormat formateDate = new SimpleDateFormat("hh:mm a");
        String formattedDate = formateDate.format(new Date()).toString();



        return formattedDate;
    }
    public String getDate(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;


    }



    public void displayDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View inflator = layoutInflater.inflate(R.layout.dialog_new_post, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //alert.setTitle("     What's on your mind?");
        //alert.setMessage("Ayo this the message");
        alert.setView(inflator);

        final String timeofpost = getTime();
        final String dateofpost = getDate();

        final EditText etNewDialogPost = (EditText) inflator.findViewById(R.id.etNewPostDialog);
        final String username = SharedPrefManager.getInstance(this).getUsername();
        TextView tvAttachStock = (TextView) inflator.findViewById(R.id.tvAttachStockDialog);
        TextView tvAttachPicture = (TextView) inflator.findViewById(R.id.tvAddPictureDialog);
      //  ImageView imgMoneySignDialog = (ImageView) inflator.findViewById(R.id.imgMoneySignDialog);
       // ImageView imgCameraDialog = (ImageView) inflator.findViewById(R.id.imgCameraDialog);

        tvAttachStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "You clicked the stock", Toast.LENGTH_SHORT).show();
            }
        });

        tvAttachPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Picture", Toast.LENGTH_SHORT).show();
            }
        });

       /* imgCameraDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Icon Camera", Toast.LENGTH_SHORT).show();
            }
        });

        imgMoneySignDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "$$$$$$$$$", Toast.LENGTH_SHORT).show();
            }
        });*/



        alert.setPositiveButton("Create Post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              final  String etNewPostDialog = etNewDialogPost.getText().toString().trim();
                sendUserPost(username,  etNewPostDialog, timeofpost, dateofpost );
                isClicked = true;

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



    private void displaySelectedScreen(int id){
        Fragment fragment = null;

        switch (id){
            case R.id.nav_homePage:

               //startActivity(new Intent(getApplicationContext(), HomePage.class));
                fragment = new NavHomePage(); //menu 1

                break;
            case R.id.nav_newsFeed:
                fragment = new NavNewsFeed(); //menu 2
                //btnLoadRSS.setVisibility(View.INVISIBLE);
                //recyclerView.setVisibility(View.INVISIBLE);

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
                String shareBody = "Hey Invest with your friends on Well Rounder! Click the Link to take you the the Store Page! @WellRounder #WellRounder https://goo.gl/3xzWVx";
              //  String shareSub = "Click the link, to take you to the Windows Store";
                //myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share with!"));

                //fragment = new NavShare(); //menu 5
                break;
            case R.id.nav_help:
                fragment = new NavHelp(); //menu 6
                break;
            case R.id.nav_settings:
                fragment = new NavSettings(); //menu 7
                break;

            case R.id.nav_messaging_main:
                fragment = new NavMessagingMain();
                break;
        }
        if(fragment !=null) {
            FragmentManager ft = getSupportFragmentManager();

            ft.beginTransaction()
                    .replace(R.id.content_home_page, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d("HomePabe", item.toString());



        int id = item.getItemId();
        String printThis = String.valueOf(id);

        Log.d("HomePabeID", printThis);

        displaySelectedScreen(id);

        return true;
    }
    private void sendUserPost(final String username, final String post, final String timeofpost, final String dateofpost) {


       // final String post = etNewPostDialog.getText().toString().trim();
      //  final String username = SharedPrefManager.getInstance(this).getUsername();

        progressDialog.setMessage("Post is being created!");
        progressDialog.show();
        //  ProgressBar progressBar = new ProgressBar(MainActivity, null,
        //        android.R.attr.progressBarStyleSmall);
        // progressBar.dismis
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_SEND_POST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

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
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Do not leave the post blank" + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("post", post);
                params.put("username", username);
                params.put("timeofpost", timeofpost);
                params.put("dateofpost", dateofpost);


                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    public void getTheCurrentUser() {

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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQue = Volley.newRequestQueue(this);
        requestQue.add(stringRequest);
    }

    private void showJSON(String response) {
        String mId= "";
        String username = "";


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
            JSONObject userData = result.getJSONObject(0);
            mId = userData.getString(userID);
            username = userData.getString(usernameUP);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //userID2 = id.toString();
        //username2 = username.toString();

        UserCurrent userCurrent = new UserCurrent();
        //userCurrent.setmIdCurrent(mId);
        userCurrent.setId2(mId);
        SharedPrefManager.getInstance(this).setCurrtUserID(mId);


       // Toast.makeText(this, userCurrent.getmIdCurrent().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, mId, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, userCurrent.getId2() + "from UC", Toast.LENGTH_SHORT).show();
    }

}
