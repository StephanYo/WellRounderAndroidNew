package com.example.admin.wellrounder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;

import android.os.AsyncTask;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by admin on 11/18/2017.
 */

public class Tab2Fragment extends Fragment {

    private static final String TAG = "Tab2Fragment";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<StockItemList> listItems;

    private EditText etSearchBar;

    private TextView tv5Days, tv10Days, tv1Month, tv3Months, tv6Months, tv1Year, tvFull, tvTickerTop;

    private GraphView graphView;

    private SwipeRefreshLayout mSwipeLayout;

    private Button btnLoadData;
    public static TextView tvTab2Test;

    private String tickerName;

    List<DataPoint> dataPoints = new ArrayList<DataPoint>();
    List<Integer> arrayRealPrice = new ArrayList<Integer>();
    List<Double> arraymRealPRice = new ArrayList<Double>();
    List<Date> arrayRealDate = new ArrayList<Date>();
    List<Date> arraymRealDate = new ArrayList<Date>();

    public String mURL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerViewTab2);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshTab2);

        graphView = (GraphView) view.findViewById(R.id.graphTab2);

        etSearchBar = (EditText) view.findViewById(R.id.etSearchBarTab2);

        listItems = new ArrayList<>();

        tvTickerTop = (TextView) view.findViewById(R.id.tvTickerTopTab2);

        tv5Days = (TextView) view.findViewById(R.id.tv5DaysTab2);
        tv5Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(5);
            }
        });


        tv1Month = (TextView) view.findViewById(R.id.tv1MonthTab2);
        tv1Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(31);
            }
        });

        tv6Months = (TextView) view.findViewById(R.id.tv6MonthTab2);
        tv6Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(186);
            }
        });
        tv1Year = (TextView) view.findViewById(R.id.tv1YearhTab2);
        tv1Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(365);
            }
        });
        tvFull = (TextView) view.findViewById(R.id.tvFullDataTab2);
        tvFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(arrayRealDate.size());
            }
        });

        btnLoadData = (Button) view.findViewById(R.id.btnLoadDataInvestTab2);
       // tvTab2Test = (TextView) view.findViewById(R.id.tvTabTest2);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mURL = etSearchBar.getText().toString();
                FetchData process = new FetchData();
                process.execute();
                Log.d("LukeStockTest", "It has begun executing");

            }
        });

        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etSearchBar.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please do not leave search empty", Toast.LENGTH_LONG).show();
                } else {
                    mURL = etSearchBar.getText().toString();
//                for (int i = 0; i < 10; i++) {
//                    StockItemList item = new StockItemList(
//                            "Header" + (i + 1),
//                            "Body",
//                            "username",
//                            "Time",
//                            "date",
//                            "asdf",
//                            "asdf",
//                            "qerqwer"
//                    );
//                    listItems.add(item);
//                }
//                adapter = new StockAdapterTab1(listItems, getContext());
//                recyclerView.setAdapter(adapter);


                    FetchData process = new FetchData();
                    process.execute();
                    mSwipeLayout.setRefreshing(true);


                }
                // Toast.makeText(getContext(), "You clicked the button at leaset", Toast.LENGTH_SHORT).show();
            }
        });


        Log.d(TAG, "onCreate: Starting.");

        return view;
    }

    public void loadGraph(Integer numDays){

        graphView.removeAllSeries();

        if(numDays> arrayRealDate.size()){
            numDays = arrayRealDate.size();
        }
        Log.d("Tab1GraphTest", arraymRealPRice.size() + " date array" + arrayRealDate.size());
        DataPoint[] dp = new DataPoint[numDays];
        for (int i = dp.length -1; i>=0; i--)  {
//            if(i ==0 ){
//                //Date realDate = arraymRealDate.get(i);
//                Integer realPrice = arrayRealPrice.get(i);
//
//                dp[i] = new DataPoint(i, realPrice );
//            }
            //Date realDate = arraymRealDate.get(i + 5);
            Integer realPrice = arrayRealPrice.get(i);

            dp[i] = new DataPoint(i, realPrice );
        }

        Log.d("Tab1GraphTest", arrayRealDate.get(0).toString());
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setDrawDataPoints(true);
        series.setTitle(tickerName + "Price");
        series.setDrawBackground(true);
        series.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


        graphView.addSeries(series);

    }

    public class FetchData extends AsyncTask<Void, Void, Void> {

        StringBuilder sB = new StringBuilder();
        StringBuilder parsedData = new StringBuilder();
        String singleParsed = "";
        List<JSONObject> list;
        String mTicker = etSearchBar.getText().toString();

        HashMap<String, String> hashMap = new HashMap<String, String>();

        private Date yesterday(int i) {
            final Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DATE, -i);

            return cal.getTime();

        }



        //@SuppressWarnings("")


        protected Void doInBackground(Void... voids) {

            Log.d(TAG, "In THe bg");
            //Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            DateFormat dateFormatNew = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            Date today = new Date();
            DecimalFormat df = new DecimalFormat("0.##");
            System.out.println("today is " + today);

            arraymRealDate.clear();
            arrayRealDate.clear();
            arraymRealPRice.clear();
            arrayRealDate.clear();


            try {

                Log.d(TAG, "In THe try");

                String URLp1 = "https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=";

                String URLp2 = mTicker + "&market=CNY&apikey=8KEK2G31V89Q5VYG";

                URL url = new URL(URLp1 + URLp2);

                Log.d("Tab2Fragment", url.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";


                while (line != null) {

                    Log.d(TAG, "In THe While");


                    line = bufferedReader.readLine();


                    sB.append(line);


                }
                //JSONObject OBJ = new JSONObject(data);

                Log.d(TAG, "In THe out the while");

                JSONObject OBJ = new JSONObject(sB.toString());

                Log.d(TAG, sB.toString());


                JSONObject resp = (JSONObject) OBJ.get("Time Series (Digital Currency Daily)");

                Log.d("Tab2Frag", "After The while");

                //to add the dates into array

                // JSONArray JAParsed = new JSONArray();
                list = new ArrayList<>();
                // go through dates, skip weekends;
                listItems.clear();
               // Toast.makeText(getContext(), resp.length(), Toast.LENGTH_SHORT).show();
                System.out.print(resp.length());
                for (int i = 0; (i < resp.length() * 2); i++) {

                    if (resp.has(dateFormat.format(yesterday(i)))) {

                        JSONObject parsedDay = (JSONObject) resp.get(dateFormat.format(yesterday(i)));
                        list.add(parsedDay);

                        hashMap.put(dateFormat.format(yesterday(i)), parsedDay.get("4b. close (USD)").toString());
                        Date realDate = yesterday(i);
                        String stringDate = yesterday(i).toString();
                        //Date thedate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).parse(stringDate);


                        String price = parsedDay.get("4b. close (USD)").toString();
                        Log.d("TAB" , price.toString());
                        Double mPrice = Double.parseDouble(price);
                        //df.format(mPrice);
                        Integer realPrice = mPrice.intValue();

                        arrayRealDate.add(realDate);
                        Double doublePrice = Double.parseDouble(df.format(mPrice));
                        arraymRealPRice.add(doublePrice);
                        arrayRealPrice.add(realPrice);
                        arraymRealPRice.add(mPrice);


                        Log.d("Tab2FragmentRealPrice", realPrice.toString());

                        dataPoints.add(new DataPoint(realDate, realPrice));

                        StockItemList stockItemList = new StockItemList(
                                hashMap,
                                mTicker,
                                dateFormat.format(yesterday(i)),
                                parsedDay.get("1b. open (USD)").toString(),
                                parsedDay.get("2. high").toString(),
                                parsedDay.get("3. low").toString(),
                                parsedDay.get("4. close").toString(),
                                parsedDay.get("5. adjusted close").toString(),
                                parsedDay.get("6. volume").toString(),
                                parsedDay.get("7. dividend amount").toString(),
                                parsedDay.get("8. split coefficient").toString()

                        );
                        listItems.add(stockItemList);


                        Log.d("Tab2Fragment", "in the for loop if statement");
                        Log.d("Tab2Fragment", parsedDay.get("1b. open (USD)").toString() + " " + parsedDay.get("8. split coefficient"));
                        //Toast.makeText(getContext(), listItems.size(), Toast.LENGTH_SHORT).show();
                    }
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mSwipeLayout.setRefreshing(false);

            super.onPostExecute(aVoid);
            //activate which one you want to see in the console
            // MainActivity.data.setText(this.list.toString());
            //Tab2Fragment.tvTab2Test.setText(this.parsedData);

            adapter = new StockAdapterTab1(listItems, getContext());

            recyclerView.setAdapter(adapter);


//            DataPoint[] points = new DataPoint[10];
//            for(int i = 0; i<points.length; i++ ){
//                if(hashMap.containsKey(yesterday(i))){
//
//
//                    Date realDate = yesterday(i);
//                    String price = hashMap.get(yesterday(i));
//                    Integer realPrice = Integer.parseInt(price);
//                    points[i] = new DataPoint(realDate, realPrice);
//                }
//
//                //points[i] = new DataPoint(i, i+1 );
//            }
//            //mSwipeLayout.setRefreshing(false);

            DataPoint[] dp = new DataPoint[5];
            for (int i = dp.length -1; i>=0; i--) {


//                if(i == 0){
//                    //Date realDate = arraymRealDate.get(i);
//
//                    Integer realPrice = arrayRealPrice.get(i);
//
//                    dp[i] = new DataPoint(i, realPrice );
//                }
                //Date realDate = arraymRealDate.get(i+5);

                Integer realPrice = arrayRealPrice.get(i);

                dp[i] = new DataPoint(i+1, realPrice );
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            series.setDrawDataPoints(true);
            series.setTitle(mTicker + "Price");
            series.setDrawBackground(true);
            series.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tickerName = mTicker;

            TextView tvTickerTop = (TextView) ((Activity)getContext()).findViewById(R.id.tvTickerTopTab1);
            tvTickerTop.setText(mTicker + ": " + arrayRealDate.get(0).toString());


            graphView.getViewport().scrollToEnd();


            graphView.addSeries(series);

            //graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            //graphView.getGridLabelRenderer().setNumHorizontalLabels(5);

            //graphView.getGridLabelRenderer().setHumanRounding(false);


            // int printThis = listItems.size();

            Log.d("Tab2Fragment", "In the onPostExecute");


        }


    }

    public String getmURL() {
        return mURL;
    }
}

