package com.csc.lesson3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String WORD = "com.csc.lesson3.word";

    private Button btnGo;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGo = (Button) findViewById(R.id.button);
        btnGo.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    public void onClick(View v) {

        if (editText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, R.string.no_text, Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, Activity2.class);
        i.putExtra(WORD, editText.getText().toString());
        startActivity(i);
    }
}

