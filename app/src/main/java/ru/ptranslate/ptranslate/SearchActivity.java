package ru.ptranslate.ptranslate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import dictionary.yandex.api.com.WordDescription;

public class SearchActivity extends AppCompatActivity implements Button.OnClickListener {

    private GlobalContext globalContext;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        globalContext = new GlobalContext(this, this);
        globalContext.onCreate();
        Intent intent = getIntent();
        if (intent.getBooleanExtra(globalContext.FROM_PREV_ACTIVITY, true)) {
            TranslateTask task = new TranslateTask();
            task.execute();
            intent.putExtra(globalContext.FROM_PREV_ACTIVITY, false);
        }
    }

    @Override
    public void onClick(View v) {
        TranslateTask task = new TranslateTask();
        task.execute();
    }

    class TranslateTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;
        private final int COUNT_COLUMN = 2;
        private final int COUNT_ROW = 8;
        private int globalCounter = COUNT_COLUMN * COUNT_ROW;
        private final int COUNT_PICTURES_REQUEST = 50;
        private final int RESIZE_HEIGHT = 800;
        private final int RESIZE_WIDTH = 800;
        private WordDescription description;
        private String inputText;
        private ArrayList<String> picturesUrl;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SearchActivity.this, getString(R.string.translating), getString(R.string.please_wait), true);
            inputText = ((EditText)findViewById(R.id.input_text)).getText().toString();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                description = globalContext.getTranslate(inputText);
                picturesUrl = globalContext.getBingPicturesUrl(inputText, COUNT_PICTURES_REQUEST);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            TextView tv = (TextView)findViewById(R.id.panel_for_show_translated_text);
            final TableLayout tableForPictures = (TableLayout) findViewById(R.id.table_for_pictures);
            tableForPictures.removeAllViews();
            if (description == null) {
                tv.setText(getString(R.string.err_common));
            } else {
                if (description.toString().isEmpty())
                {
                    tv.setText(getString(R.string.err_not_found_in_dictionary));
                }
                else
                {
                    tv.setText(Html.fromHtml(description.toString()));
                    tableForPictures.setShrinkAllColumns(true);
                    int x = 0;
                    end:
                    for (int j = 0; j < COUNT_ROW; j++) {
                        TableRow tr = new TableRow(SearchActivity.this);
                        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                        tr.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                        for (int i = 0; i < COUNT_COLUMN; i++) {
                            if (picturesUrl == null || x >= picturesUrl.size()) {
                                break end;
                            }
                            final ImageView iv = new ImageView(SearchActivity.this);
                            iv.setAdjustViewBounds(true);
                            tr.addView(iv);
                            Picasso.with(SearchActivity.this)
                                    .load(picturesUrl.get(x))
                                    .resize(RESIZE_WIDTH, RESIZE_HEIGHT)
                                    .placeholder(R.drawable.snake_load)
                                    .error(R.drawable.snake_error)
                                    .into(iv, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            iferror();
                                        }

                                        public void iferror() {
                                            if (picturesUrl != null && globalCounter < picturesUrl.size()) { //protocol specific
                                                Picasso.with(SearchActivity.this)
                                                        .load(picturesUrl.get(globalCounter++))
                                                        .resize(RESIZE_WIDTH, RESIZE_HEIGHT)
                                                        .placeholder(R.drawable.snake_load)
                                                        .error(R.drawable.snake_error)
                                                        .into(iv, new Callback() {

                                                            @Override
                                                            public void onSuccess() {

                                                            }

                                                            @Override
                                                            public void onError() {
                                                                iferror();
                                                            }
                                                        });
                                            }
                                        }
                                    });
                            x++;
                        }
                        tableForPictures.addView(tr);

                    }
                }
            }
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        globalContext.putExtra();
    }

    @Override
    public void onBackPressed() {
        globalContext.putExtraWithCode(RESULT_OK);
    }
}
