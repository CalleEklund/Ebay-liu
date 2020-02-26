package com.example.labb1android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addText = (Button) findViewById(R.id.addInput);


        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addName();
            }
        });
    }

    private void addName() {
        EditText input = (EditText) findViewById(R.id.input);
        TextView outputText = (TextView) findViewById(R.id.output);
        String inputStr = (String) input.getText().toString();

        String oldValue = (String) outputText.getText().toString();
        String newValue = (String) oldValue + "\nNamn: " + inputStr;

        outputText.setText(newValue);
    }
}
