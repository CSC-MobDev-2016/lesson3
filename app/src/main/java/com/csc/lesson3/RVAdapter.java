package com.csc.lesson3;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ImageViewHolder> {

    private List<String> images;
    private Activity activity;

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }
    }

    public RVAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public RVAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAdapter.ImageViewHolder holder, int position) {
        Picasso.with(activity)
                .load(images.get(position))
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setImages(List<String> images) {
        this.images = images;
        notifyDataSetChanged();
    }
}
