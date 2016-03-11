package com.csc.lesson3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qurbonzoda on 07.03.16.
 */
public class TranslationActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, TextView.OnEditorActionListener {

    private Button goButton;
    private EditText textToTranslate;
    TextView translation;
    private GridView gridView;
    private final String WORD = "word";
    Bitmap imageBitmaps[] = null;
    Translator translator;
    ImageDownloader imageDownloader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation_layout);

        goButton = (Button) findViewById(R.id.goButton);
        textToTranslate = (EditText) findViewById(R.id.editText);
        translation = (TextView) findViewById(R.id.translations);
        gridView = (GridView) findViewById(R.id.pictureGrig);

        Log.d(TAG, "onCreate");

        goButton.setOnClickListener(this);
        gridView.setOnItemClickListener(this);
        textToTranslate.setOnEditorActionListener(this);

        Object[] tasks = (Object[])getLastNonConfigurationInstance();
        if (tasks != null) {
            translator = (Translator)tasks[0];
            imageDownloader = (ImageDownloader)tasks[1];
            imageBitmaps = (Bitmap[])tasks[2];

            translator.attach(this);
            imageDownloader.attach(this);

            if (imageBitmaps != null) {
                buildGrid(imageBitmaps);
            }
        }

        if (savedInstanceState == null) {
            String w = getIntent().getStringExtra(WORD);
            Log.d(TAG, "word := " + w);

            textToTranslate.setText(w);
            goButton.callOnClick();
        } else {
            textToTranslate.setText(savedInstanceState.getString("textToTranslate"));
            translation.setText(savedInstanceState.getString("translation"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("textToTranslate", textToTranslate.getText().toString());
        savedInstanceState.putString("translation", translation.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        translator.detach();
        imageDownloader.detach();

        return(new Object[]{translator, imageDownloader, imageBitmaps});
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick ");

        String text = textToTranslate.getText().toString();

        if (text.isEmpty()) {
            Log.d(TAG, "onClick word is empty");
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        } else {
            translator = new Translator(this);
            imageDownloader = new ImageDownloader(this);
            translator.execute(text);
            imageDownloader.execute(text);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            goButton.callOnClick();
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    void buildGrid(Bitmap[] images) {
        imageBitmaps = images;
        ImageAdapter<Bitmap> adapter = new ImageAdapter<>(
                getApplicationContext(),
                R.id.pictureGrig,
                imageBitmaps);

        gridView.setAdapter(adapter);
    }

    private final String TAG = "TranslationActivity";
}
