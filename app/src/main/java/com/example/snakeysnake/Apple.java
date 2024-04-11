package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Objects;
import java.util.Random;

public class Apple extends AbstractApple  {
    public Apple(Context context, Point sr, int s) {
        super(context, sr, s);

    }

    private void defaultSpawn() {
        mBitmapApple = Bitmap.createScaledBitmap(mBitmapApple, mSize, mSize, false);

        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
    }


    // Method overridding
    @Override
    public void spawn() {
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        defaultSpawn();
    }

    // Method overloading
    @Override
    public void spawn(String type) {
        if(Objects.equals(type, "green")) {
            mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
            defaultSpawn();
        } else if (Objects.equals(type, "death")) {
            // Will Spawn Death Apple (temp spawns head)
            mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
            defaultSpawn();
        }
    }
}

