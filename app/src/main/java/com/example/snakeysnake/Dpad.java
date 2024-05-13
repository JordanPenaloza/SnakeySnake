package com.example.snakeysnake;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Dpad extends UI implements Drawable {
    private static final int BUTTON_SIZE = 100;
    private static final int SPACING = 45;
    private static final int CENTER_X = 2000;
    private static final int CENTER_Y = 550;

    private final Rect leftButton;
    private final Rect rightButton;
    private final Rect topButton;
    private final Rect bottomButton;
    public String dpadString = "";

    public Dpad(AssetManager assetManager) {
        super(assetManager);
        leftButton = new Rect(CENTER_X - BUTTON_SIZE - SPACING, CENTER_Y - BUTTON_SIZE / 2,
                CENTER_X - SPACING, CENTER_Y + BUTTON_SIZE / 2);
        rightButton = new Rect(CENTER_X + SPACING, CENTER_Y - BUTTON_SIZE / 2,
                CENTER_X + BUTTON_SIZE + SPACING, CENTER_Y + BUTTON_SIZE / 2);
        topButton = new Rect(CENTER_X - BUTTON_SIZE / 2, CENTER_Y - BUTTON_SIZE - SPACING,
                CENTER_X + BUTTON_SIZE / 2, CENTER_Y - SPACING);
        bottomButton = new Rect(CENTER_X - BUTTON_SIZE / 2, CENTER_Y + SPACING,
                CENTER_X + BUTTON_SIZE / 2, CENTER_Y + BUTTON_SIZE + SPACING);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(leftButton, paint);
        canvas.drawRect(rightButton, paint);
        canvas.drawRect(topButton, paint);
        canvas.drawRect(bottomButton, paint);
    }

    public boolean buttonClicked(MotionEvent motionEvent, Rect button) {
        float fingerX = motionEvent.getX();
        float fingerY = motionEvent.getY();
        return (fingerX > button.left && fingerX < button.right &&
                fingerY > button.top && fingerY < button.bottom);
    }

    public boolean bottomClicked(MotionEvent motionEvent) {
        dpadString = "bottom";
        return buttonClicked(motionEvent, bottomButton);
    }

    public boolean topClicked(MotionEvent motionEvent) {
        dpadString = "top";
        return buttonClicked(motionEvent, topButton);
    }

    public boolean leftClicked(MotionEvent motionEvent) {
        dpadString = "left";
        return buttonClicked(motionEvent, leftButton);
    }

    public boolean rightClicked(MotionEvent motionEvent) {
        dpadString = "right";
        return buttonClicked(motionEvent, rightButton);
    }

    public boolean dpadTouched(MotionEvent motionEvent) {
        return (rightClicked(motionEvent) || leftClicked(motionEvent) ||
                topClicked(motionEvent) || bottomClicked(motionEvent));
    }
}