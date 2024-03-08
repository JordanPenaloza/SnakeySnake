package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;

abstract class AbstractApple {
    private Point location = new Point();
    private Point mSpawnRange;
    private int mSize;
    private Bitmap mBitmapApple;
    public AbstractApple(Context context, Point sr, int s){
        this.mSpawnRange = sr;
        this.mSize = s;
        this.location.x = -10;


        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);

        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, s, s, false);
    }


    public void spawn(){
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;

    }


    public Point getLocation(){
        return location;
    }


    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBitmapApple,
                location.x * mSize, location.y * mSize, paint);

    }

}