package com.csc.lesson3;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by qurbonzoda on 08.03.16.
 */
public class Translator extends AsyncTask<String, Void, String> {

    private final String CLIENT_ID = "trnsl.1.1.20160308T091418Z.6f1e47a4bdf04a08.bf9bbb5a9c0ccdced5bb2be059f0a2f7dfd2028e";

    TranslationActivity activity;

    Translator(TranslationActivity activity) {
        this.activity = activity;
    }

    void attach(TranslationActivity activity) {
        Log.d(TAG, "attach");
        this.activity = activity;
    }
    void detach() {
        Log.d(TAG, "detach");
        activity = null;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return throwableDoInBackground(params);
        } catch (Exception e) {
            Log.d(TAG, "Exception: " + e);
            return "Sorry, we couldn't translate text";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (activity != null) {
            activity.translation.setText(result);
            Log.d(TAG, "Translated Successfully");
        }
    }
    private String throwableDoInBackground(String... params) throws Exception {

        URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + CLIENT_ID
                + "&lang=en-ru&text=" + params[0]);

        JsonReader jsonReader = new JsonReader(new InputStreamReader(url.openStream()));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals("text")) {
                jsonReader.beginArray();
                return jsonReader.nextString();
            }
            jsonReader.skipValue();
        }
        jsonReader.endObject();

        return "Unknown language";
    }
    private final String TAG = "Translator";
}
