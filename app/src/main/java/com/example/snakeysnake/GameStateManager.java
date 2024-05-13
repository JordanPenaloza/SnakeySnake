package com.example.snakeysnake;

import android.util.Log;

public class GameStateManager {
    private enum State {
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private State currentState;

    public GameStateManager() {
        currentState = State.PAUSED;  // Start the game paused
        logStateChange("Initial state set to PAUSED");
    }

    public void startGame() {
        if (currentState == State.PAUSED || currentState == State.GAME_OVER) {
            currentState = State.RUNNING;
            logStateChange("Game started");
        }
    }

    public void pauseGame() {
        if (currentState == State.RUNNING) {
            currentState = State.PAUSED;
            logStateChange("Game paused");
        }
    }

    public void resumeGame() {
        if (currentState == State.PAUSED) {
            currentState = State.RUNNING;
            logStateChange("Game resumed");
        }
    }

    public void gameOver() {
        if (currentState == State.RUNNING) {
            currentState = State.GAME_OVER;
            logStateChange("Game over");
        }
    }

    private void logStateChange(String message) {
        Log.d("GameStateManager", message);
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

    public String getCurrentStateName() {
        return currentState.name();
    }
}