package com.csc.lesson3;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class WordTranslateActivity extends AppCompatActivity {

    private String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        word = intent.getStringExtra(MainActivity.WORD);
        setContentView(R.layout.activity_word_translate);
        setWord();
        /*
        EditText editext = (EditText)findViewById(R.id.word);
        editext.setHint(word);
        TextView textView = (TextView)findViewById(R.id.display_word);
        textView.setText(word);
        */
        //TextView textView = new TextView(this);
        //textView.setTextSize(40);
        //textView.setText(word);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.content2);
        //layout.addView(textView);
        //PostTask postTask = new PostTask();
        //postTask.doInBackground("k");
        new PostTask().execute();
        new ImageTask().execute();
        //TextView textView2 = (TextView)findViewById(R.id.display_translate);
        //textView2.setText(answer);
        //setWord(answer);
        //String query2 = URLEncoder.encode("apples oranges", "utf-8");
        //String url = "http://stackoverflow.com/search?q=" + query;


    }

    public void onClick(View v) {
        // Perform action on click
        EditText editext = (EditText) findViewById(R.id.word);
        String word = editext.getText().toString();
        this.word = word;
        setWord();
        new PostTask().execute();
        new ImageTask().execute();
    }

    public void setWord() {
        EditText editext = (EditText) findViewById(R.id.word);
        editext.setHint(word);
        TextView textView = (TextView) findViewById(R.id.display_word);
        textView.setText(word);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_translate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // /*
    // https://habrahabr.ru/post/261259/
    // http://stackoverflow.com/questions/31552242/sending-http-post-request-with-android
    // http://developer.android.com/intl/ru/reference/android/os/AsyncTask.html
    private class PostTask extends AsyncTask<Void, Void, String> {

        private String APIKey = "trnsl.1.1.20160312T191555Z.78fb47e13550904e.49288016e4dd802607080920d97a2029de8d951b";
        private String address = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        private String lang = "en";

        @Override
        protected String doInBackground(Void... adress) {
            // Create a new HttpClient and Post Header
            String translated = "";
            try {
                String urlStr = address + "key=" + APIKey;
                URL urlObj = new URL(urlStr);
                HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes("text=" + URLEncoder.encode(word, "UTF-8") + "&lang=" + lang);

                InputStream response = connection.getInputStream();
                String json = new java.util.Scanner(response).nextLine();
                int start = json.indexOf("[");
                int end = json.indexOf("]");
                translated = json.substring(start + 2, end - 1);
            } catch (Exception e) {
                translated = "I failed";
            }
            return translated;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView textView = (TextView) findViewById(R.id.display_translate);
            textView.setText(result);
        }
    }

    private class ImageTask extends AsyncTask<Void, Void, ArrayList<Map<String, Object>>> {

        final String k1 = "title";
        final String k2 = "image";

        @Override
        protected ArrayList<Map<String, Object>> doInBackground(Void... wdor) {

            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            try {
                //https://www.googleapis.com/customsearch/v1?q=%D0%BC%D0%B8%D1%80&key=AIzaSyBArRr189umjV6O_0c707_I7g4I6ohuQsQ&cx=006914356100683202957:q_a7pumtcla&imgSize=small
                //https://www.googleapis.com/customsearch/v1?q=%D0%BC%D0%B8%D1%80&key=AIzaSyBArRr189umjV6O_0c707_I7g4I6ohuQsQ&cx=006914356100683202957:q_a7pumtcla&imgSize=small&searchType=image
                // http://stackoverflow.com/questions/13374088/google-search-in-java
                String key = "AIzaSyBArRr189umjV6O_0c707_I7g4I6ohuQsQ";
                String qry = word;
                String cx = "006914356100683202957:q_a7pumtcla";
                URL url = new URL(
                        "https://www.googleapis.com/customsearch/v1?key=" + key +
                                "&cx=" + cx + "&q=" + URLEncoder.encode(qry, "UTF-8") +
                                "&imgSize=" + "small" + "&searchType=" + "image");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;
                System.out.println("Output from Server .... \n");
                Boolean itemsPassed = false;

                Map<String, Object> m = new HashMap();
                while ((output = br.readLine()) != null) {

                    if (output.contains("\"items\"")) {
                        itemsPassed = true;
                        System.out.println(itemsPassed);
                    }
                    if (output.contains("\"title\": \"") & itemsPassed) {
                        String title = output.substring(output.indexOf("\"title\": \"") + ("\"title\": \"").length(), output.indexOf("\","));
                        m.put(k1, title);
                    }
                    if (output.contains("\"link\": \"")) {
                        String link = output.substring(output.indexOf("\"link\": \"") + ("\"link\": \"").length(), output.indexOf("\","));
                        m.put(k2, link);
                        data.add(m);
                        m = new HashMap<String, Object>();
                    }
                }
                conn.disconnect();

                /*
                String key = "AIzaSyBArRr189umjV6O_0c707_I7g4I6ohuQsQ";
                String cx = "006914356100683202957:q_a7pumtcla";
                //String searchText = word;
                String queryArguments = "key="+key+ "&cx="+ cx +"&q="+ word+"&imgSize="+"small"+"&searchType="+"image";

                String addition = URLEncoder.encode(queryArguments, "UTF-8")
                        .replaceAll("\\%28", "(")
                        .replaceAll("\\%29", ")")
                        .replaceAll("\\+", "%20")
                        .replaceAll("\\%27", "'")
                        .replaceAll("\\%21", "!")
                        .replaceAll("\\%7E", "~");


                URL url = new URL("https://www.googleapis.com/customsearch/v1?" + addition);// key="+key+ "&cx="+ cx +"&q="+ URLEncoder.encode(word, "UTF-8") +"&alt=json");
                HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                //System.out.println("Connection opened!");
                conn2.setRequestMethod("GET");
                conn2.setRequestProperty("Accept", "application/json");
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn2.getInputStream())));

                responce = br.readLine();
                /*
                String urlStr = "https://www.googleapis.com/customsearch/v1?&key=AIzaSyBArRr189umjV6O_0c707_I7g4I6ohuQsQ&cx=006914356100683202957:q_a7pumtcla";
                URL urlObj = new URL(urlStr);
                HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes("q=" + URLEncoder.encode(word, "UTF-8") + "&searchType=" + "image" + "&imgSize=" + "small");

                InputStream response = connection.getInputStream();
                String json = new java.util.Scanner(response).nextLine();
                //int start = json.indexOf("[");
                //int end = json.indexOf("]");
                responce = json;
                */
            } catch (Exception e) {
                Map<String, Object> m = new HashMap<String, Object>();
                m.put(k1, "I failed");
                m.put(k2, "");
                data.add(m);
            }
            return data;
        }

        // https://github.com/arvifox/android_image_url_listview
        @Override
        protected void onPostExecute(ArrayList<Map<String, Object>> data) {
            ListView listView = (ListView) findViewById(R.id.image_list);

            String[] from = {k1, k2};
            int[] to = {R.id.tvNewsCaption, R.id.ivNewsImage};
            SimpleAdapter sa = new SimpleAdapter(WordTranslateActivity.this, data, R.layout.list_row_layout, from, to);
            sa.setViewBinder(new NewsBinder());
            listView.setAdapter(sa);
        }
    }

    class NewsBinder implements SimpleAdapter.ViewBinder {

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (view.getId() == R.id.ivNewsImage) {
                Picasso pi;
                pi = Picasso.with(WordTranslateActivity.this);
                pi.setIndicatorsEnabled(true);
                pi.load((String) data).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).fit().centerCrop().into((ImageView) view);
                return true;
            }
            return false;
        }
    }
}
