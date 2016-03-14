package com.csc.lesson3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ImageProvider extends AsyncTask< String, Void, List<Bitmap> > {
    private static final String CLIENT_ID = "2212830-cfb3024b67d3e773643e49d8d";
    private static final int PICTURES_COUNT = 25;
    private TranslationActivity activity;

    ImageProvider(TranslationActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Bitmap> doInBackground(String... params) {
        try {
            List<Bitmap> images = new ArrayList<>();
            // Также пробовала бинг, но он крайне медленно грузит изображения
            URL url = new URL("https://pixabay.com/api/?key=" + CLIENT_ID
                    + "&q=" + URLEncoder.encode(params[0], "UTF-8")
                    + "&image_type=all&per_page=" + String.valueOf(PICTURES_COUNT));

            InputStream inputStream = url.openStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            JSONObject jsonReader = new JSONObject(buffer.toString());
            JSONArray results = jsonReader.getJSONArray("hits");
            for(int i = 0; i < results.length(); ++i) {
                Bitmap img;
                try {
                    URL imageUrl = new URL(results.getJSONObject(i).getString("previewURL"));
                    img = BitmapFactory.decodeStream(imageUrl.openStream());
                    images.add(img);
                } catch (Exception ignored) {}
            }
            return images;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Bitmap> images) {
        if (activity != null) {
            Bitmap[] bitmaps = new Bitmap[images.size()];
            images.toArray(bitmaps);
            activity.gridView.setAdapter(new ImageAdapter(activity.getApplicationContext(), R.id.pictures, bitmaps));
        }
    }

    class ImageAdapter extends ArrayAdapter<Bitmap> {
        public ImageAdapter(Context context, int resource, Bitmap[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView view = new ImageView(getContext());
            view.setImageBitmap(getItem(position));
            int side = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                    getContext().getResources().getDisplayMetrics());
            GridView.LayoutParams layoutParams = new GridView.LayoutParams(side, side);
            view.setLayoutParams(layoutParams);
            return view;
        }
    }
}