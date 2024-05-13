package com.example.snakeysnake;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Dpad extends UI implements Drawable {
    public String dpadString = "";
    public Dpad(Paint paint, AssetManager assetManager) {
        super(paint, assetManager);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        int size = 100;
        int spacing = 45;
        int centerX = 2000;
        int centerY = 550;

        int halfSize = size / 2;
        canvas.drawRect(centerX - size - spacing, centerY - halfSize, centerX - spacing, centerY + halfSize, paint);
        canvas.drawRect(centerX + spacing, centerY - halfSize, centerX + size + spacing, centerY + halfSize, paint);
        canvas.drawRect(centerX - halfSize, centerY - size - spacing, centerX + halfSize, centerY - spacing, paint);
        canvas.drawRect(centerX - halfSize, centerY + spacing, centerX + halfSize, centerY + size + spacing, paint);
    }
    public boolean bottomClicked(MotionEvent motionEvent) {
        float fingerX = (int) motionEvent.getX();
        float fingerY = (int) motionEvent.getY();
        final float buttonX1 = 1950;
        final float buttonX2 = 2050;
        final float buttonY1 = 600;
        final float buttonY2 = 700;
        dpadString = "bottom";
        return ((fingerX > buttonX1 && fingerX < buttonX2) && (fingerY > buttonY1 && fingerY < buttonY2));
    }
    public boolean topClicked(MotionEvent motionEvent) {
        float fingerX = (int) motionEvent.getX();
        float fingerY = (int) motionEvent.getY();
        final float buttonX1 = 1950;
        final float buttonX2 = 2050;
        final float buttonY1 = 400;
        final float buttonY2 = 500;
        dpadString = "top";
        return ((fingerX > buttonX1 && fingerX < buttonX2) && (fingerY > buttonY1 && fingerY < buttonY2));
    }
    public boolean leftClicked(MotionEvent motionEvent) {
        float fingerX = (int) motionEvent.getX();
        float fingerY = (int) motionEvent.getY();
        final float buttonX1 = 1850;
        final float buttonX2 = 1950;
        final float buttonY1 = 500;
        final float buttonY2 = 600;
        dpadString = "left";
        return ((fingerX > buttonX1 && fingerX < buttonX2) && (fingerY > buttonY1 && fingerY < buttonY2));
    }
    public boolean rightClicked(MotionEvent motionEvent) {
        float fingerX = (int) motionEvent.getX();
        float fingerY = (int) motionEvent.getY();
        final float buttonX1 = 2050;
        final float buttonX2 = 2150;
        final float buttonY1 = 500;
        final float buttonY2 = 600;
        dpadString = "right";
        return ((fingerX > buttonX1 && fingerX < buttonX2) && (fingerY > buttonY1 && fingerY < buttonY2));
    }
    public boolean dpadTouched(MotionEvent motionEvent) {
        return(rightClicked(motionEvent) || leftClicked(motionEvent) || topClicked(motionEvent) || bottomClicked(motionEvent));
    }
}
