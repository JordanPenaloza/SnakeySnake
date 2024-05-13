package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Lebron implements GameObjects, Drawable {
    private static final Random random = new Random();

    private final Bitmap mBitmap;
    private final Point location = new Point();
    private final int mSize;
    private final Point mSpawnRange;
    private final Context context;

    public Lebron(Context context, Point spawnRange, int size) {
        this.context = context;
        this.mSpawnRange = spawnRange;
        this.mSize = size;
        this.mBitmap = loadScaledBitmap(R.drawable.lebron, size);
        resetLocation();
    }

    private Bitmap loadScaledBitmap(int resId, int size) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return Bitmap.createScaledBitmap(originalBitmap, size, size, false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmap, location.x * mSize, location.y * mSize, paint);
    }

    @Override
    public void spawn() {
        resetLocation(); // Resets the location each time it spawns
    }

    private void resetLocation() {
        location.x = random.nextInt(mSpawnRange.x);
        location.y = random.nextInt(mSpawnRange.y);
    }

    @Override
    public Point getLocation() {
        return new Point(location);
    }
}