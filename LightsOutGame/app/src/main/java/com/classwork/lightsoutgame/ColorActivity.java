package com.classwork.lightsoutgame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ColorActivity extends AppCompatActivity {

    public static final String EXTRA_COLOR = "com.classwork.lightsoutgame.color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
    }

    public void onColorSelected(View view) {
        int colorId = R.color.yellow;
        if (view.getId() == R.id.radio_red) {
            colorId = R.color.red;
        }
        else if (view.getId() == R.id.radio_orange) {
            colorId = R.color.orange;
        }
        else if (view.getId() == R.id.radio_green) {
            colorId = R.color.green;
        }

        int selectedColor = ContextCompat.getColor(this, colorId);



        Log.d("ColorActivity", "Selected Color: " + Integer.toHexString(selectedColor)); // Log the color

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COLOR, selectedColor);
        setResult(RESULT_OK, intent);
        finish();
    }
}