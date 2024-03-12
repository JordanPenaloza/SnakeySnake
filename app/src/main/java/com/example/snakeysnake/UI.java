package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class UI {
    private Paint mPaint;

    public UI(Paint paint) {

        mPaint = paint;
    }

    public void displayTapToPlayMessage(Canvas mCanvas) {
        synchronized (mCanvas) {
            mPaint.setColor(Color.argb(255, 0, 0, 0));
            mPaint.setTextSize(250);
            Typeface typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
            mPaint.setTypeface(typeface);

            mCanvas.drawText("Tap To Play!", 200, 700, mPaint);

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
        mCanvas.drawText("Jesus Pablo ", 1600, 190, mPaint);
    }


}