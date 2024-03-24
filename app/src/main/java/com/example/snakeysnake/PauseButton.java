package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class PauseButton extends UI {
    // Initial Variables
    private final int rectWidth = 500;
    private final int rectHeight = 110;
    private final int fillColor = Color.WHITE;
    private final int borderColor = Color.BLACK;
    private final int borderWidth = 10;
    private final int topPadding = 5;

    public PauseButton(Paint paint) {
        super(paint);
    }

    public void displayPauseButton(Canvas canvas) {

        // Calculate the horizontal start position to center the rectangle
        int canvasWidth = canvas.getWidth();
        float startX = (canvasWidth - rectWidth) / 2.0f;

        // Initialize fill paint for the rectangle
        Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(fillColor);

        // Draw the rectangle filled with White
        canvas.drawRect(startX, topPadding, startX + rectWidth, topPadding + rectHeight, fillPaint);

        // Initialize border paint
        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        // Adjust for stroke width
        float halfBorderWidth = borderWidth / 2.0f;

        // Draw the border
        canvas.drawRect(startX + halfBorderWidth, topPadding + halfBorderWidth, startX + rectWidth - halfBorderWidth, topPadding + rectHeight - halfBorderWidth, borderPaint);

        // Draw Pause Text
        mPaint.setColor(Color.argb(255, 0, 0, 0));
        mPaint.setTextSize(120);
        canvas.drawText("Pause", 930, 100, mPaint);
    }

    // Testing Function used for debugging.
    public void displayCounter(Canvas mCanvas, int pauseCount) {
        synchronized (mCanvas) {
            mPaint.setColor(Color.argb(255, 0, 0, 0));
            mPaint.setTextSize(120);
            mCanvas.drawText("" + pauseCount, 100, 120, mPaint);
        }
    }

    public boolean pauseButtonClicked(Canvas mCanvas, MotionEvent motionEvent) {
        // Get finger press cords
        float fingerX = (int) motionEvent.getX();
        float fingerY = (int) motionEvent.getY();

        final float buttonX1 = 859;
        final float buttonX2 = 1346;
        final float buttonY1 = 6;
        final float buttonY2 = 104;


        // Check if finger in pause button
        return ((fingerX > buttonX1 && fingerX < buttonX2) && (fingerY > buttonY1 && fingerY < buttonY2));
    }
}
