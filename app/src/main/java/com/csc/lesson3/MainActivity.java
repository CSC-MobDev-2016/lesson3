package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    private Button goButton;
    private EditText wordToSearch;
    private final String WORD = "word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.d(TAG, "onCreate");
        goButton = (Button) findViewById(R.id.goButton);
        wordToSearch = (EditText) findViewById(R.id.editText);
        goButton.setOnClickListener(this);
        wordToSearch.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        if (wordToSearch.getText().toString().isEmpty()) {
            Log.d(TAG, "onClick word is empty");
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();

        } else {
            Log.d(TAG, "onClick word := " + wordToSearch.getText());
            Intent intent = new Intent(this, TranslationActivity.class);
            intent.putExtra(WORD, wordToSearch.getText().toString());
            startActivity(intent);
        }
    }

    private final String TAG = "MainActivity";

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            goButton.callOnClick();
        }
        return false;
    }
}
