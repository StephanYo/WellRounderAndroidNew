package com.example.admin.wellrounder;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


/**
 * Created by admin on 11/18/2017.
 */

public class Tab1FragmentNew extends Fragment {

    private static final String TAG = "Tab1FragmentNew";

    public LinearLayout llMainLayout;
    public EditText etSearchBar;
    public Button btnSearch;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView.Adapter adapter;

    private RecyclerView mRecyclerView;
    private List<Tab1FragmentNew.RssFeedModelNew> mFeedModelList;
    private GraphView graphView;

    HashMap<String, String> hashMap = new HashMap<String, String>();

    public Integer isDone = 0;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1fragmentnew, container, false);

        Log.d(TAG, "onCreate: Starting.");

        llMainLayout = (LinearLayout) view.findViewById(R.id.llMainLinearLayoutTab1New);
        etSearchBar = (EditText) view.findViewById(R.id.etSearchBarTab1New);
        btnSearch = (Button) view.findViewById(R.id.btnSearchTab1New);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshTab1New);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvTab1New);
        graphView = (GraphView) view.findViewById(R.id.graphTab1FramgNew);




        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etSearchBar.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Please enter a valid stock ticker ex. MSFT", Toast.LENGTH_LONG).show();
                }else {
                    mSwipeLayout.setRefreshing(true);

                    llMainLayout.setVisibility(View.VISIBLE);
                    populateGraph();




                    //new Tab1FragmentNew.FetchFeedTask().execute((Void) null);

//                            for (int i = 0; i < 10; i++) {
//            RssFeedModelNew item = new RssFeedModelNew(
//                    "Header" + (i + 1),
//                    "Body",
//                    "username",
//                    "Time"
//            );
//            mFeedModelList.add(item);
//        }
//        adapter = new Tab1FragmentNewAdapter(mFeedModelList, getContext());


                }
            }
        });


        return view;
    }


    private Date yesterday(int i) {
        final Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -i);

        return cal.getTime();

    }

    public void populateGraph(){

        Tab1FragmentNew.FetchData process = new Tab1FragmentNew.FetchData();
        process.execute();

        while(isDone == 0){
            //mSwipeLayout.setRefreshing(true);
        }

        DataPoint[] points = new DataPoint[10];
        for(int i = 0; i<points.length; i++ ){
            if(hashMap.containsKey(yesterday(i))){


                Date realDate = yesterday(i);
                String price = hashMap.get(yesterday(i));
                Integer realPrice = Integer.parseInt(price);
                points[i] = new DataPoint(realDate, realPrice);
            }

            //points[i] = new DataPoint(i, i+1 );
        }
        //mSwipeLayout.setRefreshing(false);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);





//        series.setTitle("Test Curve");
//        series.setDrawDataPoints(true);
//        series.setDataPointsRadius(10);
//        series.setColor(Color.GREEN);

        graphView.addSeries(series);

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

            Log.d("Tab1FragmentNew Url ", urlLink);

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
                mRecyclerView.setAdapter(new Tab1FragmentNewAdapter(mFeedModelList, getContext()));
                mSwipeLayout.setRefreshing(false);


            } else {
                Toast.makeText(getContext(),
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<Tab1FragmentNew.RssFeedModelNew> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String mediadescription = null;
        boolean isItem = false;
        List<Tab1FragmentNew.RssFeedModelNew> items = new ArrayList<>();

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
                        Tab1FragmentNew.RssFeedModelNew item = new Tab1FragmentNew.RssFeedModelNew(title, link, description, pubDate);
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

    public class RssFeedModelNew {

        public String title;
        public String link;
        public String description;
        public String pubDate;

        public RssFeedModelNew(String title, String link, String description, String pubDate) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.pubDate = pubDate;
        }
    }


    public class FetchData extends AsyncTask<Void, Void, Void> {

        StringBuilder sB = new StringBuilder();
        StringBuilder parsedData = new StringBuilder();
        String singleParsed = "";
        List<JSONObject> list;
        String mTicker = etSearchBar.getText().toString();



        private Date yesterday(int i) {
            final Calendar cal = Calendar.getInstance();

            cal.add(Calendar.DATE, -i);

            return cal.getTime();

        }


        //@SuppressWarnings("")


        protected Void doInBackground(Void... voids) {

            //Calendar cal = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date today = new Date();
            System.out.println("today is " + today);
            isDone = 0;

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

                //to add the dates into array

                // JSONArray JAParsed = new JSONArray();
                list = new ArrayList<>();
                // go through dates, skip weekends;
               // listItems.clear();
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

//                        StockItemList stockItemList = new StockItemList(
//                                hashMap,
//                                mTicker,
//                                dateFormat.format(yesterday(i)),
//                                parsedDay.get("1. open").toString(),
//                                parsedDay.get("2. high").toString(),
//                                parsedDay.get("3. low").toString(),
//                                parsedDay.get("4. close").toString(),
//                                parsedDay.get("5. adjusted close").toString(),
//                                parsedDay.get("6. volume").toString(),
//                                parsedDay.get("7. dividend amount").toString(),
//                                parsedDay.get("8. split coefficient").toString()
//
//                        );
//                        listItems.add(stockItemList);



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
            //mSwipeLayout.setRefreshing(false);
            isDone = 1;

            super.onPostExecute(aVoid);
            //activate which one you want to see in the console
            // MainActivity.data.setText(this.list.toString());
            //Tab1Fragment.tvTab2Test.setText(this.parsedData);


            Log.d("Tab1Fragment", "In the onPostExecute");


        }


    }


}
