package com.csc.lesson3;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Филипп on 13.03.2016.
 */
class RVAdapter extends RecyclerView.Adapter<RVAdapter.ResultImageViewHolder> {

    private final static String LOG_TAG = "RVAdapter";
    public static class ResultImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ResultImageViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.result_image);
        }
    }

    List<String> urls = new ArrayList<>();
    Activity activity;

    public RVAdapter(Activity activity) {
        this.activity = activity;
    }

    public void updateUrls(List<String> urls) {
        Log.d(LOG_TAG, urls.toString());
        this.urls = urls;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    @Override
    public ResultImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_image_item, viewGroup, false);
        return new ResultImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ResultImageViewHolder holder, int position) {
        Picasso.with(activity).load(urls.get(position)).fit().into(holder.image);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}