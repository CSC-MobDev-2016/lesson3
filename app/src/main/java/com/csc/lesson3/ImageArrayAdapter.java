package com.csc.lesson3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chainic-vina on 14.03.16.
 */
public class ImageArrayAdapter extends ArrayAdapter {
    public ImageArrayAdapter(Context context, List<String> urls) {
        super(context, R.layout.grid_view_item, urls);
        this.context = context;
        this.urls = urls;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_view_item, parent, false);
            Picasso
                    .with(context)
                    .load(urls.get(position))
                    .into((ImageView)convertView);
        }
        return convertView;
    }

    private Context context;
    private List<String> urls;
    LayoutInflater inflater;
}
