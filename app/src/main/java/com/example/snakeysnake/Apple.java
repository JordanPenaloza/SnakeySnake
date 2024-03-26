package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;

public class Apple extends AbstractApple  {
    public Apple(Context context, Point sr, int s) {
        super(context, sr, s);

    }

    @Override
    public void spawn() {
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, mSize, mSize, false);

        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }

    @Override
    public void spawn(String color) {
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, mSize, mSize, false);

        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;

    }
}

