package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class UI {
    private Paint mPaint;

    public UI(Paint paint) {

        mPaint = paint;
    }

    public void displayTapToPlayMessage(Canvas mCanvas) {
        synchronized (mCanvas) {
            mPaint.setColor(Color.argb(255, 255, 255, 255));
            mPaint.setTextSize(250);
            mCanvas.drawText("Tap To Play!", 200, 700, mPaint);
        }
    }


}