package com.csc.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;
import java.net.URLConnection;

/**
 * Created by roman on 14.03.2016.
 */

public class GetImageTask extends AsyncTask<ImageViewAndURL, Void, Bitmap> {
    private static final String TAG = "GetImageAsyncTask";
    private ImageView imageView;
    private URL url;
    private Bitmap bitmap;

    @Override
    protected Bitmap doInBackground(ImageViewAndURL... imageViewAndURLs) {
        try {
            imageView = imageViewAndURLs[0].iv;
            url = imageViewAndURLs[0].url;
            URLConnection conn = url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception e) {
            Log.e(TAG, "Exception during downloading image");
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}

