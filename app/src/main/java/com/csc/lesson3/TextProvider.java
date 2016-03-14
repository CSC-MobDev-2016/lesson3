package com.csc.lesson3;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class TextProvider extends AsyncTask<String, Void, String> {
    private static final String CLIENT_ID =
            "trnsl.1.1.20160313T210103Z.36e7c3780185b662.6ff6cc439673102f1cbca936b41715133ad55688";

    private TranslationActivity activity;

    TextProvider(TranslationActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String result;
        try {
            URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + CLIENT_ID);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

            dataOutputStream.writeBytes("text=" + URLEncoder.encode(params[0], "UTF-8") + "&lang=en-ru");

            InputStream response = connection.getInputStream();
            String json = new java.util.Scanner(response).nextLine();
            result = json.substring(json.indexOf("[") + 2, json.indexOf("]") - 1);
        } catch (Exception e) {
            result = "try again";
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if (activity != null) {
            TextView textView = (TextView) activity.findViewById(R.id.translations);
            textView.setText(result);
        }
    }
}