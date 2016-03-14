package com.csc.lesson3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        editText = (EditText) findViewById(R.id.textBox);
        editText.setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String word = editText.getText().toString();
            if (word.length() == 0)
                return true;
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("WORD", word);
            startActivity(intent);
            editText.clearFocus();
        }
        return false;
    }

    private EditText editText;
}
