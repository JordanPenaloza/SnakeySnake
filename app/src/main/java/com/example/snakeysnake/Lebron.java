package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Lebron implements GameObjects, Drawable {
    protected Point location = new Point();
    private Bitmap mBitmap;
    protected int mSize;
    protected Point mSpawnRange;
    protected Context context;

    public Lebron(Context context, Point sr, int s) {
        location = new Point(0, 0);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lebron);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, s, s, false);
        this.mSpawnRange = sr;
        this.mSize = s;
        this.location.x = -10;
        this.context = context;

    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmap, location.x * mSize, location.y * mSize, paint);
    }

    public void spawn() {

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lebron);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mSize* 2, mSize * 2, false);
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    public Point getLocation() {
        return location;
    }

}