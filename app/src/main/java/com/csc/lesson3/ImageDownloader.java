package com.csc.lesson3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by qurbonzoda on 08.03.16.
 */
public class ImageDownloader extends AsyncTask< String, Void, List<Bitmap> > {

    private final String CLIENT_ID = "2183564-e3a0ab8a392becd18c5968e6b";
    private final String IMAGE_TYPE = "all";
    private final int IMAGE_PER_PAGE = 20;

    private TranslationActivity activity;

    ImageDownloader(TranslationActivity activity) {
        this.activity = activity;
    }

    void attach(TranslationActivity activity) {
        this.activity = activity;
        Log.d(TAG, "attach");
    }
    void detach() {
        activity = null;
        Log.d(TAG, "detach");
    }

    @Override
    protected List<Bitmap> doInBackground(String... params) {
        try {
            return throwableDoInBackGround(params[0]);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Bitmap> images) {
        if (activity != null) {
            Bitmap[] bitmaps = new Bitmap[images.size()];
            images.toArray(bitmaps);
            activity.buildGrid(bitmaps);
            Log.d(TAG, "gridView set successfully");
        }
    }

    private List<Bitmap> throwableDoInBackGround(String text) throws Exception {
        List<Bitmap> images = new ArrayList<>();

        text = URLEncoder.encode(text, "UTF-8");

        URL url = new URL("https://pixabay.com/api/?key=" + CLIENT_ID
                + "&q=" + text
                + "&image_type=" + IMAGE_TYPE
                + "&per_page=" + String.valueOf(IMAGE_PER_PAGE));

        Log.d(TAG, url.toString());

        JsonReader jsonReader = new JsonReader(new InputStreamReader(url.openStream()));
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.nextName().equals("hits")) {
                jsonReader.beginArray();

                while (jsonReader.hasNext()) {
                    jsonReader.beginObject();

                    while (jsonReader.hasNext()) {
                        if (jsonReader.nextName().equals("previewURL")) {
                            String imageURL = jsonReader.nextString();
                            if (activity != null) {
                                Bitmap image = downloadURL(imageURL);
                                if (image != null) {
                                    images.add(image);
                                }
                            }
                        } else {
                            jsonReader.skipValue();
                        }
                    }

                    jsonReader.endObject();
                }

                jsonReader.endArray();
            } else {
                jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return images;
    }

    private Bitmap downloadURL(String imageURL) {
        try {
            Log.d(TAG, imageURL);
            URL url = new URL(imageURL);
            return BitmapFactory.decodeStream(url.openStream());
        } catch (Exception e) {
            return null;
        }
    }
    private final String TAG = "ImageDownloader";
}
