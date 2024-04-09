package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Dpad extends UI implements Drawable{
    private final int DPAD_SIZE = 100; // Adjust the size of the D-pad squares as needed
    private final int DPAD_GAP = 20; // Adjust the gap between D-pad squares as needed

    public Dpad(Paint paint) {
        super(paint);
    }

    public void display(Canvas canvas, int centerX, int centerY) {
        // Calculate the coordinates for the top-left corner of the D-pad
        int topLeftX = centerX - DPAD_SIZE;
        int topLeftY = centerY - DPAD_SIZE;

        // Draw left square
        canvas.drawRect(topLeftX, centerY - DPAD_SIZE / 2, topLeftX + DPAD_SIZE, centerY + DPAD_SIZE / 2, mPaint);

        // Draw top square
        canvas.drawRect(centerX - DPAD_SIZE / 2, topLeftY, centerX + DPAD_SIZE / 2, topLeftY + DPAD_SIZE, mPaint);

        // Draw right square
        canvas.drawRect(centerX, centerY - DPAD_SIZE / 2, centerX + DPAD_SIZE, centerY + DPAD_SIZE / 2, mPaint);

        // Draw bottom square
        canvas.drawRect(centerX - DPAD_SIZE / 2, centerY, centerX + DPAD_SIZE / 2, centerY + DPAD_SIZE, mPaint);
    }

    // Override the draw method to draw the D-pad squares
    @Override
    public void draw(Canvas canvas, Paint paint) {
        // Assuming you want to draw the D-pad at the center of the canvas
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        display(canvas, centerX, centerY);
    }
}