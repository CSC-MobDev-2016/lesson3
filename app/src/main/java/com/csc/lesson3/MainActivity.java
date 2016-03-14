package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements TextView.OnEditorActionListener {

    public static final String WORD = "TRANSLATION_WORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText inputText = (EditText) findViewById(R.id.input_text);
        inputText.setOnEditorActionListener(this);
    }

    public void onClick(View view) {
        EditText inputText = (EditText) findViewById(R.id.input_text);

        startNewActivity(inputText.getText().toString());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            startNewActivity(v.getText().toString());
            return true;
        }
        return false;
    }

    private void startNewActivity(String text) {
        Intent intent = new Intent(MainActivity.this, TranslationActivity.class);
        intent.putExtra(WORD, text);
        startActivity(intent);
    }
}
