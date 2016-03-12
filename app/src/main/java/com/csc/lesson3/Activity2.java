package com.csc.lesson3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {

    private static final String WORD = "com.csc.lesson3.word";

    private boolean translateDone = false;

    private Button btnGo;
    private EditText editText;
    private TranslateTask translateTask;
    private ImageLoadsTask imageLoadTask;
    private String wordToTranslate;
    private ArrayList translationList = null;
    private ArrayList imagesList = null;
    private ListView listViewTranslations;
    private GridView imageGrid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        btnGo = (Button) findViewById(R.id.button);
        btnGo.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.editText);
        wordToTranslate = getIntent().getStringExtra(WORD);
        editText.setText(wordToTranslate);

        listViewTranslations = (ListView) findViewById(R.id.listView);
        imageGrid = (GridView) findViewById(R.id.gridView);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if (translationList == null && !translateDone) {
            translateTask = new TranslateTask();
            translateTask.execute();

            imageLoadTask = new ImageLoadsTask();
            imageLoadTask.execute();

            return;
        }

        TextViewTranslateAdapter textAdapter = new TextViewTranslateAdapter(Activity2.this, translationList);
        listViewTranslations.setAdapter(textAdapter);

        GridImageAdapter imageAdapter = new GridImageAdapter(Activity2.this, imagesList);
        imageGrid.setAdapter(imageAdapter);
    }

    @Override
    public void onClick(View v) {

        if (editText.getText().toString().trim().length() == 0) {
            Toast.makeText(this, R.string.no_text, Toast.LENGTH_LONG).show();
            return;
        }

        wordToTranslate = editText.getText().toString();

        translateTask = new TranslateTask();
        translateTask.execute();

        imageLoadTask = new ImageLoadsTask();
        imageLoadTask.execute();

    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("translateArray", translationList);
        outState.putParcelableArrayList("imageArray", imagesList);
        outState.putBoolean("translateDone", translateDone);

        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        translationList = savedInstanceState.getParcelableArrayList("translateArray");
        imagesList = savedInstanceState.getParcelableArrayList("imageArray");
        translateDone = savedInstanceState.getBoolean("translateDone");
    }

    class TranslateTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                 translationList = Translate.execute(wordToTranslate, Language.RUSSIAN, Language.ENGLISH);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            translateDone = true;

            if (translationList == null || translationList.isEmpty()){
                Toast.makeText(Activity2.this, R.string.no_translation, Toast.LENGTH_SHORT).show();
                return;
            }

            TextViewTranslateAdapter adapter = new TextViewTranslateAdapter(Activity2.this, translationList);
            listViewTranslations.setAdapter(adapter);
        }
    }

    public class ImageLoadsTask extends AsyncTask<Void, Void, Void>
    {
        //String strSearch = "time";

        @Override
        protected Void doInBackground(Void... params) {
            imagesList = ImageLoadTranslate.execute(wordToTranslate);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (imagesList== null || imagesList.isEmpty()){
                Toast.makeText(Activity2.this, R.string.no_images, Toast.LENGTH_SHORT).show();
                return;
            }

            GridImageAdapter adapter = new GridImageAdapter(Activity2.this, imagesList);
            imageGrid.setAdapter(adapter);

            imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parant, View view, int position, long id){
                    Intent intent = new Intent(Activity2.this, FullImageActivity.class);
                    intent.putExtra("position", position);
                    intent.putParcelableArrayListExtra("imagelist", imagesList);
                    startActivity(intent);
                }
            });
        }
    }

}
