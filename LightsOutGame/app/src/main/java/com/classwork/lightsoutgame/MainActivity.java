package com.classwork.lightsoutgame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private LightsOutGame mGame;
    private GridLayout mLightGrid;
    private int mLightOnColor;
    private int mLightOffColor;
    private final String Game_State = "gameState";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mLightGrid = findViewById(R.id.light_grid);
        mLightOnColor = ContextCompat.getColor(this, R.color.yellow);
        mLightOffColor = ContextCompat.getColor(this, R.color.black);

        mGame = new LightsOutGame();

        if (savedInstanceState == null) {
            mGame = new LightsOutGame();
            startGame();
            setupGridButtons();
        } else {
            String gameState = savedInstanceState.getString(Game_State);
            mGame = new LightsOutGame();
            mGame.setState(gameState);
            setButtonColors();
        }

        setupGridButtons();
        startGame();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Game_State, mGame.getState());
    }


    private void setupGridButtons() {
        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            gridButton.setOnClickListener(this::onLightButtonClick);

            if (buttonIndex == 4) {
                gridButton.setOnLongClickListener(this::onCheatButtonClick);
            }
        }
    }

    private void startGame() {
        mGame.newGame();
        setButtonColors();
    }

    private void onLightButtonClick(View view) {
        int buttonIndex = mLightGrid.indexOfChild(view);
        int row = buttonIndex / LightsOutGame.GRID_SIZE;
        int col = buttonIndex % LightsOutGame.GRID_SIZE;

        mGame.selectLight(row, col);
        setButtonColors();
        if (mGame.isGameOver()) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
        }
    }

    private void setButtonColors() {
        Button[][] buttons = new Button[LightsOutGame.GRID_SIZE][LightsOutGame.GRID_SIZE];

        for (int buttonIndex = 0; buttonIndex < mLightGrid.getChildCount(); buttonIndex++) {
            Button gridButton = (Button) mLightGrid.getChildAt(buttonIndex);
            int row = buttonIndex / LightsOutGame.GRID_SIZE;
            int col = buttonIndex % LightsOutGame.GRID_SIZE;

            buttons[row][col] = gridButton;
            gridButton.setBackgroundColor(mGame.isLightOn(row, col) ? mLightOnColor : mLightOffColor);
        }
        mGame.setButtonColors(buttons);
    }

    public void onNewGameClick(View view) {
        Toast.makeText(this, R.string.new_game, Toast.LENGTH_SHORT).show();
        startGame();
    }

    private boolean onCheatButtonClick(View view) {
        mGame.turnOffAllLights();
        setButtonColors();
        if (mGame.turnOffAllLights()) {
            Toast.makeText(this, R.string.cheated, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void onColorsClick(View view){
        Intent intent = new Intent(this, ColorActivity.class);
        mColorResultLauncher.launch(intent);

    }

    private final ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        int colorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, R.color.yellow);
                        Log.d("MainActivity", "Received Color: " + Integer.toHexString(colorId));
                        data.putExtra("Color ID for Buttons", colorId);
                        setResult(RESULT_OK, data);
                        mGame.updateLightColor(colorId);
                        setButtonColors();
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && resultCode == RESULT_OK && data != null) {
            int receivedColorId = data.getIntExtra(ColorActivity.EXTRA_COLOR, ContextCompat.getColor(this, R.color.yellow));
            mGame.updateLightColor(receivedColorId);
            setButtonColors();
        }
    }

    //Starting help activity
    public void onHelpClick(View view) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivityForResult(intent, RESULT_OK);
    }


}
