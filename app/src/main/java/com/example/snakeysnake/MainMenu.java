package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class MainMenu {
    // Constants for menu items
    private final String[] menuItems = {"Main Menu", "Play", "Reset", "Quit"};
    // Constants for dimensions and colors, similar to PauseButton
    private final int rectWidth = 500;
    private final int rectHeight; // calculate based on number of items and padding
    private final int itemHeight = 110; // height for each item
    private final int fillColor = Color.WHITE;
    private final int borderColor = Color.BLACK;
    private final int borderWidth = 10;
    private final int topPadding = 5;
    private final int textSize = 120;

    // Paint objects for drawing
    private Paint fillPaint;
    private Paint borderPaint;
    private Paint textPaint;

    // Position variables
    private float startX;
    private float startY; // This will need to be set based on the canvas size



    public MainMenu(Paint paint) {
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // Initialize the Paint objects
        initializePaints();

        // Calculate the total rectangle height based on the number of menu items and padding
        rectHeight = itemHeight * menuItems.length;
    }

    private void initializePaints() {
        fillPaint.setColor(fillColor);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
    }

    public void displayMenu(Canvas canvas, float pauseButtonTopY) {
        // Horizontal center position
        startX = (canvas.getWidth() - rectWidth) / 2.0f;
        // The vertical position should be such that the top menu item's text aligns with the 'Pause' text
        startY = pauseButtonTopY - textSize; // Adjust if you need some padding between 'Pause' and the menu

        // Draw the menu rectangle
        canvas.drawRect(startX, startY, startX + rectWidth, startY + rectHeight, fillPaint);
        canvas.drawRect(startX, startY, startX + rectWidth, startY + rectHeight, borderPaint);

        // Draw the title "Main Menu"
        float titleX = startX + (rectWidth - textPaint.measureText("Main Menu")) / 2.0f;
        canvas.drawText("Main Menu", titleX, startY, textPaint);

        // Adjust startY for the menu items to be below "Main Menu"
        startY += itemHeight;

        // Draw each menu item
        for (int i = 1; i < menuItems.length; i++) { // start at index 1 to skip "Main Menu"
            float textX = startX + (rectWidth - textPaint.measureText(menuItems[i])) / 2.0f; // Center the text in the button
            float textY = startY + ((i - 1) * itemHeight) + ((itemHeight + textSize) / 2.0f); // Vertically center the text in each item
            canvas.drawText(menuItems[i], textX, textY, textPaint);
        }
    }

    // Check if a menu item was clicked
    public boolean menuItemClicked(MotionEvent motionEvent, String item) {
        float fingerX = motionEvent.getX();
        float fingerY = motionEvent.getY();
        int index = java.util.Arrays.asList(menuItems).indexOf(item);
        if (index == -1) {
            return false;
        }
        // Calculate the top and bottom Y values for the clicked item
        float topY = startY + (index * itemHeight);
        float bottomY = topY + itemHeight;

        // Check if the click was within the X and Y bounds of the item
        return (fingerX >= startX && fingerX <= startX + rectWidth) && (fingerY >= topY && fingerY < bottomY);
    }

    // Add additional methods to handle the functionality of each menu item as needed
    // For example, methods like handlePlay(), handleReset(), handleQuit() could be added here
}
