package com.example.snakeysnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameOver {
    private SurfaceHolder surfaceHolder;
    private int score;
    private boolean isDead;
    private Rect restartButtonRect;
    private Paint paint;
    private int screenWidth;
    private int screenHeight;

    public GameOver(SurfaceHolder surfaceHolder, int score, boolean isDead, int screenWidth, int screenHeight) {
        this.surfaceHolder = surfaceHolder;
        this.score = score;
        this.isDead = isDead;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.paint = new Paint();

        // Define the rectangle for the restart button
        int buttonWidth = 300;
        int buttonHeight = 100;
        int buttonLeft = screenWidth / 2 - buttonWidth / 2;
        int buttonTop = screenHeight / 2 - buttonHeight / 2;
        restartButtonRect = new Rect(buttonLeft, buttonTop, buttonLeft + buttonWidth, buttonTop + buttonHeight);
    }

    public void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            try {
                // Draw the game over screen
                canvas.drawColor(Color.argb(255, 26, 128, 182)); // Background color
                paint.setColor(Color.WHITE);
                paint.setTextSize(50);
                canvas.drawText("Game Over!", screenWidth / 2, screenHeight / 4, paint);
                canvas.drawText("Score: " + score, screenWidth / 2, screenHeight / 3, paint);

                // Draw the restart button
                paint.setColor(Color.BLACK);
                canvas.drawRect(restartButtonRect, paint);
                paint.setColor(Color.WHITE);
                canvas.drawText("Restart", restartButtonRect.left + 70, restartButtonRect.top + 60, paint);
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    // Check if the restart button is touched
    public boolean touchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (restartButtonRect.contains(x, y)) {
                // The restart button was touched
                return true;
            }
        }
        return false;
    }
}
