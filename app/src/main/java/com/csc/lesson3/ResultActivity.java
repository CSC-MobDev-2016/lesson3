package com.csc.lesson3;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.csc.lesson3.services.BingImages;
import com.csc.lesson3.services.YandexTranslator;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends MainActivity {

    public static final String INPUT = "INPUT";
    private static final String STATE_URLS = "STATE_URLS";
    private static final String STATE_TRANSLATION = "STATE_TRANSLATION";
    private String input_cache;

    TextView translation;
    RecyclerView recyclerView;
    RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT ? 3 : 5);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new RVAdapter(this);
        recyclerView.setAdapter(adapter);

        translation = ((TextView) findViewById(R.id.translation));

        if (savedInstanceState != null) {
            adapter.updateUrls(savedInstanceState.getStringArrayList(STATE_URLS));
        }
        else {
            String input = getIntent().getStringExtra(INPUT);
            editText.setText(input);
            getTranslation(input);
            getImages(input);
        }
        input_cache = editText.getText().toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText.setText(input_cache);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList(STATE_URLS, new ArrayList<>(adapter.urls));
        savedInstanceState.putString(STATE_TRANSLATION, translation.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.result);
    }

    private void getTranslation(String word) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (translation != null) {
                    translation.setText(result);
                }
            }
            @Override
            protected String doInBackground(String... params) {
                return new YandexTranslator().translate(params[0]);
            }
        }.execute(word);
    }

    private void getImages(String word) {
        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected void onPostExecute(List<String> s) {
                super.onPostExecute(s);
                if (adapter != null) {
                    adapter.updateUrls(s);
                }
            }

            @Override
            protected List<String> doInBackground(String... params) {
                return new BingImages().getImages(params[0]);
            }
        }.execute(word);
    }
}
