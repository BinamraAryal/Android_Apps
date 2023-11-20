package com.classwork.lightsoutgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        backButton=findViewById(R.id.back_button);

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent colorIntent = new Intent();
                int receivedColorId = ContextCompat.getColor(HelpActivity.this, R.color.green); // Example: Set default color
                colorIntent.putExtra(ColorActivity.EXTRA_COLOR, receivedColorId);
                setResult(RESULT_OK, colorIntent);
                finish();
            }
        });
    }



}