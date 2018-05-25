package com.example.admin.wellrounder;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class StockTest extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<TestStockList> mStockList;
    private ProgressDialog progressDialog;

    private TextView tvStockClose;
    public String stockPrice;

    public String newUrl = Constants.URL_GET_ALL_POSTS;

    public String url = "https://www.quandl.com/api/v3/datasets/WIKI/AAPL.json?start_date=1985-05-01&end_date=1997-07-01&order=asc&column_index=4&collapse=quarterly&transformation=rdiff";
    public String URL2 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=MSFT&apikey=1CVGJPOE3X2ADTG6";
    public String url4 = "https://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json";

    public String xiginteURL = "https://globalquotes.xignite.com/v3/xGlobalQuotes.json/GetGlobalExtendedQuote?IdentifierType=Symbol&Identifier=BAC&_token=00280AD7ACF342B8A53C54903981C1F2";
    public String xiginteURL2= "https://globalquotes.xignite.com/v3/xGlobalQuotes.json/GetTopMarketMovers?MarketMoverType=PercentGainers&NumberOfMarketMovers=5&Exchanges=XNYS,ARCX&_token=00280AD7ACF342B8A53C54903981C1F2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_test);

        progressDialog = new ProgressDialog(this);

        recyclerView = (RecyclerView) findViewById(R.id.rvRecyclerViewStock);
        recyclerView.setHasFixedSize(true); //means every one has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        tvStockClose = (TextView) findViewById(R.id.tvStockClose);

        mStockList = new ArrayList<>();


        //loadRecyclerViewData();
        // getCurrenciesNew();
        tvStockClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testXignite();
                //testGetUserName();

                //usernameTest(newUrl);
                //       getCurry(url4);
                //parseData2(URL2);
                //parseData(URL2);
                //  Toast.makeText(StockTest.this, stockPrice +  " - " + getYesterDayString(), Toast.LENGTH_SHORT).show();
                //tvStockClose.setText(stockPrice);
            }
        });

        //loadRecyclerViewData();
        //  parseData(URL2);
        //parseData2(URL2);


        progressDialog.setMessage("Loading Data...");

