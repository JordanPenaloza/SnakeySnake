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
    protected Point spawnRange;
    protected int size;
    protected Bitmap bitmapApple;
    protected Context context;
    protected String type;

    public AbstractApple(Context context, Point spawnRange, int size, int drawableResourceId) {
        this.context = context;
        this.spawnRange = spawnRange;
        this.size = size;
        this.bitmapApple = loadAndScaleBitmap(drawableResourceId);
        resetLocation();
    }

    private Bitmap loadAndScaleBitmap(int resourceId) {
        Bitmap initialBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        return Bitmap.createScaledBitmap(initialBitmap, size, size, false);
    }

    public void spawn(String type) {
        this.type = type;
        resetLocation();
    }

    void resetLocation() {
        Random random = new Random();
        location.x = random.nextInt(spawnRange.x);
        location.y = random.nextInt(spawnRange.y);
    }

    public Point getLocation() {
        return location;
    }

    public int getSize() {
        return size;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmapApple, location.x * size, location.y * size, paint);
    }

    protected abstract void setType(String type);

    protected abstract String getType();
}