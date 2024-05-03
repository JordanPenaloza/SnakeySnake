package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

abstract class AbstractApple implements Drawable, GameObjects {
    protected Point location = new Point();
    protected Point mSpawnRange;
    protected int mSize;
    protected Bitmap mBitmapApple;
    protected Context context;
    protected String type;

    public AbstractApple(Context context, Point sr, int s) {
        this.mSpawnRange = sr;
        this.mSize = s;
        this.location.x = -10;
        this.context = context;
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.redapple);
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }


    protected void defaultSpawn() {
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, mSize, mSize, false);
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    protected abstract void setType(String type);
    protected abstract String getType();


    public Point getLocation(){return location;}
    public int getSize() {return mSize;}

    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }

}