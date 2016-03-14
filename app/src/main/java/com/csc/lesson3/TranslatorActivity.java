package com.csc.lesson3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class TranslatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private final String YANDEX_API_KEY = "dict.1.1.20160312T163624Z.a25588e7dedc14da.61443128fd19ebdc371f8db8accd46d1fa6375e3";
    private final String YANDEX_BASE_URL = "https://dictionary.yandex.net/api/v1/dicservice.json/";
    private String PIXABAY_API_KEY = "2212765-818fc1b2706a3ba06c1b154c3";;
    private String PIXABAY_BASE_URL = "https://pixabay.com/api/";;
    private String selectedLanguage;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedLanguage =getResources().getStringArray(R.array.language_array)[0];
    }

    interface YandexDictionaryService {
        @GET("lookup?")
        Call<YandexDictionaryResponse> getTranslation(@Query("key") String key,
                                                      @Query("lang") String language,
                                                      @Query("text") String text);
    }

    interface PixabayService {
        @GET("?")
        Call<PixabayResponse> getPictures(@Query("key") String key,
                                          @Query("q") String query,
                                          @Query("lang") String language);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        Spinner languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        languageSpinner.setOnItemSelectedListener(this);

        final Context context = this;
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.queryWord);
                String query = editText.getText().toString();
                Intent intent = new Intent(context, TranslatorActivity.class);
                intent.putExtra(MainActivity.QUERY_WORD, query);
                intent.putExtra(MainActivity.SELECTED_LANGUAGE, selectedLanguage);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        final String intent_query = intent.getStringExtra(MainActivity.QUERY_WORD);
        final String intent_language = intent.getStringExtra(MainActivity.SELECTED_LANGUAGE);

        Retrofit translation_retrofit = new Retrofit.Builder()
                .baseUrl(YANDEX_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YandexDictionaryService dictionaryService = translation_retrofit.create(YandexDictionaryService.class);

        Call<YandexDictionaryResponse> call_translation = dictionaryService.getTranslation(
                YANDEX_API_KEY, intent_language.toLowerCase().replaceAll(">", ""), intent_query);
        call_translation.enqueue(new Callback<YandexDictionaryResponse>() {
            @Override
            public void onResponse(Call<YandexDictionaryResponse> call, Response<YandexDictionaryResponse> response) {
                TextView tv = (TextView) findViewById(R.id.translationText);
                try {
                    String s = response.body().def[0].tr[0].text;
                    tv.setText(s);
                }
                catch (Exception e) {
                    String emptyTranslation = getResources().getString(R.string.empty_translation);
                    tv.setText(emptyTranslation);
                }
            }

            @Override
            public void onFailure(Call<YandexDictionaryResponse> call, Throwable t) {}
        });

        Retrofit image_retrofit =  new Retrofit.Builder()
                .baseUrl(PIXABAY_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PixabayService imagesService = image_retrofit.create(PixabayService.class);
        Call<PixabayResponse> call_image = imagesService.getPictures(PIXABAY_API_KEY, intent_query,
                intent_language.toLowerCase().substring(4));
        call_image.enqueue(new Callback<PixabayResponse>() {
            @Override
            public void onResponse(Call<PixabayResponse> call, Response<PixabayResponse> response) {
                if (response.isSuccess()) {
                    ArrayList<String> data = new ArrayList<>();
                    for (Hit h : response.body().hits) {
                        data.add(h.previewURL);
                    }

                    GridView picturesGrid = (GridView) findViewById(R.id.picturesGrid);
                    PicturesArrayAdapter gridAdapter = new PicturesArrayAdapter(context, R.layout.grid_item_layout, data);
                    picturesGrid.setAdapter(gridAdapter);
                } else {
                    Log.d("PIXABAY fail", response.message());
                }
            }

            @Override
            public void onFailure(Call<PixabayResponse> call, Throwable t) {}
        });
    }
}
