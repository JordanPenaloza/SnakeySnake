package com.example.snakeysnake;
import android.content.Context;
import android.graphics.Point;
import java.util.ArrayList;
public class AppleList {
    private ArrayList<Apple> apples;
    private Context context;
    private Point spawnRange;
    private int size;
    public AppleList(Context context, Point sr, int s) {
        apples = new ArrayList<>();
        this.context = context;
        this.spawnRange = sr;
        this.size = s;

    }
    public void addApple(String type) {
        Apple apple = new Apple(context, spawnRange, size);
        apple.spawn(type);
        apples.add(apple);
    }
    public void removeApple(Apple apple) {
        apples.remove(apple);
    }

    public ArrayList<Apple> getApples() {
        return apples;
    }

    public int getSize() {
        return apples.size();
    }

    public boolean isEmpty() {
        return apples.isEmpty();
    }

    public void clear() {
        apples.clear();
    }
}
