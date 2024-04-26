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
    public void addApple(Apple apple) {
        apples.add(apple);
    }
    public void removeApple(Apple apple) {
        apples.remove(apple);
    }

    public ArrayList<Apple> getApples() {
        return apples;
    }

    public boolean isEmpty() {
        return apples.isEmpty();
    }

    public void clear() {
        apples.clear();
    }
}
