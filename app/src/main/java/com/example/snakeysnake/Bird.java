package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import java.util.Random;
public class Bird {
    private Point position;
    private int size;
    private int screenWidth;
    private int screenHeight;
    private boolean isActive;
    private static final int DIRECTION_UP = 0;
    private static final int DIRECTION_RIGHT = 1;
    private static final int DIRECTION_DOWN = 2;
    private static final int DIRECTION_LEFT = 3;
    private int currentDirection = DIRECTION_RIGHT;
    private Point spawnRange;

    public Bird(int screenWidth, int screenHeight, int blockSize, Point sr) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.spawnRange = sr;
        this.size = blockSize;
        position = new Point(-1, -1); // Start off-screen
        isActive = false;
    }

    public void spawn(int y) {
        Random random = new Random();
        position.x = random.nextInt(spawnRange.x) + 1;
        position.y = random.nextInt(spawnRange.y - 1) + 1;
        isActive = true;
    }

    public void move() {
        if (isActive) {
            int randomDirection = (int)(Math.random() * 4);
            switch (currentDirection) {
                case DIRECTION_UP:
                    position.y -= 1;
                    break;
                case DIRECTION_RIGHT:
                    position.x += 1;
                    break;
                case DIRECTION_DOWN:
                    position.y += 1;
                    break;
                case DIRECTION_LEFT:
                    position.x -= 1;
                    break;
            }

            if(Math.random() < .4) {
                currentDirection = randomDirection;
            }

            if (position.x >= screenWidth || position.y >= screenHeight || position.x < 0 || position.y < 0) {
                isActive = false; // Deactivate when it moves off-screen
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        if (isActive) {
            // Body of the bird
            paint.setColor(Color.BLUE);
            canvas.drawCircle(position.x * size + size / 2, position.y * size + size / 2, size / 2, paint);

            // Eye of the bird
            paint.setColor(Color.WHITE);
            canvas.drawCircle(position.x * size + 3 * size / 4, position.y * size + size / 2, size / 4, paint);

            // Beak of the bird
            paint.setColor(Color.YELLOW);
            float[] beakPoints = {
                    position.x * size + size, position.y * size + size / 2,
                    position.x * size + size + size / 2, position.y * size + size / 3,
                    position.x * size + size + size / 2, position.y * size + 2 * size / 3
            };
            canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 6, beakPoints, 0, null, 0, null, 0, null, 0, 0, paint);
        }
    }

    public Point getPosition() {
        return position;
    }

    public boolean isActive() {
        return isActive;
    }
}

