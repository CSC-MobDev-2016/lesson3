package com.csc.lesson3.services;

import android.util.Base64;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Филипп on 13.03.2016.
 */
public class BingImages {
    private final static String KEY = "kzyzCVH0yTzX1QgdGArR4FCgm6TQigCij4aWcSYAmUY";
    private final static String BING_URL = "https://api.datamarket.azure.com/Bing/Search/v1/Image?";
    private final static int count = 15;
    private final static String LOG_TAG = "BING_IMAGES";

    public List<String> getImages(String word) {
        try {
            URL url = new URL(BING_URL + "Query='" + URLEncoder.encode(word) + "'"  +
                    "&$format=json" + "&$top=" + count);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type","text/plain; charset=" + "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            Log.d(LOG_TAG, Base64.encodeToString((":" + KEY).getBytes(), Base64.DEFAULT));
            connection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((":" + KEY).getBytes(), Base64.DEFAULT));
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            StringBuilder buffer = new StringBuilder();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String result = buffer.toString();
            Log.d(LOG_TAG, result);

            JSONObject dataJsonObj = new JSONObject(result);
            JSONArray results = dataJsonObj.getJSONObject("d").getJSONArray("results");

            List<String> images = new ArrayList<>();
            for (int i = 0; i < results.length(); ++i) {
                images.add(results.getJSONObject(i).getString("MediaUrl"));
            }
            return images;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
