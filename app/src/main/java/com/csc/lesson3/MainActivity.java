package com.csc.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener, TextView.OnEditorActionListener {

    public static final String WORD = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Button goButton = (Button)findViewById(R.id.go_button);
        goButton.setOnClickListener(this);
        EditText originalText = (EditText)findViewById(R.id.original_text);
        originalText.setOnEditorActionListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText originalText = (EditText)findViewById(R.id.original_text);
        if (originalText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please write smth", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, TranslationActivity.class);
            intent.putExtra(WORD, originalText.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Button goButton = (Button)findViewById(R.id.go_button);
            goButton.callOnClick();
            return true;
        }
        return false;
    }
}