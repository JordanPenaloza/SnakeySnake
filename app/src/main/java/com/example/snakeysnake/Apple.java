package com.example.snakeysnake;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;

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

    public String generateType() {
        Random random = new Random();
        String type = "";
        int randomInt = random.nextInt(5) + 1;
        switch(randomInt) {
            case 1:
                type = "red";
                break;
            case 2:
                type =  "blue";
            break;
            case 3:
                type = "gold";
            break;
            case 4:
                type = "green";
            break;
            case 5:
                type = "purple";
            break;
        }
        return type;
    }

    public void spawn(String type) {
        switch (type) {
            case "green":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.greenapple);
                break;
            case "blue":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.blueapple);
                break;
            case "purple":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.purpleapple);
                break;
            case "gold":
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldapple);
                break;
            default:
                mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.redapple);
                break;
        }
        setType(type);
        defaultSpawn();
    }

    @Override
    public void spawn() {
        // Do nothing
    }
}

