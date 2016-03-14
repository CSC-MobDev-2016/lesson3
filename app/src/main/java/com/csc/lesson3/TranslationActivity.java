package com.csc.lesson3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TranslationActivity extends Activity implements TextView.OnEditorActionListener {

    private static final String LANG = "en-ru";
    private EditText inputText;
    private RecyclerView recyclerView;
    protected RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        inputText = (EditText) findViewById(R.id.input_text);
        String word = getIntent().getStringExtra(MainActivity.WORD);
        inputText.setText(word);
        inputText.setOnEditorActionListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new RVAdapter(this);

        updatePage(word);
    }

    public void onClick(View view) {
        updatePage(inputText.getText().toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            updatePage(v.getText().toString());
            return true;
        }
        return false;
    }

    private void updatePage(String text) {
        TranslationTask translationTask = new TranslationTask(this);
        translationTask.execute(text, LANG);

        ImageViewTask imageViewTask = new ImageViewTask();
        List<String> urlImages = null;
        try {
            urlImages = imageViewTask.execute(text).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        adapter.setImages(urlImages);
        recyclerView.setAdapter(adapter);
    }
}
