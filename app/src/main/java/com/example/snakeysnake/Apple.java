package com.example.snakeysnake;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Apple extends AbstractApple  {

    public Apple(Context context, Point sr, int s) {
        super(context, sr, s);
    }

    public void spawn(String type) {
        switch (type) {
            case "green": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
                defaultSpawn();
                break;
            }
            case "blue": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueapple);
                defaultSpawn();
                break;
            }
            case "purple": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.purpleapple);
                defaultSpawn();
                break;
            }
            case "gold": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldapple);
                defaultSpawn();
                break;
            }
            default:
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.redapple);
                defaultSpawn();
                break;
        }
    }

    @Override
    public void spawn() {
        // Do nothing
    }
}

