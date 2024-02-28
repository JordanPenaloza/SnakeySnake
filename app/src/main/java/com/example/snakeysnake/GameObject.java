package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Point;

import java.util.Random;

public class GameObject {

    protected Point location;
    protected Point mSpawnRange;
    protected int mSize;



    public GameObject() {
        location = new Point();
    }
    public Point getLocation() {
        return location;
    }
    public void setLocation(int x, int y) {
        location.x = x;
        location.y = y;
    }
    public void spawn() {
        Random random = new Random();
        location.x = random.nextInt(mSpawnRange.x) + 1;
        location.y = random.nextInt(mSpawnRange.y - 1) + 1;
        setLocation(location.x, location.y);
    }

}
