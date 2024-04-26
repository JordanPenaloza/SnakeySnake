package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;

public class DeathApple extends AbstractApple  {

    public DeathApple(Context context, Point sr, int s) {
        super(context, sr, s);
    }

    @Override
    public void spawn() {
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.deathapple);
        defaultSpawn();
    }

    public void deSpawn() {
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, mSize, mSize, false);
        location.x = -1000;
        location.y = -1000;
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.deathapple);
        defaultSpawn();
    }
}

