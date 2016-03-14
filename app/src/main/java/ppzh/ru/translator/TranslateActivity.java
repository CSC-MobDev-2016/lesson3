package ppzh.ru.translator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TranslateActivity extends AppCompatActivity {
    private final String ORIGINAL_TEXT = "ppzh.ru.translator.original_text";
    private final String TRANSLATED_TEXT = "ppzh.ru.translator.translated_text";
    private final String IMAGE_URLS = "ppzh.ru.translator.image_urls";

    private final String YANDEX_TRANSLATE_KEY =
            "trnsl.1.1.20160306T080530Z.55125315a1bb2d4e.576ead730c7369204404407980a74cbec04f1f6c";
    private final String FROM_TO = "en-ru";

    private final String PIXABAY_KEY = "2214342-9fe25c63bc6ab6a732fa6d8f4";
    private final String IMAGE_TYPE = "all";
    private final String IMAGE_PER_PAGE = "20";

    private final int MAX_PROGRESS = 25;

    private String originalText;
    private TextView translated;
    private Button goButton;
    private EditText toTranslate;
    private ProgressBar downloadProgress;
    private GridView grid;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        goButton = (Button) findViewById(R.id.go_button);
        toTranslate = (EditText) findViewById(R.id.text_to_translate);
        translated = (TextView) findViewById(R.id.translated);
        downloadProgress = (ProgressBar) findViewById(R.id.progressBar);
        grid = (GridView) findViewById(R.id.gridView);

        imageAdapter = new ImageAdapter(this);
        grid.setAdapter(imageAdapter);

        if (savedInstanceState != null) {
            originalText = savedInstanceState.getString(ORIGINAL_TEXT);
            String[] imageUrls = savedInstanceState.getStringArray(IMAGE_URLS);

            translated.setText(savedInstanceState.getString(TRANSLATED_TEXT));
            imageAdapter.setContent(imageUrls);
            imageAdapter.notifyDataSetChanged();
        } else {
            originalText = getIntent().getStringExtra(MainActivity.TEXT);
            toTranslate.setText(originalText);
            getTrsnslation();
        }

        goButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                translate(v);
            }
        });

        toTranslate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    translate(v);
                    return true;
                }
                return false;
            }
        });
    }

    private void translate(View v) {
        String text = toTranslate.getText().toString().trim();
        if (text.length() == 0) {
            Snackbar.make(v, R.string.empty_text_msg, Snackbar.LENGTH_LONG).show();
            toTranslate.setText("");
            return;
        }
        if (!text.equals(originalText)) {
            originalText = text;
            getTrsnslation();
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    private void getTrsnslation() {
        downloadTranslation();
        downloadPictures();
    }

    private void downloadTranslation() {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected void onPreExecute() {
                downloadProgress.setProgress(0);
                downloadProgress.setVisibility(ProgressBar.VISIBLE);
            }

            @Override
            protected String doInBackground(String... params) {
                int progress = 1;
                publishProgress(progress++);
                StringBuilder sb = new StringBuilder();
                try {
                    URL url = new URL("https://translate.yandex.net/api/v1.5/tr/translate" +
                            "?key=" + YANDEX_TRANSLATE_KEY +
                            "&text=" + URLEncoder.encode(params[0], "UTF-8") +
                            "&lang=" + FROM_TO);

                    XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
                    xpp.setInput(url.openStream(), null);
                    publishProgress(progress++);

                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.TEXT)
                            sb.append(xpp.getText());
                        eventType = xpp.next();
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return getResources().getString(R.string.incorrect_query_error);
                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                    return getResources().getString(R.string.translation_error);
                }

                publishProgress(progress);
                return sb.toString();
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                downloadProgress.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                translated.setText(s);
            }
        }.execute(originalText);

    }

    private void downloadPictures() {
        new AsyncTask<String, Integer, List<String>>() {
            @Override
            protected List<String> doInBackground(String... params) {
                int progress = MAX_PROGRESS - Integer.parseInt(IMAGE_PER_PAGE) - 1;
                List<String> imageURLs = new ArrayList<String>();
                try {
                    URL get_images_query = new URL("https://pixabay.com/api/" +
                            "?key=" + PIXABAY_KEY +
                            "&q=" + URLEncoder.encode(params[0], "UTF-8") +
                            "&image_type=" + IMAGE_TYPE +
                            "&per_page=" + IMAGE_PER_PAGE);

                    JsonReader reader =
                            new JsonReader(new InputStreamReader(get_images_query.openStream()));
                    publishProgress(progress++);

                    reader.beginObject();
                    while (reader.hasNext()) {
                        if (reader.nextName().equals("hits")) {
                            reader.beginArray();
                            while (reader.hasNext()) {
                                reader.beginObject();
                                while (reader.hasNext()) {
                                    if (reader.nextName().equals("webformatURL")) {
                                        imageURLs.add(reader.nextString());
                                    } else {
                                        reader.skipValue();
                                    }
                                }
                                reader.endObject();
                                publishProgress(progress++);
                            }
                            reader.endArray();
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();

                } catch (IOException e) {
                    e.printStackTrace();
                    imageURLs.clear();
                    return imageURLs;
                }
                return imageURLs;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                downloadProgress.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(List<String> imageUrls) {

                String[] arr = new String[imageUrls.size()];
                imageUrls.toArray(arr);
                imageAdapter.setContent(arr);
                imageAdapter.notifyDataSetChanged();
                downloadProgress.setProgress(0);
                downloadProgress.setVisibility(ProgressBar.INVISIBLE);
            }

        }.execute(originalText);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ORIGINAL_TEXT, originalText);
        outState.putString(TRANSLATED_TEXT, translated.getText().toString());
        outState.putStringArray(IMAGE_URLS, imageAdapter.getContent());
        super.onSaveInstanceState(outState);
    }
}
