package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class MainMenu {
    // Constants for menu items
    private final String[] menuItems = {"Resume", "Quit", "New Game"};
    // Constants for dimensions and colors
    private final int rectWidth = 600; // Increased width
    private int rectHeight; // Dynamic calculation based on items and separation
    private final int itemHeight = 120; // Increased height for each item
    private final int separationHeight = 2; // Height for the separation between items
    private final int fillColor = Color.WHITE;
    private final int borderColor = Color.BLACK;
    private final int borderWidth = 10;
    private final int textSize = 120;
    private boolean isGameOver = false;

    // Padding around text
    private final int textPadding = 20;

    // Paint objects for drawing
    private Paint fillPaint;
    private Paint borderPaint;
    private Paint textPaint;

    // Position variables
    private float startX;
    private float startY;

    // Rectangles for the buttons
    private RectF[] buttonRects;

    public MainMenu(Paint paint) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

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
        // Calculate the position of the menu based on the canvas size
        startX = (canvas.getWidth() - rectWidth) / 2.0f;
        startY = (canvas.getHeight() - rectHeight) / 2.0f; // Adjusted to be dynamic

        // Increase the height for each button to create separation lines between them
        int separationLineHeight = 4; // Height of the separation line
        rectHeight = (itemHeight + separationLineHeight) * menuItems.length - separationLineHeight; // Adjust rectHeight to include separation lines

        // Draw the menu rectangle
        RectF menuRect = new RectF(startX, startY, startX + rectWidth, startY + rectHeight);
        canvas.drawRect(menuRect, fillPaint);

        // Check if it's game over and set the startY accordingly
        if (isGameOver) {
            startY = (canvas.getHeight() / 4f); // Position the menu lower for game over
        } else {
            startY = (canvas.getHeight() - rectHeight) / 2.0f; // Center the menu in the middle
        }

        // Draw "GAME OVER" if the game is over
        if (isGameOver) {
            float gameOverTextSize = textSize * 1.5f;
            textPaint.setTextSize(gameOverTextSize);
            canvas.drawText("GAME OVER", canvas.getWidth() / 2.0f, startY - textPadding, textPaint);
            textPaint.setTextSize(textSize); // Reset text size for menu items
            startY += gameOverTextSize; // Offset startY to draw the menu below "GAME OVER"
        }

        // Draw each menu item with a line separating them
        for (int i = 0; i < menuItems.length; i++) {
            float top = startY + i * (itemHeight + separationHeight);
            float bottom = top + itemHeight;
            buttonRects[i] = new RectF(startX, top, startX + rectWidth, bottom);

            // Draw the separation lines
            if (i > 0) {
                float lineTop = top - separationHeight;
                canvas.drawRect(startX, lineTop, startX + rectWidth, top, borderPaint);
            }

            // Fill and draw the text
            canvas.drawRect(buttonRects[i], fillPaint);
            float textY = top + itemHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(menuItems[i], startX + rectWidth / 2f, textY, textPaint);
        }

        // Draw the outer border last so it covers the separation lines at the edges
        canvas.drawRect(menuRect, borderPaint);
    }


    // Check if a menu item was clicked
    public String menuItemClicked(MotionEvent motionEvent) {
        float fingerX = motionEvent.getX();
        float fingerY = motionEvent.getY();
        for (int i = 0; i < buttonRects.length; i++) {
            if (buttonRects[i].contains(fingerX, fingerY)) {
                return menuItems[i];  // Return the name of the button clicked
            }
        }
        return null;  // No button was clicked
    }

    // Method to set the game over state
    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }
}
