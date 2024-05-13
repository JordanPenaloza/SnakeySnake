package com.example.snakeysnake;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;

public class MainMenu {
    private static final int RECT_WIDTH = 600;
    private static final int ITEM_HEIGHT = 120;
    private static final int SEPARATION_HEIGHT = 2;
    private static final int BORDER_WIDTH = 5;
    private static final int TEXT_SIZE = 120;
    private static final int CORNER_RADIUS = 20;

    private final String[] menuItems = {"Resume", "Quit", "New Game"};
    private final String[] gameOverMenuItems = {"New Game", "Quit"};
    private String[] currentMenuItems;
    private RectF[] buttonRects;
    private boolean isGameOver;
    private final Paint fillPaint;
    private final Paint borderPaint;
    private final Paint textPaint;
    private final int screenWidth;
    private final int screenHeight;
    private int rectHeight;
    private RectF menuRect;

    public MainMenu(AssetManager assetManager, int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        initializePaints(assetManager);
        rectHeight = (ITEM_HEIGHT * menuItems.length) + CORNER_RADIUS * 2;
        buttonRects = new RectF[menuItems.length];
        menuRect = new RectF();
        setMenuItems();
    }

    private void initializePaints(AssetManager assetManager) {
        fillPaint.setColor(Color.argb(200, 255, 255, 255));
        fillPaint.setStyle(Paint.Style.FILL);

        borderPaint.setColor(Color.argb(255, 200, 200, 200));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(BORDER_WIDTH);

        Typeface customFont = Typeface.createFromAsset(assetManager, "fonts/MightySouly-lxggD.ttf");
        textPaint.setTypeface(customFont);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void setMenuItems() {
        currentMenuItems = isGameOver ? gameOverMenuItems : menuItems;
        rectHeight = (ITEM_HEIGHT * currentMenuItems.length) + (SEPARATION_HEIGHT * (currentMenuItems.length - 1));

        float startX = (screenWidth - RECT_WIDTH) / 2.0f;
        float startY = (screenHeight - rectHeight) / 2.0f;
        buttonRects = new RectF[currentMenuItems.length];
        menuRect = new RectF(startX, startY, startX + RECT_WIDTH, startY + rectHeight);
        for (int i = 0; i < currentMenuItems.length; i++) {
            float top = startY + i * (ITEM_HEIGHT + SEPARATION_HEIGHT);
            float bottom = top + ITEM_HEIGHT;
            buttonRects[i] = new RectF(startX, top, startX + RECT_WIDTH, bottom);
        }
    }

    public void displayMenu(Canvas canvas) {
        for (int i = 0; i < buttonRects.length; i++) {
            canvas.drawRoundRect(buttonRects[i], CORNER_RADIUS, CORNER_RADIUS, fillPaint);
            float textY = buttonRects[i].top + ITEM_HEIGHT / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(currentMenuItems[i], buttonRects[i].centerX(), textY, textPaint);
        }
        canvas.drawRoundRect(menuRect, CORNER_RADIUS, CORNER_RADIUS, borderPaint);
    }

    public String menuItemClicked(MotionEvent motionEvent) {
        float fingerX = motionEvent.getX();
        float fingerY = motionEvent.getY();
        for (int i = 0; i < buttonRects.length; i++) {
            if (buttonRects[i].contains(fingerX, fingerY)) {
                return currentMenuItems[i];
            }
        }
        return null;
    }

    public void setGameOver(boolean isGameOver) {
        if (this.isGameOver != isGameOver) {
            this.isGameOver = isGameOver;
            setMenuItems();  // Recalculate button rectangles if game state changes
        }
    }
}