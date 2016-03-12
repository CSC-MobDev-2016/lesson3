package com.csc.lesson3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csc.lesson3.apis.ApiServices;
import com.csc.lesson3.apis.common.ApiRequestCallback;
import com.csc.lesson3.apis.flickr_image_search.FlickrImageSearchApiService;
import com.csc.lesson3.apis.yandex_translate.YandexTranslateApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {
    public static final String EXTRA_INPUT = "EXTRA_INPUT";

    private static final String SAVED_STATE = "SAVED_STATE";
    private static final String SAVED_ULRS = "SAVED_ULRS";

    private final YandexTranslateApiService translateApi = ApiServices.YANDEX_TRANSLATE;

    private final FlickrImageSearchApiService imageSearchApi = ApiServices.FLICKR_IMAGE_SEARCH;

    private final Adapter adapter = new Adapter();

    private ViewState state;

    @Bind(R.id.input) EditText inputEditText;
    @Bind(R.id.translate_button) Button traslateButton;
    @Bind(R.id.result_textview) TextView resultTextView;
    @Bind(R.id.images_recyclerview) RecyclerView recyclerView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        setUpRecyclerView();

        translateApi.setCallback(translateCallback);
        imageSearchApi.setCallback(imageSearchCallback);

        if (savedInstanceState == null) {
            onFirstCreation();
        } else {
            onSubsequentCreation(savedInstanceState);
        }
    }

    private void onFirstCreation() {
        String input = getIntent().getStringExtra(EXTRA_INPUT);
        inputEditText.setText(input);
        translate(input);
    }

    private void onSubsequentCreation(Bundle savedInstanceState) {
        int stateKey = savedInstanceState.getInt(SAVED_STATE);
        for (ViewState state: ViewState.values()) {
            if (state.key == stateKey) {
                goToState(state);
                break;
            }
        }
        adapter.setUrls(savedInstanceState.getStringArrayList(SAVED_ULRS));

        if (state == ViewState.TRANSLATING) {
            translateApi.deliverPendingResult();
        } else if (state == ViewState.LOADING_IMAGE_URLS) {
            imageSearchApi.deliverPendingResult();
        }
    }

    private void setUpRecyclerView() {
        int columnCount = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        translateApi.setCallback(null);
        imageSearchApi.setCallback(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_STATE, state.key);
        outState.putStringArrayList(SAVED_ULRS, new ArrayList<>(adapter.getUrls()));
    }

    public void onTranslateClick(View view) {
        translate(inputEditText.getText().toString());
    }

    private void translate(String input) {
        goToState(ViewState.TRANSLATING);
        translateApi.fetchTranslation(input);
    }

    private void launchImageSearch() {
        goToState(ViewState.LOADING_IMAGE_URLS);
        adapter.setUrls(Collections.<String>emptyList());
        imageSearchApi.fetchImages(inputEditText.getText().toString());
    }

    private void goToState(ViewState state) {
        this.state = state;
        recyclerView.setVisibility(state.recyclerViewVisible ? View.VISIBLE: View.GONE);
        progressBar.setVisibility(state.recyclerViewVisible ? View.GONE: View.VISIBLE);
        traslateButton.setEnabled(state.controlsEnabled);
        inputEditText.setEnabled(state.controlsEnabled);
    }

    private final ApiRequestCallback<String> translateCallback = new ApiRequestCallback<String>() {
        @Override
        public void onSuccess(String translation) {
            launchImageSearch();
            resultTextView.setText(translation);
        }

        @Override
        public void onError(Throwable error) {
            goToState(ViewState.READY);
            Toast.makeText(GalleryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private final ApiRequestCallback<List<String>> imageSearchCallback = new ApiRequestCallback<List<String>>() {
        @Override
        public void onSuccess(List<String> urls) {
            goToState(ViewState.READY);
            adapter.setUrls(urls);
        }

        @Override
        public void onError(Throwable error) {
            goToState(ViewState.READY);
            Toast.makeText(GalleryActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }

        public void bindToItem(String url) {
            Picasso.with(GalleryActivity.this)
                    .load(url)
                    .into(imageView);
        }

    }

    private class Adapter extends RecyclerView.Adapter<ImageViewHolder> {
        private List<String> urls = Collections.emptyList();

        public void setUrls(List<String> urls) {
            this.urls = urls;
            notifyDataSetChanged();
        }

        private List<String> getUrls() {
            return urls;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_view, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            holder.bindToItem(urls.get(position));
        }

        @Override
        public int getItemCount() {
            return urls.size();
        }
    }

    private enum ViewState {
        READY(0, true, true), TRANSLATING(1, true, false), LOADING_IMAGE_URLS(2, false, false);

        public final int key;
        public final boolean recyclerViewVisible;
        public final boolean controlsEnabled;

        ViewState(int key, boolean recyclerViewVisible, boolean enabled) {
            this.key = key;
            this.recyclerViewVisible = recyclerViewVisible;
            controlsEnabled = enabled;
        }
    }
}
