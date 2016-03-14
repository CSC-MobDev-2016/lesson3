package com.csc.lesson3;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TranslationTask extends AsyncTask<Object, Void, String> {

    private Activity activity;

    public TranslationTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Object... params) {
        String answer;
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
            answer = jsonObject.getString("text").replaceAll("\\W", "");

        } catch (IOException e) {
            e.printStackTrace();
            answer = "Connection error. Please, try again later.";
        } catch (JSONException e) {
            e.printStackTrace();
            answer = "Some problems. Please, try again later.";
        }

        return answer;
    }

    @Override
    protected void onPostExecute(String answer) {
        TextView answerText = (TextView) activity.findViewById(R.id.translation_text);
        answerText.setText(answer);
    }

    private String getYandexApiId() {
        return "trnsl.1.1.20160314T094719Z.8d15f1040032695d.994e9167613db3059977bf749d7f192ad5f439ff";
    }

    private String getUrl() {
        return "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + getYandexApiId()
                + "&text=%s&lang=%s";
    }
}
