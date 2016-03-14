package com.csc.visualTranslator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void newActivity(View view) {
        Log.d(LOG_TAG, "activityStarted");
        Intent intent = new Intent(this, ResultActivity.class);
        EditText editText = (EditText) findViewById(R.id.main_edit);
        String msg = editText.getText().toString();
        if (msg.length() > 0) {
            intent.putExtra(Constants.ORIGINAL_TEXT, msg);
            startActivity(intent);
        } else
            Toast.makeText(this,"Enter some text to translate.",Toast.LENGTH_SHORT).show();
    }
}
