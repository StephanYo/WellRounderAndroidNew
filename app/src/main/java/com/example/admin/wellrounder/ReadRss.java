package com.example.admin.wellrounder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by admin on 11/1/2017.
 */

public class ReadRss extends AsyncTask<Void, Void, Void> {
    Context context;
  //  ProgressDialog progressDialog;
    String address = "http://rss.nytimes.com/services/xml/rss/nyt/Business.xml";
    URL url;

    public ReadRss(Context context) {
        this.context = context;
       // progressDialog.setMessage("Loading Data...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ProcessXml(getData());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            Log.d("Root", data.getDocumentElement().getNodeName());
        }
    }

    public Document getData(){

        try {
            url = new URL(address);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }
}
