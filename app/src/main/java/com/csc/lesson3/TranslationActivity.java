package com.csc.lesson3;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class TranslationActivity extends Activity implements TextView.OnEditorActionListener {

    private static final String LANG = "en-ru";
    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        inputText = (EditText) findViewById(R.id.input_text);
        String word = getIntent().getStringExtra(MainActivity.WORD);
        inputText.setText(word);
        inputText.setOnEditorActionListener(this);

        TranslationTask translationTask = new TranslationTask(this);
        translationTask.execute(word, LANG);
    }

    public void onClick(View view) {
        TranslationTask translationTask = new TranslationTask(this);
        translationTask.execute(inputText.getText().toString(), LANG);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            TranslationTask translationTask = new TranslationTask(this);
            translationTask.execute(v.getText().toString(), LANG);
            return true;
        }
        return false;
    }
}
