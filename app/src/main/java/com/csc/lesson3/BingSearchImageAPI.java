package com.csc.lesson3;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by JV on 12.03.2016.
 */
public class BingSearchImageAPI {

    private static final String ACCOUNT_KEY = "92yEoIpfhAzqXEptiLj03R34k61DcrMUBUrmvupE+vs";
    private static final int NUM_OF_IMAGES = 9;

    protected static ArrayList<String> retrieveResponse(final URL url) {

        String accountKeyEnc = Base64.encodeToString((ACCOUNT_KEY + ":" + ACCOUNT_KEY).getBytes(), Base64.NO_WRAP);

        final URLConnection connection;
        try {
            connection = url.openConnection();

            connection.setRequestProperty("Authorization", "Basic " + accountKeyEnc);

            final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            final StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            return parseJSON(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<String> parseJSON(final String jsonString){
        JSONObject json;

        try {
            json = new JSONObject(jsonString);
            JSONObject responseObject = json.getJSONObject("d");
            JSONArray resultArray = responseObject.getJSONArray("results");

            return getImageList(resultArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<String> getImageList(JSONArray resultArray)
    {
        ArrayList<String> listImages = new ArrayList<>();

        try
        {
            for(int i = 0; i < resultArray.length() && i < NUM_OF_IMAGES; ++i)
            {
                JSONObject obj;
                obj = resultArray.getJSONObject(i);

                listImages.add(obj.getString("MediaUrl"));
            }
            return listImages;
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
