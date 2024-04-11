package com.example.snakeysnake;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;


public class GameStateManager {
    private enum State {
        MAIN_MENU,
        RUNNING,
        PAUSED,
        PAUSE_MENU
    }

    private State currentState;

    public GameStateManager() {
        currentState = State.MAIN_MENU;  // Start with the main menu
    }

    public void setState(State state) {
        this.currentState = state;
    }

    // These are query methods used by SnakeGame to check the state and decide what to draw
    public boolean isMainMenu() {
        return currentState == State.MAIN_MENU;
    }

    public boolean isRunning() {
        return currentState == State.RUNNING;
    }

    public boolean isPaused() {
        return currentState == State.PAUSED;
    }

    public boolean isPauseMenu() {
        return currentState == State.PAUSE_MENU;
    }
}
