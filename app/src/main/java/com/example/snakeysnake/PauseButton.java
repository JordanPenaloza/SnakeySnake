package com.example.snakeysnake;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class PauseButton extends UI {
    private static final int RECT_WIDTH = 500;
    private static final int RECT_HEIGHT = 110;
    private static final int FILL_COLOR = Color.argb(200, 255, 255, 255); // Transparent white
    private static final int BORDER_COLOR = Color.argb(255, 200, 200, 200); // Light grey
    private static final int BORDER_WIDTH = 5; // Thinner border
    private static final int TOP_PADDING = 5;
    private static final float CORNER_RADIUS = 20; // Rounded corners

    private Paint fillPaint;
    private Paint borderPaint;
    private Paint textPaint;
    private RectF buttonRect;

    public PauseButton(AssetManager assetManager) {
        super(assetManager);

        // Initialize and setup paints
        initializePaints(assetManager);
    }

    private void initializePaints(AssetManager assetManager) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(FILL_COLOR);
        fillPaint.setStyle(Paint.Style.FILL);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(BORDER_COLOR);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(BORDER_WIDTH);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(Typeface.createFromAsset(assetManager, "fonts/MightySouly-lxggD.ttf"));
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(120);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setupButton(float screenWidth) {
        float startX = (screenWidth - RECT_WIDTH) / 2.0f;
        float startY = TOP_PADDING;
        buttonRect = new RectF(startX, startY, startX + RECT_WIDTH, startY + RECT_HEIGHT);
    }

    public void displayPauseButton(Canvas canvas) {
        if (buttonRect == null) {
            setupButton(canvas.getWidth());
        }
        canvas.drawRoundRect(buttonRect, CORNER_RADIUS, CORNER_RADIUS, fillPaint);
        canvas.drawRoundRect(buttonRect, CORNER_RADIUS, CORNER_RADIUS, borderPaint);
        float textY = buttonRect.centerY() - (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText("Pause", buttonRect.centerX(), textY, textPaint);
    }

    public boolean pauseButtonClicked(MotionEvent motionEvent) {
        if (buttonRect != null) {
            float fingerX = motionEvent.getX();
            float fingerY = motionEvent.getY();
            return buttonRect.contains(fingerX, fingerY);
        }
        return false;
    }
}
