package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.ArrayList;

public class Snake implements Drawable, GameObjects {
    private static Snake instance;

    private final ArrayList<Point> segmentLocations = new ArrayList<>();
    private final int segmentSize;
    private final Point moveRange;
    private float speed;

    @Override
    public Point getLocation() {
        return null;
    }

    @Override
    public void spawn() {

    }

    private enum Heading {
        UP, RIGHT, DOWN, LEFT
    }

    private Heading heading = Heading.RIGHT;
    private final Bitmap bitmapHeadRight;
    private final Bitmap bitmapHeadLeft;
    private final Bitmap bitmapHeadUp;
    private final Bitmap bitmapHeadDown;
    private final Bitmap bitmapBody;

    private Snake(Context context, Point moveRange, int segmentSize) {
        this.speed = 1;
        this.segmentSize = segmentSize;
        this.moveRange = moveRange;
        this.bitmapHeadRight = loadAndRotateBitmap(context, R.drawable.head, 0);
        this.bitmapHeadLeft = loadAndRotateBitmap(context, R.drawable.head, 180);
        this.bitmapHeadUp = loadAndRotateBitmap(context, R.drawable.head, 270);
        this.bitmapHeadDown = loadAndRotateBitmap(context, R.drawable.head, 90);
        this.bitmapBody = loadBitmap(context, R.drawable.body, segmentSize);
    }

    public static Snake getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Snake not initialized");
        }
        return instance;
    }

    public static void init(Context context, Point moveRange, int segmentSize) {
        if (instance == null) {
            instance = new Snake(context, moveRange, segmentSize);
        }
    }

    private Bitmap loadBitmap(Context context, int resId, int size) {
        Bitmap original = BitmapFactory.decodeResource(context.getResources(), resId);
        return Bitmap.createScaledBitmap(original, size, size, false);
    }

    private Bitmap loadAndRotateBitmap(Context context, int resId, float degrees) {
        Bitmap original = loadBitmap(context, resId, segmentSize);
        if (degrees == 0) return original;

        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
    }

    public void spawn(int width, int height) {
        heading = Heading.RIGHT;
        segmentLocations.clear();
        segmentLocations.add(new Point(width / 2, height / 2));
    }

    public void cutSnake() {
        if (segmentLocations.size() > 1) {
            segmentLocations.subList(segmentLocations.size() / 2, segmentLocations.size()).clear();
        }
    }

    public void move() {
        if (segmentLocations.isEmpty()) return; // Exit if there are no segments

        // Move the body
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            segmentLocations.get(i).set(segmentLocations.get(i - 1).x, segmentLocations.get(i - 1).y);
        }

        // Move the head
        Point head = segmentLocations.get(0);
        switch (heading) {
            case UP:    head.y -= 1; break;
            case RIGHT: head.x += 1; break;
            case DOWN:  head.y += 1; break;
            case LEFT:  head.x -= 1; break;
        }

        // Wrap the snake head to the other side of the screen if out of bounds
        wrap(head);
    }

    private void wrap(Point point) {
        point.x = (point.x < 0) ? moveRange.x - 1 : (point.x >= moveRange.x) ? 0 : point.x;
        point.y = (point.y < 0) ? moveRange.y - 1 : (point.y >= moveRange.y) ? 0 : point.y;
    }

    public void setSpeed(float speed) {
       this.speed = speed;
    }

    public boolean detectDeath() {
        Point head = segmentLocations.get(0);
        for (int i = 1; i < segmentLocations.size(); i++) {
            if (head.equals(segmentLocations.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDinner(Point foodLocation) {
        if (segmentLocations.get(0).equals(foodLocation)) {
            segmentLocations.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (segmentLocations.isEmpty()) return;

        Bitmap headBitmap = bitmapHeadRight; // Default to right
        switch (heading) {
            case RIGHT:
                break;
            case LEFT:  headBitmap = bitmapHeadLeft;  break;
            case UP:    headBitmap = bitmapHeadUp;    break;
            case DOWN:  headBitmap = bitmapHeadDown;  break;
        }
        Point head = segmentLocations.get(0);
        canvas.drawBitmap(headBitmap, head.x * segmentSize, head.y * segmentSize, paint);

        for (int i = 1; i < segmentLocations.size(); i++) {
            Point segment = segmentLocations.get(i);
            canvas.drawBitmap(bitmapBody, segment.x * segmentSize, segment.y * segmentSize, paint);
        }
    }

    public void switchHeading(String direction) {
        Heading newHeading = Heading.valueOf(direction);
        if (newHeading == null) return;

        // Prevent reversing direction
        if (Math.abs(newHeading.ordinal() - heading.ordinal()) != 2) {
            heading = newHeading;
        }
    }
}