//         for (int i = 0; i < 10; i++) {
//             TestStockList item = new TestStockList(
//                  "stockTicker" + (i + 1)
////                  "stockName",
////                  "stockPrice",
////                  "stockLow",
////                  "stockHigh"
//
//          );
//          mStockList.add(item);
//      }
//      mAdapter = new TestMyAdapterStockList(mStockList, getApplicationContext());
//
//      recyclerView.setAdapter(mAdapter);

    }

    public void testXignite() {

        progressDialog.setMessage("Retrieving Data, Please Be Patient");
        progressDialog.show();
        // Toast.makeText(getApplicationContext(), "1 :D", Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                xiginteURL2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //    Toast.makeText(getApplicationContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {

                                /*
                                This works below
                                 */
                            //JSONObject responseJson = new JSONObject(response);

                            // String closePrice = responseJson.getString("Close");

                            // tvStockClose.setText(closePrice);

                            // Toast.makeText(StockTest.this, closePrice.toString(), Toast.LENGTH_SHORT).show();


                            //       Toast.makeText(getApplicationContext(), "3:D", Toast.LENGTH_SHORT).show();
                            //-------------
                            mStockList.clear();
                            JSONObject responseJson = new JSONObject(response);

                            JSONArray array = responseJson.getJSONArray("Movers");


                            for (int i = 0; i < 10; i++) {
                                JSONObject mJson = array.getJSONObject(i);
                                //JSONObject fieldsJson = mJson.getJSONObject("fields");
                                TestStockList testStockList = new TestStockList(
                                        mJson.getString("Symbol"),
                                        mJson.getString("Last")
//                                        mJson.getString("database_code"),
//                                        mJson.getString("name"),
//                                        mJson.getString("oldest_available_date")
                                        //mJson.getString("post_id")
                                );
                                mStockList.add(testStockList);
                                Toast.makeText(getApplicationContext(), mJson.getString("price"), Toast.LENGTH_SHORT).show();
                            }


                            mAdapter = new TestMyAdapterStockList(mStockList, getApplicationContext());

                            recyclerView.setAdapter(mAdapter);
                            progressDialog.dismiss();


                        } catch (JSONException e) {

                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        Toast.makeText(getApplicationContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }


    public void loadRecyclerViewDataTest() {
        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
        //progressDialog2.show();
        Toast.makeText(getApplicationContext(), "1 :D", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url4,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        // Toast.makeText(getContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {
                            //Toast.makeText(getContext(), "3:D", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "3 :D", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);


                            JSONArray array = jsonObject.getJSONArray("resources");
                            JSONObject specificJSON = array.getJSONObject(7);
                            JSONObject resourceJSON = specificJSON.getJSONObject("resource");
                            String testYo = resourceJSON.getString("classname");

                            JSONObject fieldsJson = resourceJSON.getJSONObject("fields");
                            String priceOfCurrency = fieldsJson.getString("price");

                            //String usernameTest =  mJson.getString("post");
                            Toast.makeText(StockTest.this, testYo.toString() + " - " + priceOfCurrency.toString(), Toast.LENGTH_SHORT).show();
//                            //listItems.clear();/*
//                            for (int i = array.length()-1; i >= 0; i--) {
//                                JSONObject mJson = array.getJSONObject(i);
//                                ListItem listItem = new ListItem(
//                                        mJson.getString("id"),
//                                        mJson.getString("post"),
//                                        mJson.getString("username"),
//                                        mJson.getString("timeofpost"),
//                                        mJson.getString("dateofpost")
//                                        //mJson.getString("post_id")
//                                );
//                                listItems.add(listItem);
//                            }
//
//                            adapter = new MyAdapter(listItems, getContext());
//
//                            recyclerView.setAdapter(adapter);*/

                            //Toast.makeText(getContext(), "4 :D", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getApplicationContext(), "4 :D", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "5 :D", Toast.LENGTH_SHORT).show();

                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                //Toast.makeText(getContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        Toast.makeText(getApplicationContext(), "7 :D", Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }

/*

    public void getCurrencies(String response){

        try {
            JSONObject responseJson = new JSONObject(response);
            JSONObject

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
*/


//    /*public void loadRecyclerViewData() {
//        progressDialog.setMessage("Retrieving Data, Please Be Patient");
//        progressDialog.show();
//        Toast.makeText(getApplicationContext(), "1 :D", Toast.LENGTH_SHORT).show();
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                URL2,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Toast.makeText(getApplicationContext(), "2 :D", Toast.LENGTH_SHORT).show();
//                        try {
//                            Toast.makeText(getApplicationContext(), "3:D", Toast.LENGTH_SHORT).show();
//                            mStockList.clear();
//                            JSONObject jsonObject = new JSONObject(response);
//
//                            JSONArray array = jsonObject.getJSONArray("list");
//
//                            JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
//                            JSONObject specificDate = timeSeries.getJSONObject("2017-11-03");
//                            String addThisnew = specificDate.getString("4. close");
//                            tvStockClose.setText(addThisnew);
//                            Toast.makeText(StockTest.this, addThisnew, Toast.LENGTH_SHORT).show();
//
//                            String addThis = jsonObject.getJSONObject("20171102").getString("close");
//                            tvStockClose.setText(addThis);
//
//                            TestStockList testStockList = new TestStockList(addThis);
//                            mStockList.add(testStockList);
//
//                            Toast.makeText(StockTest.this, addThis, Toast.LENGTH_SHORT).show();
//
////                           for (int i = array.length()-1; i >= array.length() - 10; i--) {
////                                JSONObject mJson = array.getJSONObject(i);
////                                TestStockList testStockList = new TestStockList(
////                                        mJson.getString("name")//,
//////                                        mJson.getString("dataset_code"),
//////                                        mJson.getString("database_code"),
//////                                        mJson.getString("name"),
//////                                        mJson.getString("oldest_available_date")
////                                        //mJson.getString("post_id")
////                                );
////                                mStockList.add(testStockList);
////                            }
//
//
//                            mAdapter = new TestMyAdapterStockList(mStockList, getApplicationContext());
//
//                            recyclerView.setAdapter(mAdapter);
//                            progressDialog.dismiss();
//
//                            Toast.makeText(getApplicationContext(), "4 :D", Toast.LENGTH_SHORT).show();
//
//
//                        } catch (JSONException e) {
//
//                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "6 :D", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//        Toast.makeText(getApplicationContext(), "7 :D", Toast.LENGTH_SHORT).show();
//    }*/


    public void parseData(String response) {
        //response = URL2;
        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONObject time = jsonObject.getJSONObject("Time Series (Daily)");
            Iterator<String> iterator = time.keys();

            while (iterator.hasNext()) {
                String date = iterator.next().toString();
                JSONObject dateJson = time.getJSONObject(date);
                String close = dateJson.getString("4. close");
                Log.d("data", "4. close-" + close);
                Toast.makeText(getApplicationContext(), close.toString(), Toast.LENGTH_SHORT).show();
                tvStockClose.setText(close.toString());
                stockPrice = close.toString();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        return cal.getTime();
    }

    private String getYesterDayString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(yesterday());
    }

    private void parseData2(String response) {
        try {
            JSONObject responseJson = new JSONObject(response);
            JSONObject timeSeriesJson = responseJson.getJSONObject("Time Series (Daily)");
            JSONObject dailyObject = timeSeriesJson.getJSONObject(getYesterDayString());
            String closePrice = dailyObject.getString("4. Close");
            Log.d("closePrice", closePrice);
            //tvStockClose.setText(closePrice.toString());
            //Toast.makeText(this, closePrice + " - " + getYesterDayString(), Toast.LENGTH_SHORT).show();

            stockPrice = closePrice.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


//    public void getCurrencies(String response) {
//        progressDialog.setMessage("Retrieving Data, Please Be Patient");
//        progressDialog.show();
//        try {
//            JSONObject responseJson = new JSONObject(response);
//
//            JSONArray array = responseJson.getJSONArray("resources");
//
//
//            for (int i = array.length() - 1; i >= array.length() - 10; i--) {
//                JSONObject mJson = array.getJSONObject(i);
//                //JSONObject fieldsJson = mJson.getJSONObject("fields");
//                TestStockList testStockList = new TestStockList(
//                        mJson.getString("price")//,
////                                        mJson.getString("dataset_code"),
////                                        mJson.getString("database_code"),
////                                        mJson.getString("name"),
////                                        mJson.getString("oldest_available_date")
//                        //mJson.getString("post_id")
//                );
//                mStockList.add(testStockList);
//                Toast.makeText(getApplicationContext(), mJson.getString("price"), Toast.LENGTH_SHORT).show();
//            }
//
//
//            mAdapter = new TestMyAdapterStockList(mStockList, getApplicationContext());
//
//            recyclerView.setAdapter(mAdapter);
//            progressDialog.dismiss();
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            progressDialog.dismiss();
//            Toast.makeText(this, "darn it did not work ):", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void usernameTest(String response) {
        try {
            JSONObject responseJson = new JSONObject(response);
            JSONArray JSONarray = responseJson.getJSONArray("posts");
            String testUsername = JSONarray.getJSONObject(7).getString("post");
            tvStockClose.setText(testUsername);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "nope soz cuz", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCurry(String response) {

        try {
            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
            JSONObject responseObject = new JSONObject(response);
            JSONArray resourcesArray = responseObject.getJSONArray("resources");
            JSONObject mJson = resourcesArray.getJSONObject(0);
            String specificPrice = mJson.getString("name");
            tvStockClose.setText(specificPrice.toString());
            Toast.makeText(getApplicationContext(), specificPrice.toString(), Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "nope soz", Toast.LENGTH_SHORT).show();
        }

    }

//    public void getCurrenciesNew() {
//        progressDialog.setMessage("Retrieving Data, Please Be Patient");
//        progressDialog.show();
//        // Toast.makeText(getApplicationContext(), "1 :D", Toast.LENGTH_SHORT).show();
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                url4,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //    Toast.makeText(getApplicationContext(), "2 :D", Toast.LENGTH_SHORT).show();
//                        try {
//                            //       Toast.makeText(getApplicationContext(), "3:D", Toast.LENGTH_SHORT).show();
//                            mStockList.clear();
//                            JSONObject responseJson = new JSONObject(response);
//
//                            JSONArray array = responseJson.getJSONArray("resources");
//
//
//                            for (int i = 0; i < 10; i++) {
//                                JSONObject mJson = array.getJSONObject(i);
//                                JSONObject fieldsJson = mJson.getJSONObject("fields");
//                                TestStockList testStockList = new TestStockList(
//                                        fieldsJson.getString("price")//,
////                                        mJson.getString("dataset_code"),
////                                        mJson.getString("database_code"),
////                                        mJson.getString("name"),
////                                        mJson.getString("oldest_available_date")
//                                        //mJson.getString("post_id")
//                                );
//                                mStockList.add(testStockList);
//                                Toast.makeText(getApplicationContext(), mJson.getString("price"), Toast.LENGTH_SHORT).show();
//                            }
//
//
//                            mAdapter = new TestMyAdapterStockList(mStockList, getApplicationContext());
//
//                            recyclerView.setAdapter(mAdapter);
//                            progressDialog.dismiss();
//
//
//                        } catch (JSONException e) {
//
//                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "6 :D", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        requestQueue.add(stringRequest);
//        Toast.makeText(getApplicationContext(), "7 :D", Toast.LENGTH_SHORT).show();
//    }

    public void testGetUserName() {


        progressDialog.setMessage("Retrieving Data, Please Be Patient");
        progressDialog.show();
        // Toast.makeText(getApplicationContext(), "1 :D", Toast.LENGTH_SHORT).show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL_GET_ALL_POSTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //    Toast.makeText(getApplicationContext(), "2 :D", Toast.LENGTH_SHORT).show();
                        try {

                            JSONObject responseJson = new JSONObject(response);

                            JSONArray arrayJson = responseJson.getJSONArray("posts");

                            String usernameTest = arrayJson.getJSONObject(7).getString("post");

                            Toast.makeText(StockTest.this, usernameTest, Toast.LENGTH_SHORT).show();

                            tvStockClose.setText(usernameTest);


                             /*   //       Toast.makeText(getApplicationContext(), "3:D", Toast.LENGTH_SHORT).show();
                                mStockList.clear();
                                JSONObject responseJson = new JSONObject(response);

                                JSONArray array = responseJson.getJSONArray("resources");


                                for (int i = 0; i <10; i++) {
                                    JSONObject mJson = array.getJSONObject(i);
                                    JSONObject fieldsJson = mJson.getJSONObject("fields");
                                    TestStockList testStockList = new TestStockList(
                                            fieldsJson.getString("price")//,
//                                        mJson.getString("dataset_code"),
//                                        mJson.getString("database_code"),
//                                        mJson.getString("name"),
//                                        mJson.getString("oldest_available_date")
                                            //mJson.getString("post_id")
                                    );
                                    mStockList.add(testStockList);
                                    Toast.makeText(getApplicationContext(), mJson.getString("price"), Toast.LENGTH_SHORT).show();*/
                            //}


                            mAdapter = new TestMyAdapterStockList(mStockList, getApplicationContext());

                            recyclerView.setAdapter(mAdapter);
                            progressDialog.dismiss();


                        } catch (JSONException e) {

                            //Toast.makeText(getContext(), "5 :D", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "6 :D", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        Toast.makeText(getApplicationContext(), "7 :D", Toast.LENGTH_SHORT).show();
    }


}
