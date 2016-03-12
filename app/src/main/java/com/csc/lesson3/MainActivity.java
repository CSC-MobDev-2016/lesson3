package com.csc.lesson3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onTranslateClick(View view) {
        EditText inputEditText = (EditText) findViewById(R.id.input);
        String input  = inputEditText.getText().toString();

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra(GalleryActivity.EXTRA_INPUT, input);
        startActivity(intent);
    }
}
