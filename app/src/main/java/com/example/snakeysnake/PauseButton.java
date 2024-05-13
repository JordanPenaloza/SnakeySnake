package com.example.snakeysnake;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class PauseButton extends UI {
    private final int rectWidth = 500;
    private final int rectHeight = 110;
    private final int fillColor = Color.argb(200, 255, 255, 255); // Transparent white
    private final int borderColor = Color.argb(255, 200, 200, 200); // Light grey
    private final int borderWidth = 5; // Thinner border
    private final int topPadding = 5;
    private final float cornerRadius = 20; // Rounded corners
    private Paint fillPaint;
    private Paint borderPaint;
    private Paint textPaint;
    private RectF buttonRect;

    public PauseButton(Paint paint, AssetManager assetManager) {
        super(paint, assetManager);

        // Initialize Paint objects
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Set up Paint properties
        fillPaint.setColor(fillColor);
        fillPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        // Load the custom font
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/MightySouly-lxggD.ttf");
        textPaint.setTypeface(customFont);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(120);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void displayPauseButton(Canvas canvas) {
        float startX = (canvas.getWidth() - rectWidth) / 2.0f;
        float startY = topPadding;
        buttonRect = new RectF(startX, startY, startX + rectWidth, startY + rectHeight);

        // Draw rounded rectangle for button
        canvas.drawRoundRect(buttonRect, cornerRadius, cornerRadius, fillPaint);
        canvas.drawRoundRect(buttonRect, cornerRadius, cornerRadius, borderPaint);

        // Draw the text
        float textY = startY + rectHeight / 2 - (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText("Pause", startX + rectWidth / 2, textY, textPaint);
    }

    public boolean pauseButtonClicked(MotionEvent motionEvent) {
        float fingerX = motionEvent.getX();
        float fingerY = motionEvent.getY();
        return (fingerX > buttonRect.left && fingerX < buttonRect.right &&
                fingerY > buttonRect.top && fingerY < buttonRect.bottom);
    }
}
