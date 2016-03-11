package com.csc.lesson3;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by qurbonzoda on 10.03.16.
 */
public class ImageAdapter<T> extends ArrayAdapter<T> {

    public ImageAdapter(Context context, int resource, T[] objects) {
        super(context, resource, objects);
    }

    public ImageAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ImageView view = new ImageView(getContext());
        view.setImageBitmap((Bitmap) getItem(position));
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                getContext().getResources().getDisplayMetrics());
        int width = height;
        GridView.LayoutParams layoutParams = new GridView.LayoutParams(width, height);
        view.setLayoutParams(layoutParams);
        return view;
    }
}
