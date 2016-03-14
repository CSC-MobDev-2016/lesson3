package com.csc.lesson3;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ImageViewTask extends AsyncTask<Object, Void, List<String>> {

    @Override
    protected List<String> doInBackground(Object... params) {

        List<String> images = new ArrayList<>();
        try {
            String[] encodedArgs = new String[params.length];
            for (int i = 0; i < params.length; i++) {
                encodedArgs[i] = URLEncoder.encode(params[i].toString(), "UTF-8");
            }

            URL url = new URL(String.format(getUrl(), encodedArgs));
            URLConnection conn = url.openConnection();
            conn.connect();

            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("hits");
            for (int i = 0; i < jsonArray.length(); i++) {
                images.add(jsonArray.getJSONObject(i)
                        .getString("webformatURL"));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return images;
    }

    private String getApiId() {
        return "2216203-7316ffb1657e3f6f888c8d313";
    }

    private String getUrl() {
        return "https://pixabay.com/api?key="
                + getApiId()
                + "&q=%s";
    }
}