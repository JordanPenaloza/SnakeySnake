package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;

public class AppleGreen extends AbstractApple {

    public AppleGreen(Context context, Point sr, int s){
        super(context, sr, s);

    }

    @Override
    public void draw(Canvas canvas, Paint paint){
        Paint coloredPaint = new Paint(paint);
        ColorFilter colorFilter = new LightingColorFilter(0xFF00FF00, 0xFF008000);
        coloredPaint.setColorFilter(colorFilter);
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, coloredPaint);

    }
}
