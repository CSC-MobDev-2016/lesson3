package ru.ptranslate.ptranslate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import dictionary.yandex.api.com.Language;
import dictionary.yandex.api.com.Translate;
import dictionary.yandex.api.com.WordDescription;
import picture.bing.api.com.BingPicture;

public class GlobalContext {
    private Translate translate = new Translate();
    private BingPicture bingPicture = new BingPicture();
    private String[] data = { "English", "Russian", "French", "German", "Ukrainian" };
    private Language[] lang = { Language.ENGLISH, Language.RUSSIAN, Language.FRENCH, Language.GERMAN, Language.UKRAINIAN };
    private Language langFrom = Language.ENGLISH;
    private Language langTo = Language.RUSSIAN;
    private AppCompatActivity activity;
    private Button.OnClickListener clickListener;
    private EditText inputText;
    private Spinner spinnerLangFrom;
    private Spinner spinnerLangTo;
    private Button btnTranslate;

    public final String MESSAGE_FOR_SEARCH = "MESSAGE";
    public final String LANG_FROM = "LANG_FROM";
    public final String LANG_TO = "LANG_TO";
    public final String FROM_PREV_ACTIVITY = "FROM_PREV_ACTIVITY";


    GlobalContext(AppCompatActivity activity,  Button.OnClickListener clickListener) {
        translate.setKey("dict.1.1.20160306T093514Z.bbb9e3db01cef073.d0356e388174436a1f2c93cce683819103ec4579");
        bingPicture.setKey("ObWAz+L07325riezjA4+F0O/KkK6Hq2qPWAIiCCIadA=");
        this.activity = activity;
        this.clickListener = clickListener;
        inputText = (EditText) activity.findViewById(R.id.input_text);
        spinnerLangFrom = (Spinner) activity.findViewById(R.id.lang_from);
        spinnerLangTo = (Spinner) activity.findViewById(R.id.lang_to);
        btnTranslate = (Button) activity.findViewById(R.id.button_translate);
    }

    private int getID(final Language l) {
        for (int i = 0; i < lang.length; i++) {
            if (lang[i] == l) {
                return i;
            }
        }
        return -1;
    }

    public void onCreate()
    {
        btnTranslate.setOnClickListener(clickListener);
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerLangFrom.setAdapter(adapter);
            spinnerLangFrom.setPrompt(activity.getString(R.string.from));
            spinnerLangFrom.setSelection(0);
            spinnerLangFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    langFrom = lang[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, data);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerLangTo.setAdapter(adapter);
            spinnerLangTo.setPrompt(activity.getString(R.string.to));
            spinnerLangTo.setSelection(1);
            spinnerLangTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    langTo = lang[position];
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }
        Intent intent = activity.getIntent();
        setLanguage(intent);
    }

    public void setLanguage(Intent intent)
    {
        inputText.setText(intent.getStringExtra(MESSAGE_FOR_SEARCH));
        langFrom = Language.fromString(intent.getStringExtra(LANG_FROM));
        langTo = Language.fromString(intent.getStringExtra(LANG_TO));
        if (getID(langFrom) != -1){
            spinnerLangFrom.setSelection(getID(langFrom));
        }

        if (getID(langTo) != -1){
            spinnerLangTo.setSelection(getID(langTo));
        }
    }

    private  void putExtra(Intent intent) {
        intent.putExtra(MESSAGE_FOR_SEARCH, inputText.getText().toString());
        intent.putExtra(LANG_FROM, langFrom.toString());
        intent.putExtra(LANG_TO, langTo.toString());
    }

    public void putExtra() {
        Intent intent = activity.getIntent();
        putExtra(intent);
    }

    public void putExtraWithCode(int resultCode) {
        Intent intent = activity.getIntent();
        putExtra(intent);
        activity.setResult(resultCode, intent);
        activity.finish();
    }

    public void startActivityForResult(Class cl, int requestCode) {
        Intent intent = new Intent(activity, cl);
        String message = inputText.getText().toString();
        intent.putExtra(MESSAGE_FOR_SEARCH, message);
        intent.putExtra(LANG_FROM, langFrom.toString());
        intent.putExtra(LANG_TO, langTo.toString());
        intent.putExtra(FROM_PREV_ACTIVITY, true);
        activity.startActivityForResult(intent, requestCode);
    }

    public WordDescription getTranslate(final String inputText) throws Exception {
        return translate.execute(inputText, langFrom, langTo);
    }

    public ArrayList<String> getBingPicturesUrl(final String inputText, int count) throws Exception {
        return bingPicture.execute(inputText, count).getMediaUrlList();
    }
}
