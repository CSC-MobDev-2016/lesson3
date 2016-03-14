package com.csc.lesson3.api;

import android.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anastasia on 14.03.16.
 */
public class ImageSearch {
    private static final String KEY = "3SFImejWQhyXfHDeHQ8tguQCWBCQlDpJdXrkmoSlo0c";
    private static final String HOME_URL = "https://api.datamarket.azure.com/Bing/Search/v1/Image";
    private static final String ENC = "UTF-8";
    private static final int CNT = 20;


    public List<String> getImages(String word) {
        try {
            URL url = new URL(HOME_URL + "?Query='" + URLEncoder.encode(word) + "'" + "&$format=json&$top=" + CNT);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type","text/plain; charset=" + ENC);
            httpConnection.setRequestProperty("Accept-Charset", ENC);
            httpConnection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((":" + KEY).getBytes(), Base64.DEFAULT));

            httpConnection.connect();

            String line;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line + '\n');
            }
            List<String> images = parseImagesFromJSON(strBuilder.toString());
            return images;
        } catch (Exception e) {
        }
        return null;
    }

    public static List<String> parseImagesFromJSON(String str) {
        try {
            JSONObject object = new JSONObject(str);
            JSONArray array = object.getJSONObject("d").getJSONArray("results");
            List<String> images = new ArrayList<String>();

            for (int i = 0; i < array.length(); ++i) {
                images.add(array.getJSONObject(i).getString("MediaUrl"));
            }

            return images;
        } catch (Exception e) {
        }
        return null;
    }

}
