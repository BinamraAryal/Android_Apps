package com.classwork.lightsoutgame;

import android.graphics.Color;

import android.widget.Button;

import java.util.Random;

import android.util.Log;

public class LightsOutGame {
    public static final int GRID_SIZE = 3;

    private int mLightOnColor;
    private int mLightOffColor;

    // Lights that make up the grid
    private final boolean[][] mLightsGrid;

    public LightsOutGame() {
        mLightsGrid = new boolean[GRID_SIZE][GRID_SIZE];
    }

    public void newGame() {
        Random randomNumGenerator = new Random();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = randomNumGenerator.nextBoolean();
            }
        }
    }

    public boolean isLightOn(int row, int col) {
        return mLightsGrid[row][col];
    }

    public void selectLight(int row, int col) {
        mLightsGrid[row][col] = !mLightsGrid[row][col];
        if (row > 0) {
            mLightsGrid[row - 1][col] = !mLightsGrid[row - 1][col];
        }
        if (row < GRID_SIZE - 1) {
            mLightsGrid[row + 1][col] = !mLightsGrid[row + 1][col];
        }
        if (col > 0) {
            mLightsGrid[row][col - 1] = !mLightsGrid[row][col - 1];
        }
        if (col < GRID_SIZE - 1) {
            mLightsGrid[row][col + 1] = !mLightsGrid[row][col + 1];
        }
    }

    public boolean isGameOver() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (mLightsGrid[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    //cheat method
    public boolean turnOffAllLights() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = false;
            }
        }
        return true;
    }

    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                char value = mLightsGrid[row][col] ? 'T' : 'F';
                boardString.append(value);
            }
        }

        return boardString.toString();
    }

    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mLightsGrid[row][col] = gameState.charAt(index) == 'T';
                index++;
            }
        }
    }

    // Add a method to update the light color
    public void updateLightColor(int color) {
        mLightOnColor = color;
        Log.d("LightsOutGame", "Updated Light Color: " + Integer.toHexString(mLightOnColor)); // Log the updated color
    }

    // Modify the setButtonColors() method to set default colors initially
    public void setButtonColors(Button[][] buttons) {

        if (mLightOnColor == 0 || mLightOffColor == 0) {
            // Set the initial colors
            mLightOnColor = Color.YELLOW;
            mLightOffColor = Color.BLACK;
        }

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                buttons[row][col].setBackgroundColor(mLightsGrid[row][col] ? mLightOnColor : mLightOffColor);
            }
        }
    }

}


