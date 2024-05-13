package com.example.snakeysnake;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class UI {
    private static final int TITLE_TEXT_SIZE = 250;
    private static final int SCORE_TEXT_SIZE = 120;
    private static final int NAMES_TEXT_SIZE = 60;
    private static final int GAME_OVER_TEXT_SIZE = 100;

    private static final int BLACK_COLOR = Color.argb(255, 0, 0, 0);
    private static final int RED_COLOR = Color.RED;

    private Paint paint;

    public UI(AssetManager assetManager) {
        paint = new Paint();
        Typeface typeface = Typeface.createFromAsset(assetManager, "fonts/MightySouly-lxggD.ttf");
        paint.setTypeface(typeface);
        paint.setAntiAlias(true); // Smoother text
    }

    public void displayPoints(Canvas canvas, int score) {
        setupPaint(BLACK_COLOR, SCORE_TEXT_SIZE);
        canvas.drawText(String.valueOf(score), 20, 120, paint);
    }


    private void setupPaint(int color, int textSize) {
        setupPaint(color, textSize, paint);
    }

    private void setupPaint(int color, int textSize, Paint paint) {
        paint.setColor(color);
        paint.setTextSize(textSize);
    }
}