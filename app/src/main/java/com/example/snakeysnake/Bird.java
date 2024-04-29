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
    private boolean isActive;

    public Bird(int screenWidth, int blockSize) {
        this.screenWidth = screenWidth;
        this.size = blockSize;
        position = new Point(-1, -1); // Start off-screen
        isActive = false;
    }

    public void spawn(int y) {
        if(y == 0){
            position = new Point(-1, -1);
        }else {
            Random random = new Random();
            position.x = 0; // Start from the left side
            position.y = random.nextInt(y); // Random y position within bounds
            isActive = true;
        }
    }

    public void move() {
        if (isActive) {
            position.x += 1; // Move to the right
            if (position.x * size > screenWidth) {
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

