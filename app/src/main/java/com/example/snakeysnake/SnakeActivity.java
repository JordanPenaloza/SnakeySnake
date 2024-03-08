package com.example.snakeysnake;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class SnakeActivity extends Activity {
    SnakeGame mSnakeGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mSnakeGame = new SnakeGame(this, size);
        setContentView(mSnakeGame);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSnakeGame.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSnakeGame.pause();
    }
}