package ru.ptranslate.ptranslate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    private GlobalContext globalContext;
    private final int REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            globalContext.setLanguage(data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("OnCreate", "Start");
        globalContext = new GlobalContext(this, this);
        globalContext.onCreate();
    }

    @Override
    public void onClick(View v) {
        globalContext.startActivityForResult(SearchActivity.class, REQUEST_CODE);
    }
}
