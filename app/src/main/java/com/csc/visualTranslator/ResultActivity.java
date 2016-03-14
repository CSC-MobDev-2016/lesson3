package com.csc.visualTranslator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity implements TranslateReceiver.Receiver {

    private TranslateReceiver receiver;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        progress = (ProgressBar) findViewById(R.id.result_page_pb);

        String originalText = getIntent().getStringExtra(Constants.ORIGINAL_TEXT);
        ((EditText)findViewById(R.id.result_page_edit)).setText(originalText);

        final Button translate = (Button) findViewById(R.id.result_page_btn);
        translate.post(new Runnable() {
            @Override
            public void run() {
                translate.performClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new TranslateReceiver(new Handler());
        receiver.setReceiver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        receiver.setReceiver(null);
    }

    public void translate(View view) {
        EditText editText = (EditText) findViewById(R.id.result_page_edit);
        String msg = editText.getText().toString();
        startService(new Intent(this, TranslateService.class)
                .putExtra(Constants.ORIGINAL_TEXT, msg)
                .putExtra(Constants.RECEIVER, receiver));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle data) {
        if (resultCode == Constants.WAITING) {
            progress.setVisibility(View.VISIBLE);
            findViewById(R.id.result_page_translation).setVisibility(View.INVISIBLE);
        } else if (resultCode == Constants.DONE) {
            progress.setVisibility(View.INVISIBLE);
            TextView t = (TextView) findViewById(R.id.result_page_translation);
            t.setVisibility(View.VISIBLE);
            t.setText(data.getString(Constants.TRANSLATED_TEXT));
        }
    }
}
