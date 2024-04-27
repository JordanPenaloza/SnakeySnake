package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

public class MainMenu {
    // Constants for menu items
    private final String[] menuItems = {"Resume", "Quit", "New Game"};
    // Updated menu items for game over state
    private final String[] gameOverMenuItems = {"New Game", "Quit"};

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
        // Choose the right set of menu items based on the game state
        String[] currentMenuItems = isGameOver ? gameOverMenuItems : menuItems;

        // Update rectHeight for the current number of menu items
        rectHeight = (itemHeight * currentMenuItems.length) + (separationHeight * (currentMenuItems.length - 1));

        // Center startX and startY on the canvas
        startX = (canvas.getWidth() - rectWidth) / 2.0f;
        startY = (canvas.getHeight() - rectHeight) / 2.0f;

        // Create and draw menu rectangle
        RectF menuRect = new RectF(startX, startY, startX + rectWidth, startY + rectHeight);
        canvas.drawRect(menuRect, fillPaint);

        // Initialize the buttonRects array with the correct number of RectF objects
        buttonRects = new RectF[currentMenuItems.length];

        // Draw each menu item with a line separating them
        for (int i = 0; i < currentMenuItems.length; i++) {
            float top = startY + (i * (itemHeight + separationHeight));
            float bottom = top + itemHeight;
            buttonRects[i] = new RectF(startX, top, startX + rectWidth, bottom);

            // Draw the separation lines
            if (i > 0) {
                canvas.drawRect(startX, top - separationHeight, startX + rectWidth, top, borderPaint);
            }

            // Draw the button rectangles and text
            canvas.drawRect(buttonRects[i], fillPaint);
            float textY = top + itemHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(currentMenuItems[i], startX + rectWidth / 2f, textY, textPaint);
        }

        // Draw the border of the menu
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
        // Recalculate buttonRects based on game over state
        buttonRects = new RectF[isGameOver ? gameOverMenuItems.length : menuItems.length];
        // Your code here to initialize the RectF objects for buttonRects if necessary
    }
}
