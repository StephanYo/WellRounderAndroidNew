package com.example.admin.wellrounder;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.List;
import java.util.Locale;


/**
 * Created by Luke Taylor on 12/20/2017.
 * <p>
 * <p>
 * Parses JSON presented by a URL and presents it nicely
 */


@SuppressWarnings("WeakerAccess")


public class fetchData extends AsyncTask<Void, Void, Void> {

    StringBuilder sB = new StringBuilder();
    StringBuilder parsedData = new StringBuilder();
    String singleParsed = "";
    List<JSONObject> list;



    private static Date yesterday(int i) {
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
        try {

            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=MSFT&apikey=demo");
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
            for (int i = 0; (i < resp.length() * 2); i++) {
                if (resp.has(dateFormat.format(yesterday(i)))) {

                    JSONObject parsedDay = (JSONObject) resp.get(dateFormat.format(yesterday(i)));
                    list.add(parsedDay);

                    parsedDay.put("Date: ", dateFormat.format(yesterday(i)));

                    singleParsed = "Date : " + dateFormat.format(yesterday(i)) + "\n" +
                            "Open : " + parsedDay.get("1. open") + "\n" +
                           "High : " + parsedDay.get("2. high") + "\n" +
                            "Low : " + parsedDay.get("3. low") + "\n" +
                            "Close : " + parsedDay.get("4. close") + "\n" +
                            "Adjusted Close : " + parsedDay.get("5. adjusted close") + "\n" +
                            "Volume : " + parsedDay.get("6. volume") + "\n" +
                            "Dividend Amount : " + parsedDay.get("7. dividend amount") + "\n" +
                            "Split Coefficient : " + parsedDay.get("8. split coefficient") + "\n" + "\n";
                    parsedData.append(singleParsed);

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
        super.onPostExecute(aVoid);
       //activate which one you want to see in the console
        // MainActivity.data.setText(this.list.toString());
        Tab1Fragment.tvTab2Test.setText(this.parsedData);


    }


}
