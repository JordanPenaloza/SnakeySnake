package com.example.snakeysnake;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;

public class SnakeGame extends SurfaceView implements Runnable {
    private Thread mThread = null;
    private long mNextFrameTime;

    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;

    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;
    private int mLebronID = -1;

    private final int NUM_BLOCKS_WIDE = 40;
    private static final int TARGET_FPS = 30;
    private static final long MILLIS_PER_SECOND = 1000;

    private int mNumBlocksHigh;

    private int mScore;
    protected Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    protected Paint mPaint;
    private Snake mSnake;

    private Apple mApple;
    private DeathApple mDeathApple;
    private ArrayList<Apple> apples;

    private Bird mBird;
    private UI mUI;
    private PauseButton mPauseButton;
    private int pauseCount;
    private int gameTimer;
    private Dpad dpad;
    private Lebron mLebron;
    private GameStateManager gameStateManager;
    private MainMenu mainMenu;
    private Thread mGameThread = null;
    private Context context;

    public SnakeGame(Context context, Point size) {
        super(context);
        this.context = context;
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        mNumBlocksHigh = size.y / blockSize;
        gameStateManager = new GameStateManager(); // Initialize GameStateManager
        gameStateManager.pauseGame(); // Start game in paused state
        mainMenu = new MainMenu(mPaint); // Initialize the mainMenu

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSP = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("get_apple.ogg");
            mEat_ID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("snake_death.ogg");
            mCrashID = mSP.load(descriptor, 0);
            descriptor = assetManager.openFd("lebron.ogg");
            mLebronID = mSP.load(descriptor, 0);

        } catch (IOException e) {
            // Empty
        }

        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh),
                blockSize);

        mDeathApple = new DeathApple(context,
                new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh),
                blockSize);


        mSnake = new Snake(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
                blockSize);

        mBird = new Bird(size.x,blockSize);
        mUI = new UI(mPaint);
        mPauseButton = new PauseButton(mPaint);
        dpad = new Dpad(mPaint);
        mLebron = new Lebron(context, new Point(NUM_BLOCKS_WIDE,
                mNumBlocksHigh),
                blockSize);
    }


    // Game loop
    @Override
    public void run() {
        while (mPlaying) {
            // Instead of checking mPaused, we now check if gameStateManager.isRunning() is true
            if (updateRequired() && gameStateManager.isRunning()) {
                update();
            }
            draw(); // Pass the correct parameters
        }
    }



    public boolean updateRequired() {

        final long TARGET_FPS = 10;
        final long MILLIS_PER_SECOND = 1000;

        if(mNextFrameTime <= System.currentTimeMillis()){
            mNextFrameTime =System.currentTimeMillis()
                    + MILLIS_PER_SECOND / TARGET_FPS;

            return true;
        }
        return false;
    }

    public void update() {
        // Check if the game state is RUNNING before proceeding with the update logic
        if (!gameStateManager.isRunning()) {
            // If the game is not running, no need to update the game objects
            return;
        }

        gameTimer++;
        mSnake.move();
        mBird.move();
        handleSnakeEating();

        // Check for collision with the bird and handle accordingly
        if (mBird.isActive() && mSnake.getLocation().equals(mBird.getPosition())) {
            handleBirdCollision();
        }


        // Check if the snake has died, which could be from self-collision or hitting the death apple
        if (mSnake.detectDeath()) {
            handleSnakeDeath();
        }
    }




    // Draw all game pieces initially, regardless of pause state
    public void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 26, 180, 100)); // Set the background color

            // Draw game objects
            mSnake.draw(mCanvas, mPaint);
            mApple.draw(mCanvas, mPaint);
            mBird.draw(mCanvas, mPaint);
            mLebron.draw(mCanvas, mPaint);
            mDeathApple.draw(mCanvas, mPaint);
            dpad.draw(mCanvas, mPaint);

            // Check the game state and draw UI elements accordingly
            if (gameStateManager.isGameOver()) {
                // Display the game over message
                mUI.displayGameOver(mCanvas);

                // Set the game over state for the menu and display it
                mainMenu.setGameOver(true); // Inform the MainMenu that the game is over
                mainMenu.displayMenu(mCanvas); // Display the game over menu

            } else if (gameStateManager.isPaused()) {
                // If the game is paused, but not over, still show the menu
                mainMenu.setGameOver(false); // Make sure the game over state is not set in the menu
                mainMenu.displayMenu(mCanvas); // Display the pause menu
            } else {
                // If the game is running, display the score and other running state UI elements
                mPauseButton.displayPauseButton(mCanvas);
                mUI.displayPoints(mCanvas, mScore);
            }

            // Post the canvas to be drawn on the screen
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }




    // Touch event logic for pausing and resuming game
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Process D-pad input if the game is running
                if (gameStateManager.isRunning() && dpad.dpadTouched(motionEvent)) {
                    if (dpad.bottomClicked(motionEvent)) {
                        mSnake.switchHeading("DOWN");
                    } else if (dpad.topClicked(motionEvent)) {
                        mSnake.switchHeading("UP");
                    } else if (dpad.rightClicked(motionEvent)) {
                        mSnake.switchHeading("RIGHT");
                    } else if (dpad.leftClicked(motionEvent)) {
                        mSnake.switchHeading("LEFT");
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                String menuItem = mainMenu.menuItemClicked(motionEvent);
                if (menuItem != null) {
                    handleMenuItemClick(menuItem);
                    return true;
                } else if (mPauseButton.pauseButtonClicked(motionEvent)) {
                    if (gameStateManager.isRunning()) {
                        gameStateManager.pauseGame();
                        Log.d("Game State", "Game state after pausing: " + gameStateManager.getCurrentStateName());
                        pauseCount++;
                    } else if (gameStateManager.isPaused() && pauseCount >= 1) {
                        gameStateManager.resumeGame();
                        Log.d("Game State", "Game state after resuming: " + gameStateManager.getCurrentStateName());
                    }
                    return true;
                }
                break;
        }

        // Handle starting and pausing the game when initially paused
        if (gameStateManager.isPaused() && pauseCount == 0) {
            gameStateManager.startGame(); // This method will set the state to RUNNING
            newGame(); // Reset the game to a new state if needed
        } else if (gameStateManager.isPaused() && pauseCount >= 1) {
            gameStateManager.resumeGame(); // This method will set the state to RUNNING
            // If you have any specific logic for resuming from pause, add it here
        }

        return true;
    }

    private void handleMenuItemClick(String menuItem) {
        if ("Resume".equals(menuItem)) {
            gameStateManager.resumeGame();
            Log.d("Game State", "Game state after resuming: " + gameStateManager.getCurrentStateName());
        } else if ("Quit".equals(menuItem)) {
            // Assuming you have implemented a way to close the activity or exit the game
            Log.d("Game State", "Game state after quitting: " + gameStateManager.getCurrentStateName());
            // Add code to quit the game
            ((SnakeActivity) getContext()).finish();
        } else if ("New Game".equals(menuItem)) {
            // Reset the game to its initial state
            newGame(); // This method should reset the game objects and variables
            gameStateManager.startGame(); // This sets the game state to RUNNING
            Log.d("Game State", "Game state after starting a new game: " + gameStateManager.getCurrentStateName());
        }
    }

    // Handle snake eating any object
    private void handleSnakeEating() {
        Point snakeHead = mSnake.getLocation();

        // Handle eating normal apple
        if (mSnake.checkDinner(mApple.getLocation())) {
            handleEatingApple();
        }

        // Handle eating death apple
        if (mSnake.checkDinner(mDeathApple.getLocation())) {
            handleEatingDeathApple();
        }

        // Handle eating Lebron
        if (mSnake.checkDinner(mLebron.getLocation())) {
            handleEatingLebron();
        }
    }

    // Actions to take when eating an apple
    private void handleEatingApple() {
        String appleColor = mScore % 2 == 0 ? "green" : "red";
        mApple.spawn(appleColor);

        if ("green".equals(appleColor)) {
            mDeathApple.spawn();
            mBird.spawn(mSnake.getLocation().y);
        }

        mScore++;
        playSound(mEat_ID);
    }

    // Actions to take when eating Lebron
    private void handleEatingLebron() {
        mLebron.spawn();
        mScore++;
        playSound(mLebronID);
    }

    // Actions to take when eating a death apple
    private void handleEatingDeathApple() {
        Log.d("Collision", "Snake touched the skull - DeathApple");
        playSound(mCrashID);
        gameStateManager.gameOver();
        Log.d("Game State", "Game state after death by skull: " + gameStateManager.getCurrentStateName());
    }

    private void handleSnakeDeath() {
        Log.d("Collision", "Snake collided with obstacle");
        playSound(mCrashID);
        gameStateManager.gameOver();
        Log.d("Game State", "Game state after death: " + gameStateManager.getCurrentStateName());
    }

    private void handleBirdCollision() {
        mScore--; // Decrease the score
        mBird.spawn(mSnake.getLocation().y); // Respawn the bird
    }


    // Helper method to play sounds
    private void playSound(int soundID) {
        mSP.play(soundID, 1, 1, 0, 0, 1);
    }


    // Handle starting the thread
    public void startThread() {
        if (mThread == null || !mThread.isAlive()) {
            mThread = new Thread(this);
            mPlaying = true;
            mThread.start();
        }
    }

    // Handle stopping the thread
    public void stopThread() {
        mPlaying = false;
        try {
            if (mThread != null && mThread.isAlive()) {
                mThread.join();
            }
        } catch (InterruptedException e) {
            // Handle interruption
        }
    }

    public void newGame() {
        mSnake.spawn(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        mApple.spawn("red");
        mLebron.spawn();
        mScore = 0;
        mNextFrameTime = System.currentTimeMillis();
        pauseCount = 0;
        gameTimer = 0;
        gameStateManager.startGame();
    }

    public void pause() {
        mPlaying = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            // Left blank to do nothing
        }
    }

    public void resume() {
        mPlaying = true;
        mThread = new Thread(this);
        mThread.start();
    }


}