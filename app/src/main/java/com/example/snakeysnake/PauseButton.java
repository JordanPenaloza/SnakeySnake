package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class PauseButton extends UI {
    // Initial Variables
    private final int rectWidth = 600;
    private final int rectHeight = 110;
    private final int fillColor = Color.WHITE;
    private final int borderColor = Color.BLACK;
    private final int borderWidth = 10;
    private final int topPadding = 5;
    private static PauseButton instance;

    public PauseButton(Paint paint) {

        super(paint);
    }

    public static synchronized PauseButton getInstance(Paint paint){
        if (instance == null){
            instance = new PauseButton(paint);
        }
        return instance;
    }
    public void displayPauseButton(Canvas canvas, Boolean mPaused,Boolean dead) {
        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        initializeFillPaint(fillPaint);
        initializeBorder(borderPaint);
        draw(canvas, fillPaint, borderPaint,mPaused,dead);
    }

    private void draw(Canvas canvas, Paint fillPaint, Paint borderPaint, Boolean mPaused,Boolean dead) {
        int canvasWidth = canvas.getWidth();
        float startX = (canvasWidth - rectWidth) / 2.0f;
        float halfBorderWidth = borderWidth / 2.0f;
        canvas.drawRect(startX, topPadding, startX +(dead ? rectWidth: (mPaused ? rectWidth: 500)), topPadding + rectHeight, fillPaint);
        canvas.drawRect(startX + halfBorderWidth, topPadding + halfBorderWidth, startX + (dead ? rectWidth - halfBorderWidth:(mPaused ? rectWidth - halfBorderWidth: 500 - halfBorderWidth)), topPadding + rectHeight - halfBorderWidth, borderPaint);
        mPaint.setColor(Color.argb(255, 0, 0, 0));
        mPaint.setTextSize(120);
        canvas.drawText((dead ? "Restart":(mPaused ? "Resume" : "Pause")), 900, 100, mPaint);
    }

    private void initializeFillPaint(Paint fillPaint) {
        fillPaint.setColor(fillColor);
    }
    private void initializeBorder(Paint borderPaint) {
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
    }
    public boolean pauseButtonClicked(MotionEvent motionEvent) {
        float fingerX = (int) motionEvent.getX();
        float fingerY = (int) motionEvent.getY();
        final float buttonX1 = 859;
        final float buttonX2 = 1346;
        final float buttonY1 = 6;
        final float buttonY2 = 104;
        //check if finger pressed
        return ((fingerX > buttonX1 && fingerX < buttonX2) && (fingerY > buttonY1 && fingerY < buttonY2));
    }
}
