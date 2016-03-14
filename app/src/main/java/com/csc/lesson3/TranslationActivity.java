package com.csc.lesson3;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TranslationActivity extends Activity implements View.OnClickListener,
        TextView.OnEditorActionListener {

    @Bind(R.id.original_text) EditText textToTranslate;
    @Bind(R.id.go_button) Button goButton;
    @Bind(R.id.pictures) GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation_activity);
        ButterKnife.bind(this);

        String text = getIntent().getStringExtra(MainActivity.WORD);
        goButton.setOnClickListener(this);
        textToTranslate.setOnEditorActionListener(this);
        textToTranslate.setText(text);
        //проблема с клавиатурой может быть из-за этой фразы (см. комментарии ниже), но нет
        //без этой строки клавиатура все равно не уходит
        textToTranslate.requestFocus(); //Чтобы курсор встал в конец фразы
        translateAndShow(text);
    }

    @Override
    public void onClick(View v) {
        String text = textToTranslate.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please write smth", Toast.LENGTH_SHORT).show();
        }
        else {
            translateAndShow(text);
        }
    }

    private void translateAndShow(String text) {
        new TextProvider(this).execute(text);
        new ImageProvider(this).execute(text);
        //по какой-то причине клавиатура не убирается при первой загрузке, и это очень огорчает меня
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textToTranslate.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            goButton.callOnClick();
            return true;
        }
        return false;
    }
}