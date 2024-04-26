package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Objects;
import java.util.Random;

public class Apple extends AbstractApple  {
    private String type;
    public Apple(Context context, Point sr, int s) {
        super(context, sr, s);
        this.type = " ";
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
                setType("red");
                defaultSpawn();
                break;
            case "blue":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueapple);
                setType("blue");
                defaultSpawn();
                break;
            case "purple":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.purpleapple);
                setType("purple");
                defaultSpawn();
                break;
            case "gold":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldapple);
                setType("gold");
                defaultSpawn();
                break;
            case "green":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
                setType("green");
                defaultSpawn();
                break;
            default:
        }
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;

    }
}

