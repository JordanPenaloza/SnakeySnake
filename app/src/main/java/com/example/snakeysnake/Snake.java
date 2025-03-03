package com.example.snakeysnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class Snake implements Drawable, GameObjects {
    private static Snake instance;

    private ArrayList<Point> segmentLocations;
    private int mSegmentSize;
    private Point mMoveRange;
    private float speed;
    private enum Heading {
        UP, RIGHT, DOWN, LEFT
    }

    private Heading heading = Heading.RIGHT;
    private Bitmap mBitmapHeadRight;
    private Bitmap mBitmapHeadLeft;
    private Bitmap mBitmapHeadUp;
    private Bitmap mBitmapHeadDown;
    private Bitmap mBitmapBody;




   private Snake(Context context, Point mr, int ss) {
        segmentLocations = new ArrayList<>();
        mSegmentSize = ss;
        mMoveRange = mr;
        mBitmapHeadRight = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadLeft = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadUp = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadDown = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.head);

        mBitmapHeadRight = Bitmap
                .createScaledBitmap(mBitmapHeadRight,
                        ss, ss, false);

        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        mBitmapHeadLeft = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        matrix.preRotate(-90);
        mBitmapHeadUp = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        matrix.preRotate(180);
        mBitmapHeadDown = Bitmap
                .createBitmap(mBitmapHeadRight,
                        0, 0, ss, ss, matrix, true);

        mBitmapBody = BitmapFactory
                .decodeResource(context.getResources(),
                        R.drawable.body);

        mBitmapBody = Bitmap
                .createScaledBitmap(mBitmapBody,
                        ss, ss, false);
        this.speed = 1;


    }
    public static Snake getInstance() {
       if (instance == null) {
           throw new IllegalStateException("Snake not initialized");
       }
       return instance;
    }
    public static void init(Context context, Point mr, int ss) {
       if (instance == null) {
           instance = new Snake(context, mr, ss);
       }
    }
    public void spawn() {
        System.out.println("This should never print because it's being overridden :D");
    }
    public void spawn(int w, int h) {
        heading = Heading.RIGHT;
        segmentLocations.clear();
        segmentLocations.add(new Point(w / 2, h / 2));
    }
    public void setSpeed(float speed) {
       this.speed = speed;
    }
    public float getSpeed() {
       return speed;
    }
    void cutSnake() {
       int halfSize = segmentLocations.size() / 2;
        if (segmentLocations.size() > halfSize) {
            segmentLocations.subList(halfSize, segmentLocations.size()).clear();
        }

    }
    void move() {

        if (segmentLocations.isEmpty()) {
            Log.d("Snake", "No segments to move.");
            return; // Exit if there are no segments
        }


        // Move the body
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            segmentLocations.get(i).x = segmentLocations.get(i - 1).x;
            segmentLocations.get(i).y = segmentLocations.get(i - 1).y;
        }

        // Move the head
        Point p = segmentLocations.get(0);
        switch (heading) {
            case UP:
                p.y-= speed;
                break;

            case RIGHT:
                p.x+= speed;
                break;

            case DOWN:
                p.y+= speed;
                break;

            case LEFT:
                p.x-= speed;
                break;
        }

        // Wrap the snake head to the other side of the screen if out of bounds
        if (p.x >= mMoveRange.x) {
            p.x = 0;
        } else if (p.x < 0) {
            p.x = mMoveRange.x - 1;
        }

        if (p.y >= mMoveRange.y) {
            p.y = 0;
        } else if (p.y < 0) {
            p.y = mMoveRange.y - 1;
        }
    }
    boolean detectDeath() {

        boolean dead = false;
        if (segmentLocations.get(0).x == -1 ||
                segmentLocations.get(0).x > mMoveRange.x ||
                segmentLocations.get(0).y == -1 ||
                segmentLocations.get(0).y > mMoveRange.y) {

            dead = true;
        }
        for (int i = segmentLocations.size() - 1; i > 0; i--) {
            if (segmentLocations.get(0).x == segmentLocations.get(i).x &&
                    segmentLocations.get(0).y == segmentLocations.get(i).y) {

                dead = true;
            }
        }
        return dead;
    }
    public boolean checkDinner(Point l) {
        if (segmentLocations.get(0).x == l.x &&
                segmentLocations.get(0).y == l.y) {
            segmentLocations.add(new Point(-10, -10));
            return true;
        }
        return false;
    }

    public Point getLocation() {
        return segmentLocations.get(0);
    }

    public void draw(Canvas canvas, Paint paint) {

        if (!segmentLocations.isEmpty()) {
            switch (heading) {
                case RIGHT:
                    canvas.drawBitmap(mBitmapHeadRight,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;
                case LEFT:
                    canvas.drawBitmap(mBitmapHeadLeft,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;

                case UP:
                    canvas.drawBitmap(mBitmapHeadUp,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;
                case DOWN:
                    canvas.drawBitmap(mBitmapHeadDown,
                            segmentLocations.get(0).x
                                    * mSegmentSize,
                            segmentLocations.get(0).y
                                    * mSegmentSize, paint);
                    break;
            }
            for (int i = 1; i < segmentLocations.size(); i++) {
                canvas.drawBitmap(mBitmapBody,
                        segmentLocations.get(i).x
                                * mSegmentSize,
                        segmentLocations.get(i).y
                                * mSegmentSize, paint);
            }
        }
    }


    void switchHeading(String direction) {

        switch (direction) {
            case "UP":
                if (heading != Heading.DOWN){
                    heading = Heading.UP;
                }
                break;
            case "RIGHT":
                if (heading != Heading.LEFT) {
                    heading = Heading.RIGHT;
                }
                break;
            case "DOWN":
                if (heading != Heading.UP) {
                    heading = Heading.DOWN;
                }
                break;
            case "LEFT":
                if (heading != Heading.RIGHT) {
                    heading = Heading.LEFT;
                }
                break;
            }


    }
}
