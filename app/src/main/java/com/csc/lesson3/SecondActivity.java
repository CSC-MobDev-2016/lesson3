package com.csc.lesson3;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chainic-vina on 14.03.16.
 */
public class SecondActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        editText = (EditText) findViewById(R.id.textBox);
        textView = (TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridView);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        String word = getIntent().getStringExtra("WORD");
        editText.setText(word);
        editText.setOnEditorActionListener(this);
        editText.setSelection(word.length());


        if (savedInstanceState == null || !savedInstanceState.containsKey("translation")) {
            new Translator(this, textView).execute(word);
        }
        if (savedInstanceState == null || !savedInstanceState.containsKey("pic_urls")) {
            urls = new ArrayList<>();
            new FlickrSearcher(this, gridView, urls).execute(word);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            String word = editText.getText().toString();
            if (word.length() == 0)
                return true;
            new Translator(this, textView).execute(word);
            new FlickrSearcher(this, gridView, urls).execute(word);
            return false;
        }
        return false;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("translation"));
        urls = savedInstanceState.getStringArrayList("pic_urls");
        gridView.setAdapter(new ImageArrayAdapter(this, urls));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String value = textView.getText().toString();
        outState.putString("translation", value);
        outState.putStringArrayList("pic_urls", urls);
    }

    private TextView textView;
    private EditText editText;
    private GridView gridView;
    ArrayList<String> urls;

}
