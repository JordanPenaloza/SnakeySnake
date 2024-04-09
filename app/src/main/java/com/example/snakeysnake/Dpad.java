package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Dpad extends UI implements Drawable {


    public Dpad(Paint paint) {
        super(paint);
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
}