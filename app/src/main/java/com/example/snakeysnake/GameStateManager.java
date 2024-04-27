package com.example.snakeysnake;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;


public class GameStateManager {
    private enum State {
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private State currentState;

    public GameStateManager() {
        currentState = State.PAUSED;  // Start the game paused
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void startGame() {
        currentState = State.RUNNING;
    }

    public void pauseGame() {
        if (currentState == State.RUNNING) {
            currentState = State.PAUSED;
        }
    }

    public void resumeGame() {
        if (currentState == State.PAUSED) {
            currentState = State.RUNNING;
        }
    }

    public void gameOver() {
        currentState = State.GAME_OVER;
    }

    public boolean isRunning() {
        return currentState == State.RUNNING;
    }

    public boolean isPaused() {
        return currentState == State.PAUSED;
    }


    public boolean isGameOver() {
        return currentState == State.GAME_OVER;
    }
}