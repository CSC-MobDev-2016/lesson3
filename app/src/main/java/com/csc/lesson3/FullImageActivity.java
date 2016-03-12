package com.csc.lesson3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        int position = getIntent().getIntExtra("position", -1);
        ArrayList<String> imageList = getIntent().getStringArrayListExtra("imagelist");

        ImageView imageView = (ImageView) findViewById(R.id.fullImageView);

        if (position != -1){
            Picasso.with(this)
                    .load(imageList.get(position))
                    .resize(800, 800)
                    .into(imageView);
        }


    }
}
