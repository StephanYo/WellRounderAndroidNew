package com.example.admin.wellrounder;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SpecificStockItem extends AppCompatActivity {

    private TextView tvStockTicker, tvStockPrice, tvStockDate;
    private RecyclerView mRecyclerView, recyclerView2;

    private List<SpecificStockItem.RssFeedModel> mFeedModelList;

    private RecyclerView.Adapter adapter;

    private SwipeRefreshLayout mSwipeLayout;

    private static final String TAG = "SpecificStockItem";

    private Integer alreadyOpened = 0;

    public String ticker;

    public GraphView graphview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_stock_item);

        Log.d(TAG, "onCreate: Starting.");

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshSpecificStock);

        mRecyclerView = (RecyclerView) findViewById(R.id.rvSpecificStock);

        tvStockTicker = (TextView) findViewById(R.id.tvTickerSpecificStock);
        tvStockPrice = (TextView) findViewById(R.id.tvStockPriceSpecific);
        tvStockDate = (TextView) findViewById(R.id.tvStockDateSpecific);

        graphview = (GraphView) findViewById(R.id.graphSpecificStock);



        Bundle b = getIntent().getExtras();
        HashMap<String, String> hashMap = (HashMap<String, String>)b.getSerializable("map");
        Log.v("HashMapTest", hashMap.get("2018-01-05"));



        ticker = b.getString("getTicker");
        String ticker = b.getString("getTicker");
        String date = b.getString("getDate");
        String open = b.getString("getOpen");
        String high = b.getString("getHigh");
        String low = b.getString("getLow");
        String close = b.getString("getClose");
        String adjclose = b.getString("getAdjClose");
        String volume = b.getString("getVolume");
        String divAmount = b.getString("getDivAmount");
        String splitCoe = b.getString("getSplitCoe");

        tvStockTicker.setText(ticker);
        tvStockPrice.setText("$" + close);
        tvStockDate.setText(date);


        DataPoint[] points = new DataPoint[10];
        for(int i = 0; i<points.length; i++ ){
            String mDate = yesterday(i ).toString();
            Date realDate = yesterday(i);


            points[i] = new DataPoint(realDate, i+1 );
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);





        series.setTitle("Test Curve");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setColor(Color.GREEN);

        graphview.addSeries(series);





        if(alreadyOpened == 0) {
//                for (int i = 0; i < 10; i++) {
//                    RssFeedModel item = new RssFeedModel(
//                            "Header" + (i + 1),
//                            "Body",
//                            "username",
//                            "Time"
//
//                    );
//                    mFeedModelList.add(item);
//                }
//            mRecyclerView.setAdapter(new SpecificStockAdapter(mFeedModelList, getApplicationContext()));
//
//            mRecyclerView.setAdapter(adapter);

           new SpecificStockItem.FetchFeedTask().execute((Void) null);
        }
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             //   new SpecificStockItem.FetchFeedTask().execute((Void) null);
            }
        });

    }

    private Date yesterday(int i) {
        final Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE, -i);

        return cal.getTime();

    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink = null;

        private String urlLinkPart1 = "https://feeds.finance.yahoo.com/rss/2.0/headline?s=";
        private String urlLinkPart2 = "&region=US&lang=en-US";
        @Override
        protected void onPreExecute() {


            mSwipeLayout.setRefreshing(true);


            urlLink = urlLinkPart1 + ticker + urlLinkPart2;



            Log.d("SpecificStockItem Url ", urlLink);

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
                mRecyclerView.setAdapter(new SpecificStockAdapter(mFeedModelList, getApplicationContext()));
                alreadyOpened = 1;

            } else {
                Toast.makeText(getApplicationContext(),
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<SpecificStockItem.RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String mediadescription = null;
        boolean isItem = false;
        List<SpecificStockItem.RssFeedModel> items = new ArrayList<>();

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
                        SpecificStockItem.RssFeedModel item = new SpecificStockItem.RssFeedModel(title, link, description, pubDate);
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
