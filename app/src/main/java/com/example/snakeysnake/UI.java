package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
public class UI {
    protected Paint mPaint;
    protected Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);


    public UI(Paint paint) {

        mPaint = paint;
        mPaint.setTypeface(typeface);
    }

    public void displayTapToPlayMessage(Canvas mCanvas) {
        synchronized (mCanvas) {
            mPaint.setColor(Color.argb(255, 0, 0, 0));
            mPaint.setTextSize(250);
            mCanvas.drawText("Press Resume To Play!", 200, 700, mPaint);

        }
    }
    public void displayPoints(Canvas mCanvas, int mScore) {
        synchronized (mCanvas) {
            mPaint.setColor(Color.argb(255, 0, 0, 0));
            mPaint.setTextSize(120);
            mCanvas.drawText("" + mScore, 20, 120, mPaint);
        }
    }
    public void displayNames(Canvas mCanvas) {
        mPaint.setColor(Color.argb(255, 0, 0, 0));
        mPaint.setTextSize(60);
        mCanvas.drawText("Jordan Penaloza ", 1600, 120, mPaint);
        mCanvas.drawText("Jesus-Pablo Alfaro ", 1500, 190, mPaint);
        mCanvas.drawText("Alicia Luna",1600, 260, mPaint);
        mCanvas.drawText("Eric Delgado",1600, 310, mPaint);
    }

    public void displayGameOver(Canvas canvas) {
        // Set up the paint for the game over text
        Paint gameOverPaint = new Paint(mPaint); // Clone the existing paint
        gameOverPaint.setTextSize(100); // Set the text size for game over message
        gameOverPaint.setColor(Color.RED); // Set the color to red for the game over message
        gameOverPaint.setTextAlign(Paint.Align.CENTER); // Center the text

        // Calculate the position to draw the text
        float x = canvas.getWidth() / 2f;
        float y = canvas.getHeight() / 2f;

        // Draw the game over text
        canvas.drawText("GAME OVER", x, y, gameOverPaint);
    }
}
