package com.csc.lesson3.services;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Филипп on 13.03.2016.
 */
public class YandexTranslator {
    private final static String KEY = "trnsl.1.1.20160313T133244Z.c6c90554436620e8.f79a903e1dc638deedfbd4e6789ab25ef4623ed2";
    private final static String TRANSLATOR_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private final static String DIRECTION = "ru-en";
    private final static String LOG_TAG = "YandexTranslator";




    public String translate(String word) {
        try {
            URL url = new URL(TRANSLATOR_URL + "key=" + KEY + "&text=" + word + "&lang=" + DIRECTION);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
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
            JSONArray text = dataJsonObj.getJSONArray("text");

            String translations = "";
            for (int i = 0; i < text.length(); ++i) {
                translations += text.get(i) + " ";
            }
            return translations;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
