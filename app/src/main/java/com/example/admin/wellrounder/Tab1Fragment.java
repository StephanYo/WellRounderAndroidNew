package com.example.admin.wellrounder;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
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
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by admin on 11/18/2017.
 */

public class Tab1Fragment extends Fragment {

    private static final String TAG = "Tab1Fragment";

    private RecyclerView recyclerView, mRecyclerView;
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

    private List<Tab1Fragment.RssFeedModel> mFeedModelList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerViewNewsTab1);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerViewTab1);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshTab1);

        graphView = (GraphView) view.findViewById(R.id.graphTab1);

        etSearchBar = (EditText) view.findViewById(R.id.etSearchBarTab1);

        listItems = new ArrayList<>();

        tvTickerTop = (TextView) view.findViewById(R.id.tvTickerTopTab1);

        tv5Days = (TextView) view.findViewById(R.id.tv5DaysTab1);
        tv5Days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(5);
            }
        });


        tv1Month = (TextView) view.findViewById(R.id.tv1MonthTab1);
        tv1Month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(31);
            }
        });

        tv6Months = (TextView) view.findViewById(R.id.tv6MonthTab1);
        tv6Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(186);
            }
        });
        tv1Year = (TextView) view.findViewById(R.id.tv1YearhTab1);
        tv1Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(365);
            }
        });
        tvFull = (TextView) view.findViewById(R.id.tvFullDataTab1);
        tvFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadGraph(arrayRealDate.size());
            }
        });

        btnLoadData = (Button) view.findViewById(R.id.btnLoadDataInvestTab1);

        //tvTab2Test = (TextView) view.findViewById(R.id.tvTabTest1);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


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
                    new Tab1Fragment.FetchFeedTask().execute((Void) null);



                }
                // Toast.makeText(getContext(), "You clicked the button at leaset", Toast.LENGTH_SHORT).show();
            }
        });


        Log.d(TAG, "onCreate: Starting.");

        return view;
    }

    public void loadGraph(Integer numDays) {

        Double maxDouble = 0.0;

        graphView.removeAllSeries();

        if (numDays > arrayRealDate.size()) {
            numDays = arrayRealDate.size();
        }


        Log.d("Tab1GraphTest", arraymRealPRice.size() + " date array" + arrayRealDate.size());
        DataPoint[] dp = new DataPoint[numDays];
        for (int i = dp.length - 1; i >= 0; i--) {

            if (arraymRealPRice.get(i) > maxDouble) {
                maxDouble = arraymRealPRice.get(i);

            }

//            if(i ==0 ){
//                //Date realDate = arraymRealDate.get(i);
//                Integer realPrice = arrayRealPrice.get(i);
//
//                dp[i] = new DataPoint(i, realPrice );
//            }
            //Date realDate = arraymRealDate.get(i + 5);
            Double realPrice = arraymRealPRice.get(i);

            dp[i] = new DataPoint(i, realPrice);
        }


        Log.d("Tab1GraphTest", arrayRealDate.get(0).toString());
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
        series.setDrawDataPoints(true);
        series.setTitle(tickerName + "Price");
        series.setDrawBackground(true);
        series.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        series.setDrawDataPoints(false);


        graphView.getViewport().scrollToEnd();


        graphView.getViewport().setDrawBorder(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(true);


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

            //Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            DateFormat dateFormatNew = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

            Date today = new Date();
            System.out.println("today is " + today);

            DecimalFormat df = new DecimalFormat("0.##");


            arraymRealDate.clear();
            arrayRealDate.clear();
            arraymRealPRice.clear();
            arrayRealDate.clear();


            try {

                String URLp1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=";

                String URLp2 = mTicker + "&apikey=8KEK2G31V89Q5VYG";

                URL url = new URL(URLp1 + URLp2);

                Log.d("Tab1Fragment", url.toString());

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";


                while (line != null) {


                    line = bufferedReader.readLine();


                    sB.append(line);


                }
                //JSONObject OBJ = new JSONObject(data);

                JSONObject OBJ = new JSONObject(sB.toString());

                JSONObject resp = (JSONObject) OBJ.get("Time Series (Daily)");

                Log.d("Tab1Frag", "After The while");

                //to add the dates into array

                // JSONArray JAParsed = new JSONArray();
                list = new ArrayList<>();
                // go through dates, skip weekends;
                listItems.clear();
                for (int i = 0; (i < resp.length() * 2); i++) {
                    if (resp.has(dateFormat.format(yesterday(i)))) {

                        JSONObject parsedDay = (JSONObject) resp.get(dateFormat.format(yesterday(i)));
                        list.add(parsedDay);
//
//                        parsedDay.put("Date: ", dateFormat.format(yesterday(i)));
//
//                        singleParsed = "Date : " + dateFormat.format(yesterday(i)) + "\n" +
//                                "Open : " + parsedDay.get("1. open") + "\n" +
//                                "High : " + parsedDay.get("2. high") + "\n" +
//                                "Low : " + parsedDay.get("3. low") + "\n" +
//                                "Close : " + parsedDay.get("4. close") + "\n" +
//                                "Adjusted Close : " + parsedDay.get("5. adjusted close") + "\n" +
//                                "Volume : " + parsedDay.get("6. volume") + "\n" +
//                                "Dividend Amount : " + parsedDay.get("7. dividend amount") + "\n" +
//                                "Split Coefficient : " + parsedDay.get("8. split coefficient") + "\n" + "\n";
//                        parsedData.append(singleParsed);
                        hashMap.put(dateFormat.format(yesterday(i)), parsedDay.get("4. close").toString());
                        Date realDate = yesterday(i);
                        String stringDate = yesterday(i).toString();
                        //Date thedate = new SimpleDateFormat("MM/dd/yyyy", Locale.US).parse(stringDate);


                        String price = parsedDay.get("4. close").toString();
                        Double mPrice = Double.parseDouble(price);
                        Integer realPrice = mPrice.intValue();

                        arrayRealDate.add(realDate);
                        arrayRealPrice.add(realPrice);
                        Double doublePrice = Double.parseDouble(df.format(mPrice));

                        Log.d("Tab1FragmentDate", yesterday(i).toString());
                        arraymRealPRice.add(doublePrice);
                        arraymRealPRice.add(mPrice);


                        Log.d("Tab1FragmentRealPrice", realPrice.toString());

                        dataPoints.add(new DataPoint(realDate, realPrice));

                        StockItemList stockItemList = new StockItemList(
                                hashMap,
                                mTicker,
                                dateFormat.format(yesterday(i)),
                                parsedDay.get("1. open").toString(),
                                parsedDay.get("2. high").toString(),
                                parsedDay.get("3. low").toString(),
                                parsedDay.get("4. close").toString(),
                                parsedDay.get("5. adjusted close").toString(),
                                parsedDay.get("6. volume").toString(),
                                parsedDay.get("7. dividend amount").toString(),
                                parsedDay.get("8. split coefficient").toString()

                        );
                        listItems.add(stockItemList);


                        Log.d("Tab1Fragment", "in the for loop if statement");
                        Log.d("Tab1Fragment", parsedDay.get("1. open").toString() + " " + parsedDay.get("8. split coefficient"));
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
            //Tab1Fragment.tvTab2Test.setText(this.parsedData);

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
            for (int i = dp.length - 1; i >= 0; i--) {


//                if(i == 0){
//                    //Date realDate = arraymRealDate.get(i);
//
//                    Integer realPrice = arrayRealPrice.get(i);
//
//                    dp[i] = new DataPoint(i, realPrice );
//                }
                //Date realDate = arraymRealDate.get(i+5);

                // Integer realPrice = arrayRealPrice.get(i);

                Double realPrice = arraymRealPRice.get(i);

                dp[i] = new DataPoint(i + 1, realPrice);
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dp);
            series.setDrawDataPoints(true);
            series.setTitle(mTicker + "Price");
            series.setDrawBackground(true);
            series.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            tickerName = mTicker;

            TextView tvTickerTop = (TextView) ((Activity) getContext()).findViewById(R.id.tvTickerTopTab1);
            tvTickerTop.setText(mTicker + ": " + arrayRealDate.get(0).toString());

            series.setDrawDataPoints(false);

            graphView.getViewport().scrollToEnd();

            graphView.getViewport().setDrawBorder(true);
            graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
            graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);


            graphView.addSeries(series);

            //graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
            //graphView.getGridLabelRenderer().setNumHorizontalLabels(5);

            //graphView.getGridLabelRenderer().setHumanRounding(false);


            // int printThis = listItems.size();

            Log.d("Tab1Fragment", "In the onPostExecute");


        }


    }

    public String getmURL() {
        return mURL;
    }
    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink = null;

        private String urlLinkPart1 = "https://feeds.finance.yahoo.com/rss/2.0/headline?s=";
        private String urlLinkPart2 = "&region=US&lang=en-US";
        @Override
        protected void onPreExecute() {


            mSwipeLayout.setRefreshing(true);


            urlLink = urlLinkPart1 + etSearchBar.getText().toString() + urlLinkPart2;

            if(etSearchBar.getText().toString().isEmpty()){
                urlLink = "https://www.investopedia.com/feedbuilder/feed/getfeed/?feedName=rss_articles";
            }

            Log.d("Tab1Fragment Url ", urlLink);

            //urlLink = "https://finance.yahoo.com/rss/topstories";
            // urlLink = "http://rss.nytimes.com/services/xml/rss/nyt/Business.xml";

            //etSearchBarExplore.getText().toString();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {
                if(!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))
                    urlLink = "http://" + urlLink;

                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                mFeedModelList = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } catch (XmlPullParserException e) {
                Log.e(TAG, "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mSwipeLayout.setRefreshing(false);

            if (success) {
                //mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
                //mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
                //mFeedLinkTextView.setText("Feed Link: " + mFeedLink);

                // Fill RecyclerView
                mRecyclerView.setAdapter(new InvestNewsAdapter(mFeedModelList, getContext()));


            } else {
                Toast.makeText(getContext(),
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<Tab1Fragment.RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String mediadescription = null;
        boolean isItem = false;
        List<Tab1Fragment.RssFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MyXmlParser", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }

                if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                } else if(name.equalsIgnoreCase("pubDate")){
                    pubDate = result;
                    // } else if(mediadescription.equalsIgnoreCase("media:content ")){
                    //     mediadescription = result;
                    //     Toast.makeText(getContext(), mediadescription, Toast.LENGTH_SHORT).show();
                }


                if (title != null && link != null && description != null && pubDate != null) {
                    if(isItem) {
                        Tab1Fragment.RssFeedModel item = new Tab1Fragment.RssFeedModel(title, link, description, pubDate);
                        items.add(item);
                    }
                    else {
                        // Toast.makeText(getContext(), "InCorrect Ticker", Toast.LENGTH_SHORT).show();
                        // mFeedTitle = title;
                        //mFeedLink = link;
                        //mFeedDescription = description;
                        //mPubDate = pubDate;
                    }

                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

    public class RssFeedModel {

        public String title;
        public String link;
        public String description;
        public String pubDate;

        public RssFeedModel(String title, String link, String description, String pubDate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.pubDate = pubDate;
        }
    }
}
