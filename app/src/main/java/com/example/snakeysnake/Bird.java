package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

public class Bird {
    private final Point position;
    private final int size;
    private final int screenWidth;
    private final int screenHeight;
    private boolean isActive;
    private final Point spawnRange;
    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_DOWN = 2;
    private static final int DIRECTION_LEFT = 3;
    private static final Random random = new Random(); // Reuse random object
    private int currentDirection = DIRECTION_RIGHT;

    public Bird(int screenWidth, int screenHeight, int blockSize, Point sr) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spawnRange = sr;
        this.size = blockSize;
        position = new Point(-1, -1); // Start off-screen
        isActive = false;
    }

    public void spawn(int y) {
        position.x = random.nextInt(spawnRange.x)+1;
        position.y = random.nextInt(spawnRange.y - 1) + 1;
        isActive = true;
    }

    public void move() {
        if (!isActive) return;

        switch (currentDirection) {
            case DIRECTION_UP: position.y -= size; break;
            case DIRECTION_RIGHT: position.x += size; break;
            case DIRECTION_DOWN: position.y += size; break;
            case DIRECTION_LEFT: position.x -= size; break;
        }

        // Randomly change direction with a 20% chance
        if (random.nextDouble() < 0.2) {
            currentDirection = random.nextInt(4);
        }

        // Deactivate the bird if it moves off-screen
        if (position.x < 0 || position.x >= screenWidth || position.y < 0 || position.y >= screenHeight) {
            isActive = false;
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        if (!isActive) return;

        drawBody(canvas, paint);
        drawEye(canvas, paint);
        drawBeak(canvas, paint);
    }

    private void drawBody(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLUE);
        canvas.drawCircle(position.x +  size / 2, position.y + size / 2, size / 2, paint);
    }

    private void drawEye(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        canvas.drawCircle(position.x + 3 * size / 4, position.y + size / 2, size / 4, paint);
    }

    private void drawBeak(Canvas canvas, Paint paint) {
        paint.setColor(Color.YELLOW);
        float[] beakPoints = {
                position.x + size, position.y + size / 2,
                position.x + size * 1.5f, position.y + size / 3,
                position.x + size * 1.5f, position.y + 2 * size / 3
        };
        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 6, beakPoints, 0, null, 0, null, 0, null, 0, 0, paint);
    }

    public Point getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }
}