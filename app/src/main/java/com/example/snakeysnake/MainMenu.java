package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.content.res.AssetManager;

public class MainMenu {
    // Constants for menu items and styles
    private final String[] menuItems = {"Resume", "Quit", "New Game"};
    private final String[] gameOverMenuItems = {"New Game", "Quit"};
    private final int rectWidth = 600;
    private int rectHeight;
    private final int itemHeight = 120;
    private final int separationHeight = 2;
    private final int fillColor = Color.argb(200, 255, 255, 255); // 200 is alpha for transparency
    private final int borderColor = Color.argb(255, 200, 200, 200); // Light grey color
    private final int borderWidth = 5; // Thinner border
    private final int textSize = 120;
    private final int textPadding = 20;
    private final int cornerRadius = 20; // Radius for round corners
    private boolean isGameOver = false;

    private Paint fillPaint;
    private Paint borderPaint;
    private Paint textPaint;

    private float startX;
    private float startY;

    private RectF[] buttonRects;

    public MainMenu(Paint paint, AssetManager assetManager) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Load the custom font
        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/MightySouly-lxggD.ttf");
        textPaint.setTypeface(customFont);

        initializePaints();

        rectHeight = (itemHeight * menuItems.length) + textPadding * 2;
        buttonRects = new RectF[menuItems.length];
    }

    private void initializePaints() {
        fillPaint.setColor(fillColor);
        fillPaint.setStyle(Paint.Style.FILL);

        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void displayMenu(Canvas canvas) {
        String[] currentMenuItems = isGameOver ? gameOverMenuItems : menuItems;
        rectHeight = (itemHeight * currentMenuItems.length) + (separationHeight * (currentMenuItems.length - 1));

        startX = (canvas.getWidth() - rectWidth) / 2.0f;
        startY = (canvas.getHeight() - rectHeight) / 2.0f;

        RectF menuRect = new RectF(startX, startY, startX + rectWidth, startY + rectHeight);

        buttonRects = new RectF[currentMenuItems.length];
        for (int i = 0; i < currentMenuItems.length; i++) {
            float top = startY + (i * (itemHeight + separationHeight));
            float bottom = top + itemHeight;
            buttonRects[i] = new RectF(startX, top, startX + rectWidth, bottom);

            if (i > 0) {
                canvas.drawRect(startX, top - separationHeight, startX + rectWidth, top, borderPaint);
            }

            // Use drawRoundRect for rounded corners
            canvas.drawRoundRect(buttonRects[i], cornerRadius, cornerRadius, fillPaint);
            float textY = top + itemHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(currentMenuItems[i], startX + rectWidth / 2f, textY, textPaint);
        }

        canvas.drawRoundRect(menuRect, cornerRadius, cornerRadius, borderPaint);
    }




    public String menuItemClicked(MotionEvent motionEvent) {
        float fingerX = motionEvent.getX();
        float fingerY = motionEvent.getY();
        String[] currentMenuItems = isGameOver ? gameOverMenuItems : menuItems;
        for (int i = 0; i < buttonRects.length; i++) {
            if (buttonRects[i].contains(fingerX, fingerY)) {
                return currentMenuItems[i];  // Return the name of the button clicked based on the current game state
            }
        }
        return null;  // No button was clicked
    }


    // Method to set the game over state
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
        // Recalculate buttonRects based on game over state
        buttonRects = new RectF[isGameOver ? gameOverMenuItems.length : menuItems.length];
        // Your code here to initialize the RectF objects for buttonRects if necessary
    }

    public boolean isTouchOnMenu(float x, float y) {
        for (RectF button : buttonRects) {
            if (button.contains(x, y)) {
                return true;
            }
        }
        return false;
    }


}
