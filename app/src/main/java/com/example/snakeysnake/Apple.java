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
    @Override
    public void spawn(String type) {
        switch(type) {
            case "red":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.redapple);
                defaultSpawn();
                break;
            case "blue":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueapple);
                defaultSpawn();
                break;
            case "purple":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.purpleapple);
                defaultSpawn();
                break;
            case "gold":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldapple);
                defaultSpawn();
                break;
            case "green":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
                defaultSpawn();
                break;
            default:
        }
    }
}

