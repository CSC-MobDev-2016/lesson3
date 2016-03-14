package com.csc.lesson3;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridImageAdapter extends BaseAdapter {
    
	private Activity activity;
	public ArrayList<String> listImages;
	private static LayoutInflater inflater=null;
	
    public GridImageAdapter(Activity a, ArrayList<String> listImages) {
        activity = a;
        this.listImages = listImages;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return listImages.size();
    }

    public Object getItem(int position) {
    	return listImages.get(position);
    }

    public long getItemId(int position) 
    {
    	return position;
    }
    
    public static class ViewHolder{
    	public ImageView imgViewImage;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	View v = convertView;
    	ViewHolder holder;

		if(convertView == null){
			v = inflater.inflate(R.layout.listview_row, null);
			holder = new ViewHolder();
			
			holder.imgViewImage = (ImageView) v.findViewById(R.id.imageView01);
			
			v.setTag(holder);
		}
		else {
			holder = (ViewHolder) v.getTag();
		}
		
		String imageUrl = listImages.get(position);
		holder.imgViewImage.setTag(imageUrl);
		Picasso.with(activity)
				.load(imageUrl)
				.resize(150, 150)
				.into(holder.imgViewImage);

		return v;
    }
}