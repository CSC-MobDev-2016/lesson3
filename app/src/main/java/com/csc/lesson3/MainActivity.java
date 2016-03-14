package com.csc.lesson3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String WORD = "WORD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    public void onGoClick(View view) {
        EditText input = (EditText) findViewById(R.id.editText);

        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(WORD, input.getText().toString());
        startActivity(intent);
    }
}
