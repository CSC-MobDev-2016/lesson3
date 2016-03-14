package com.csc.visualTranslator;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.provider.SyncStateContract;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Allight.
 */
public class TranslateService extends IntentService {
    private static final String YANDEX_TRANSLATE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20160314T041415Z.437be19ab5f1f8b7.5d8c9875810291920f9605f3237834a6a70234bd&text=%s&lang=en-ru&format=plain";
    private static final String LOG_TAG = TranslateService.class.getSimpleName();


    public TranslateService() {
        super(LOG_TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "translate intent got");
        final ResultReceiver receiver = intent.getParcelableExtra(Constants.RECEIVER);
        receiver.send(Constants.WAITING, Bundle.EMPTY);
        final Bundle data = new Bundle();

        String original = intent.getStringExtra(Constants.ORIGINAL_TEXT);
        String translated;

        try {
            original = URLEncoder.encode(original, "utf-8");
            URL url = new URL(String.format(YANDEX_TRANSLATE_URL, original));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException("Bad response code");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null)
                buffer.append(line);
            translated = new JSONObject(buffer.toString()).getJSONArray("text").getString(0);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getMessage());
            return;
        }
        data.putString(Constants.TRANSLATED_TEXT,translated);
        receiver.send(Constants.DONE, data);
        Log.d(LOG_TAG, "translated to: " + translated);
    }


}
