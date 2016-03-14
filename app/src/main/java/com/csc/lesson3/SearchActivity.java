package com.csc.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import com.csc.lesson3.api.ImageSearch;
import com.csc.lesson3.api.Translator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ArrayList<Bitmap> images = new ArrayList<>();
    TextView textView;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private String w;
    private String translation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        EditText word = (EditText) findViewById(R.id.editText2);
        gridView = (GridView) findViewById(R.id.gridview);
        textView = (TextView) findViewById(R.id.textView);

        if (savedInstanceState == null) {
            w = getIntent().getStringExtra(MainActivity.WORD);
            translate(w);
            getImages(w);
        }
        else {
            textView.setText(translation);
        }
        word.setText(w);

        gridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, images);
        gridView.setAdapter(gridAdapter);
    }

    public void onGoReClick(View view) {
        EditText input = (EditText) findViewById(R.id.editText2);
        translate(input.getText().toString());
        getImages(input.getText().toString());
    }

    private void translate(String word) {
        new TranslateAsyncTask().execute(word);
    }

    private class TranslateAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            translation = s;
            textView.setText(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Translator t = new Translator();
            return t.translate(strings[0]);
        }
    }

    private void getImages(String word) {
        new GetImagesAsyncTask().execute(word);
    }

    private class GetImagesAsyncTask extends AsyncTask<String, Void, List<Bitmap> > {
        @Override
        protected void onPostExecute(List<Bitmap> res) {
            super.onPostExecute(res);
            images.addAll(res);
            gridAdapter.setData(images);
            gridView.setAdapter(gridAdapter);
        }

        @Override
        protected List<Bitmap> doInBackground(String... strings) {
            ImageSearch s = new ImageSearch();
            List<String> result = s.getImages(strings[0]);
            List<Bitmap> images = new ArrayList<Bitmap>();
            try{
                for(String path: result) {
                    URL url = new URL(path);
                    Bitmap img = BitmapFactory.decodeStream(url.openStream());
                    images.add(img);
                }
            } catch (Exception e) {}
            return images;
        }
    }


}
