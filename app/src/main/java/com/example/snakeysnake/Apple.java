package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;

public class Apple extends AbstractApple {

    private static final Random random = new Random();

    public Apple(Context context, Point spawnRange, int size) {
        super(context, spawnRange, size, R.drawable.redapple); // Default to red apple initially
    }

    @Override
    protected void setType(String type) {
        this.type = type;
        updateBitmapForType(type);
    }

    @Override
    protected String getType() {
        return type == null ? "NULL" : type;
    }

    private void updateBitmapForType(String type) {
        int resourceId = getResourceIdForType(type);
        if (resourceId != 0) {
            Bitmap newBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            bitmapApple = Bitmap.createScaledBitmap(newBitmap, size, size, false);
        }
    }

    private int getResourceIdForType(String type) {
        switch (type) {
            case "green":
                return R.drawable.greenapple;
            case "blue":
                return R.drawable.blueapple;
            case "purple":
                return R.drawable.purpleapple;
            case "gold":
                return R.drawable.goldapple;
            default:
                return R.drawable.redapple;
        }
    }

    public String generateType() {
        String[] types = {"red", "blue", "gold", "green", "purple"};
        return types[random.nextInt(types.length)];
    }

    public void spawn(String type) {
        setType(type);
        resetLocation();
    }

    @Override
    public void spawn() {
    }
}