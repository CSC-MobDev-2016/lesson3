package com.csc.lesson3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String QUERY_WORD = "QUERY_WORD";
    public static final String SELECTED_LANGUAGE = "SELECTED_LANGUAGE";
    private String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG", "Message");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                intent.putExtra(QUERY_WORD, query);
                intent.putExtra(SELECTED_LANGUAGE, selectedLanguage);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedLanguage = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedLanguage = getResources().getStringArray(R.array.language_array)[0];
    }
}
