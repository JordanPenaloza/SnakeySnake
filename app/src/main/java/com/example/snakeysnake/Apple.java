package com.example.snakeysnake;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Apple extends AbstractApple  {

    public Apple(Context context, Point sr, int s) {
        super(context, sr, s);

    }

    @Override
    protected void setType(String type) {
        this.type = type;
    }

    @Override
    protected String getType() {
        if(this.type == null) {
            return "NULL";
        }
        return this.type;
    }

    public void spawn(String type) {
        switch (type) {
            case "green": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
                setType("green");
                defaultSpawn();
                break;
            }
            case "blue": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueapple);
                setType("blue");
                defaultSpawn();
                break;
            }
            case "purple": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.purpleapple);
                setType("purple");
                defaultSpawn();
                break;
            }
            case "gold": {
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldapple);
                setType("gold");
                defaultSpawn();
                break;
            }
            default:
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.redapple);
                setType("red");
                defaultSpawn();
                break;
        }
    }

    @Override
    public void spawn() {
        // Do nothing
    }
}

