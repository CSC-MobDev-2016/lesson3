package com.csc.lesson3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by roman on 14.03.2016.
 */

public class PicturesArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> data;
    private int layoutResourceId;

    public PicturesArrayAdapter(Context context, int layoutResourceId, List<String> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

//        holder.image.setImageBitmap(data.get(position));
        URL url;
        try {
            url = new URL(data.get(position));
            new GetImageTask().execute(new ImageViewAndURL(holder.image, url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}
