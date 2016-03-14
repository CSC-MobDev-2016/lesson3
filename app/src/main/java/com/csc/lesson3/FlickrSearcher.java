package com.csc.lesson3;

/**
 * Created by chainic-vina on 14.03.16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Scanner;

/**
 * Created by chainic-vina on 14.03.16.
 */
public class FlickrSearcher extends AsyncTask<String, Void, List<String>> {

    FlickrSearcher(Context context, GridView gridView, List<String> outURLs) {
        this.gridView = gridView;
        this.context = context;
        this.outURLs = outURLs;
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        super.onPostExecute(strings);
        gridView.setAdapter(new ImageArrayAdapter(context, outURLs));
    }

    @Override
    protected List<String> doInBackground(String... text) {
        try {
            URL url = new URL(URL_rest + URLEncoder.encode(text[0], "UTF-8"));
            URLConnection urlConnection =  url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            Scanner scanner = new Scanner(reader).useDelimiter("\\A");
            StringBuilder stringBuilder = new StringBuilder();
            JSONObject response;
            if (scanner.hasNext()) {
                response = new JSONObject(scanner.next());
            } else {
                throw new IOException("Incorrect response");
            }
            reader.close();
            JSONArray jsonArray = response.getJSONArray("items");
            outURLs.clear();
            for (int i = 0; i < jsonArray.length(); ++i) {
                try {
                    outURLs.add(jsonArray.getJSONObject(i).getString("link"));
                }
                catch (Exception e) {}
            }
        } catch (MalformedURLException e1) {
//            Toast.makeText(context, "Searcher error", Toast.LENGTH_LONG).show();
        } catch (IOException e1) {
//            Toast.makeText(context, "Searcher error", Toast.LENGTH_LONG).show();
        } catch (JSONException e1) {
//            Toast.makeText(context, "Searcher error", Toast.LENGTH_LONG).show();
        }
        return outURLs;
    }

    private static final String URL_rest = "https://www.googleapis.com/customsearch/v1?key=AIzaSyATh9Q3Quk_ZQHG1FFx1nHUwRBV21Xrv4w&imgSize=small&searchType=image&cx=011397758239805712826:j16xg8qnyqs&q=";
    private GridView gridView;
    private List<String> outURLs;
    private Context context;
}