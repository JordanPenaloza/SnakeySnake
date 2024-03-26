package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

abstract class AbstractApple {
    protected Point location = new Point();
    protected Point mSpawnRange;
    protected int mSize;
    protected Bitmap mBitmapApple;
    protected Context context;
    public AbstractApple(Context context, Point sr, int s) {
        this.mSpawnRange = sr;
        this.mSize = s;
        this.location.x = -10;
        this.context = context;
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }


    public abstract void spawn();
    public abstract void spawn(String color);


    public Point getLocation(){
        return location;
    }


    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }

}