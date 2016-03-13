package com.csc.lesson3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public final static String WORD = "com.csc.lesson3.WORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Button button = (Button) findViewById(R.id.button_send);
    }
        //button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            // Perform action on click
            EditText editext = (EditText)findViewById(R.id.word);
            Intent intent = new Intent(this, WordTranslateActivity.class);
            intent.putExtra(WORD, editext.getText().toString());
            startActivity(intent);
        }
       // });

        /*
        button.onClick {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(WORD, editext.getText());
            StartActivity(intent);
        }
        */
    }


//}
