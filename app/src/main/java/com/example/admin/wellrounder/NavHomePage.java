package com.example.admin.wellrounder;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
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
import android.widget.Toast;

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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by admin on 9/17/2017.
 * <p>
 * Not used atm. Suppose to the fragment home page.
 */

public class NavHomePage extends android.support.v4.app.Fragment {




    private SwipeRefreshLayout mSwipeLayout;

    private EditText etSearchBarExplore;

    private Button btnSearchExplore;


    private List<NavHomePage.RssFeedModel> mFeedModelList;

    private RecyclerView mRecyclerView;


    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    private Button btnLoadRss;

    public RSSObject rssObject;

    private final String RSS_Link = "http://rss.nytimes.com/services/xml/rss/nyt/Business.xml";

    private final String RSS_to_JSON = "https://api.rss2json.com/v1/api.json?rss_url=";

    private static final String TAG = "NavHomePage";

    private SectionPageAdapter mSectionPageAdapter;

    private ViewPager mViewPager;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_home, container
                , false);


        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshNavHome);
        /**
         *
         */



        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerView.setHasFixedSize(true); //means every one has a fixed size
        //recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        listItems = new ArrayList<>();

        // loadRecyclerViewData();

        //ReadRss readRss = new ReadRss(getContext());
        //readRss.execute();

        new FetchFeedTask().execute((Void) null);

        Log.d(TAG, "onCreate: Starting.");
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new NavHomePage.FetchFeedTask().execute((Void) null);
            }
        });


        mSectionPageAdapter = new SectionPageAdapter(getFragmentManager());

       // mViewPager = (ViewPager) view.findViewById(R.id.container);
       // setUpViewPager(mViewPager);

//        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);
  //      tabLayout.setupWithViewPager(mViewPager);
       // loadRSS();

        /**
         * this breaks the home page
         *//*
        btnLoadRss = (Button) view.findViewById(R.id.btnLoadRSSData);
        btnLoadRss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                loadRSS();
            }
        });*/

         /*
         uncomment for home page to work w out recycler view, make sure u add it back to the home page fragment
          */
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        FragmentManager fm = getFragmentManager();




/*
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshNavHome);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);

                    }
                },430);
            }
        });
*/


        return view;
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink = "https://www.investopedia.com/feedbuilder/feed/getfeed/?feedName=rss_articles";

        @Override
        protected void onPreExecute() {
            mSwipeLayout.setRefreshing(true);
            urlLink = "https://www.investopedia.com/feedbuilder/feed/getfeed/?feedName=rss_articles";
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
                mRecyclerView.setAdapter(new NavHomePageAdapter(mFeedModelList, getContext()));
            } else {
                Toast.makeText(getContext(),
                        "Enter a valid Rss feed url",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public List<NavHomePage.RssFeedModel> parseFeed(InputStream inputStream) throws XmlPullParserException,
            IOException {
        String title = null;
        String link = null;
        String description = null;
        String pubDate = null;
        String mediadescription = null;
        String enclosure = null;
        boolean isItem = false;
        List<NavHomePage.RssFeedModel> items = new ArrayList<>();

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
                    Log.d("MyXmlParserPubDate", result);
                    pubDate = result;
                    // } else if(mediadescription.equalsIgnoreCase("media:content ")){
                    //     mediadescription = result;
                    //     Toast.makeText(getContext(), mediadescription, Toast.LENGTH_SHORT).show();
                }else if(name.equalsIgnoreCase("enclosure")){
                    Log.d("MyXmlParserEnclosure", result);
                    enclosure = result;
                }


                if (title != null && link != null && description != null && pubDate != null) {
                    if(isItem) {
                        NavHomePage.RssFeedModel item = new NavHomePage.RssFeedModel(title, link, description, pubDate, enclosure);
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
        public String enclosure;

        public RssFeedModel(String title, String link, String description, String pubDate, String enclosure) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.pubDate = pubDate;
            this.enclosure = enclosure;
        }
    }

/**
 * This is the old borken news feed, g00d stuff.
 */

//    public void loadRecyclerViewData() {
//        //  progressDialog2.setMessage("Retrieving Data, Please Be Patient");
//        //progressDialog2.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET,
//                Constants.URL_GET_ALL_POSTS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // progressDialog.dismiss();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray array = jsonObject.getJSONArray("post");
//
//                            for (int i = 0; i < array.length(); i++) {
//                                JSONObject mJson = array.getJSONObject(i);
//                                ListItem listItem = new ListItem(
//                                        mJson.getString("id"),
//                                        mJson.getString("post"),
//                                        mJson.getString("username"),
//                                        mJson.getString("timeofpost"),
//                                        mJson.getString("dateofpost")
//                                );
//                                listItems.add(listItem);
//                            }
//
//                            adapter = new MyAdapter(listItems, getContext());
//                            recyclerView.setAdapter(adapter);
//
//                        } catch (JSONException e) {
//
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //progressDialog.dismiss();
//                Toast.makeText(getContext(), "Something Went Wrong, try Again", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);
//    }
//
//    private void loadRSS() {
//        AsyncTask<String, String, String> loadRSSAsync = new AsyncTask<String, String, String>() {
//            ProgressDialog mDialog = new ProgressDialog(getContext());
//
//            @Override
//            protected void onPreExecute() {
//                mDialog.setMessage("Loading Data Bruh...");
//                mDialog.show();
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                String result;
//                HTTPDataHandler http = new HTTPDataHandler();
//                result = http.GetHTTPData(params[0]);
//                return result;
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                mDialog.dismiss();
//
//                rssObject = new Gson().fromJson(s, RSSObject.class);
//                RssFeedAdapter adapter = new RssFeedAdapter(rssObject, getContext());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//        };
//
//        StringBuilder url_get_data = new StringBuilder(RSS_to_JSON);
//        url_get_data.append(RSS_Link);
//        loadRSSAsync.execute(url_get_data.toString());
//    }
//
//
//    public void setUpViewPager(ViewPager viewPager) {
//        SectionPageAdapter mAdapter = new SectionPageAdapter(getFragmentManager());
//        mAdapter.addFragment(new Tab1Fragment(), "TAB1");
//        mAdapter.addFragment(new Tab2Fragment(), "TAB2");
//        mAdapter.addFragment(new Tab3Fragment(), "TAB3");
//        viewPager.setAdapter(mAdapter);
//    }
//

}







