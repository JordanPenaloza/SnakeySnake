package com.example.snakeysnake;

import android.util.Log;

public class GameStateManager {
    private enum State {
        INITIAL,
        RUNNING,
        PAUSED,
        GAME_OVER
    }

    private State currentState;

    public GameStateManager() {
        currentState = State.INITIAL;  // Start the game in the initial state
    }

    public void startGame() {
        if (currentState == State.INITIAL || currentState == State.PAUSED || currentState == State.GAME_OVER) {
            currentState = State.RUNNING;
            Log.d("GameStateManager", "Game is now running.");
        }
    }

    public void pauseGame() {
        if (currentState == State.RUNNING) {
            currentState = State.PAUSED;
            Log.d("GameStateManager", "Game is now paused.");
        }
    }

    public void resumeGame() {
        if (currentState == State.PAUSED) {
            currentState = State.RUNNING;
            Log.d("GameStateManager", "Game is running.");
        }
    }

    public void gameOver() {
        currentState = State.GAME_OVER;
        Log.d("GameStateManager", "Game is now over.");
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

    public boolean isInitial() {
        return currentState == State.INITIAL;
    }

    public String getCurrentStateName() {
        return currentState.name();
    }
}
