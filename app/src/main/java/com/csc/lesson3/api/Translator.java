package com.csc.lesson3.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by anastasia on 14.03.16.
 */
public class Translator {
    private static final String KEY = "trnsl.1.1.20160313T231853Z.7b5d43e3949d1c16.652b70f0a8206f69a8613a86c5c305ffe857b6ca";
    private static final String HOME_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String DIR = "ru-en";

    public String translate(String word) {
        try {
            URL url = new URL(HOME_URL + "?key=" + URLEncoder.encode(KEY) + "&text=" + word + "&lang=" + DIR);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type","text/plain; charset=" + "UTF-8");
            httpConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpConnection.connect();
            int rc = httpConnection.getResponseCode();

            if (rc == 200) {
                String line;
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                StringBuilder strBuilder = new StringBuilder();
                while ((line = buffReader.readLine()) != null) {
                    strBuilder.append(line + '\n');
                }
                return parseTranslationFromJSON(strBuilder.toString());
            }
            return "Oops";
        } catch (Exception e) {
            return "Oops";
        }
    }

    public static String parseTranslationFromJSON(String str) {
        try {
            JSONObject object = new JSONObject(str);
            JSONArray array = (JSONArray) object.get("text");
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < array.length(); ++i) {
                sb.append(array.get(i).toString()).append(" ");
            }

            return sb.toString();
        } catch (Exception e) {
            return "Oops oops";
        }
    }
}
