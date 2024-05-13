package com.example.snakeysnake;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class DeathApple extends AbstractApple  {

    public DeathApple(Context context, Point sr, int s) {
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

    @Override
    public void spawn() {
        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.deathapple);
        setType("death");
        defaultSpawn();
    }
}

