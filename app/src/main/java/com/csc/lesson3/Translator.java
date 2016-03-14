package com.csc.lesson3;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Translator extends AsyncTask<String, Void, String> {
    public Translator(Context context, TextView textView) {
        super();
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... text) {
        try {
            URL url = new URL(request + URLEncoder.encode(text[0], "UTF-8"));
            URLConnection urlConnection =  url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            JSONObject response = new JSONObject(reader.readLine());
            reader.close();

            if (response.getInt("code") == 200) {
                return response.getJSONArray("text").getString(0);
            }

        } catch (MalformedURLException e1) {
//            Toast.makeText(context, "Translator error", Toast.LENGTH_LONG).show();
        } catch (IOException e1) {
//            Toast.makeText(context, "Translator error", Toast.LENGTH_LONG).show();
        } catch (JSONException e1) {
//            Toast.makeText(context, "Translator error", Toast.LENGTH_LONG).show();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String text) {
        textView.setText(text);
    }

    private static final String request = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160313T200814Z.d6aeff5bc918c6d0.3ac5df3e63a9e4cc657b742e609de62bd9ee2ccb&lang=en-ru&text=";
    TextView textView;
    private Context context;
}