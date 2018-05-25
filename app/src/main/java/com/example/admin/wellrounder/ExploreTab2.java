package com.example.admin.wellrounder;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.wellrounder.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by admin on 11/18/2017.
 */

public class ExploreTab2 extends Fragment {

    private static final String TAG = "ExploreTab2";

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeLayout;

    private EditText etSearchBarExplore;

    private Button btnSearchExplore;

    private Integer alreadyOpened = 0;



    private List<ExploreTab2.RssFeedModel> mFeedModelList;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Explore");

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.explore_tab_2, container
                , false);


        Log.d(TAG, "onCreate: Starting.");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerExplore);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshNavExplore);
        //etSearchBarExplore = (EditText) view.findViewById(R.id.etSearchBarExplore);
        //btnSearchExplore = (Button) view.findViewById(R.id.btnSearchNavExplore);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(alreadyOpened == 0) {
            new ExploreTab2.FetchFeedTask().execute((Void) null);
        }
//        btnSearchExplore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new ExploreTab2.FetchFeedTask().execute((Void) null);
//            }
//        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ExploreTab2.FetchFeedTask().execute((Void) null);
            }
        });


        return view;

    }
    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink = "https://www.investopedia.com/feedbuilder/feed/getfeed/?feedName=rss_stock_analysis";

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            urlLink = "https://www.investopedia.com/feedbuilder/feed/getfeed/?feedName=rss_stock_analysis";
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
                mRecyclerView.setAdapter(new ExploreAdapter2(mFeedModelList, getContext()));
                alreadyOpened = 1;

            } else {
                Toast.makeText(getContext(),
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<ExploreTab2.RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String mediadescription = null;
        String picUrl = null;
        boolean isItem = false;
        List<ExploreTab2.RssFeedModel> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                //Log.d("MyXmlP2", xmlPullParser.getText().toString());

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

                Log.d("MyXmlParser", "Parsing name ==> " + name + "AYO");
                String result = "";

                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    Log.d("MyXmlP1", result.toString());
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
                } else if(name.equals("enclosure")){
                    picUrl = result;

                }

               Log.d("Tab1Fragment PIC URL", picUrl + "AYO");

                if (title != null && link != null && description != null && pubDate != null) {
                    if(isItem) {
                        ExploreTab2.RssFeedModel item = new ExploreTab2.RssFeedModel(title, link, description, pubDate);
                        items.add(item);
                    }
                    else {
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
