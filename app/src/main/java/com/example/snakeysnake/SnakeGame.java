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
    private static final int TARGET_FPS = 30; // Example: Set the game to run at 30 frames per second
    private static final long MILLIS_PER_SECOND = 1000; // This is constant, so you can define it like this

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

    public SnakeGame(Context context, Point size) {
        super(context);
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

    // Call this method to start the game loop
    public void startGame() {
        mPlaying = true;
        gameStateManager.startGame();
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    // Call this method to pause the game loop
    public void pauseGame() {
        mPlaying = false;
        gameStateManager.pauseGame();
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            // Handle exception
        }
    }

    // Call this method to resume the game loop
    public void resumeGame() {
        mPlaying = true;
        gameStateManager.resumeGame();
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    public void newGame() {
        mSnake.spawn(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        mApple.spawn("red");
        mLebron.spawn();
        mScore = 0;
        mNextFrameTime = System.currentTimeMillis();
        pauseCount = 0;
        gameTimer = 0;
    }


    // Game loop
    // Update the run method to include parameters when calling draw
    @Override
    public void run() {
        while (mPlaying) {
            if (!mPaused && updateRequired()) {
                if (gameStateManager.isRunning()) {
                    update();
                }
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
        if (gameStateManager.isRunning()) { // Check if the game state is RUNNING
            gameTimer++;
            mSnake.move();
            mBird.move();

            // Check if the snake has eaten an apple
            if(mSnake.checkDinner(mApple.getLocation())){
                mApple.spawn(mScore % 2 == 0 ? "green" : "red");
                mScore++;
                mSP.play(mEat_ID, 1, 1, 0, 0, 1);

                // Special cases for green apples and spawning death apples
                if (mScore % 2 == 0) {
                    mDeathApple.spawn();
                    mBird.spawn(mSnake.getLocation().y);
                }
            }

            // Check if the snake has eaten a death apple
            if(mSnake.checkDinner(mDeathApple.getLocation())) {
                gameStateManager.pauseGame(); // Pause the game when a death apple is eaten
            }

            // Check if the snake has eaten Lebron
            if (mSnake.checkDinner(mLebron.getLocation())) {
                mLebron.spawn();
                mScore++;
                mSP.play(mLebronID, 1, 1, 0, 0, 1);
            }

            // Check if the bird interacts with the snake
            if (mBird.isActive() && mSnake.getLocation().equals(mBird.getPosition())){
                mScore--;
                mBird.spawn(mSnake.getLocation().y);
            }

            // Check if the snake has collided and the game should end
            if (mSnake.detectDeath()) {
                mSP.play(mCrashID, 1, 1, 0, 0, 1);
                gameStateManager.gameOver(); // Change the state to GAME_OVER
            }
        } else if (gameStateManager.isGameOver()) {
            // Handle game over logic if needed, such as resetting the game or showing game over screen
        }
    }


    // Draw all game pieces initially, regardless of pause state
    public void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 26, 180, 100)); // Background color

            // Always draw the game objects, regardless of the game state
            mSnake.draw(mCanvas, mPaint);
            mApple.draw(mCanvas, mPaint);
            mBird.draw(mCanvas, mPaint);
            mLebron.draw(mCanvas, mPaint);
            mDeathApple.draw(mCanvas, mPaint);
            dpad.draw(mCanvas, mPaint);

            // Draw UI elements based on the game state
            if (gameStateManager.isGameOver()) {
                // If the game is over, display the game over screen and menu
                mainMenu.setGameOver(true); // Inform the MainMenu that the game is over
                mainMenu.displayMenu(mCanvas); // Use the MainMenu to display the game over menu
            } else if (gameStateManager.isPaused()) {
                // Draw the pause menu
                mainMenu.displayMenu(mCanvas); // This will draw over the game objects, acting like a pause screen
            } else {
                // Game is running, draw the pause button
                mPauseButton.displayPauseButton(mCanvas);
                mUI.displayPoints(mCanvas, mScore); // Display the score when the game is running
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }


    // Touch event logic for pausing and resuming game
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Visual feedback for button press can be handled here
                // For now, we are not handling ACTION_DOWN for the pause button
                break;

            case MotionEvent.ACTION_UP:
                String menuItem = mainMenu.menuItemClicked(motionEvent);
                if (menuItem != null) {
                    handleMenuItemClick(menuItem);
                    return true;
                } else if (mPauseButton.pauseButtonClicked(motionEvent)) {
                    // Toggle the pause state when the user lifts their finger from the pause button
                    if (gameStateManager.isRunning()) {
                        gameStateManager.pauseGame();
                        pauseCount++; // Only increment this if you're using it for something specific
                    } else if (gameStateManager.isPaused() && pauseCount >= 1) {
                        gameStateManager.resumeGame();
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
        // Handle the menu item click, e.g., start a new game, resume, or quit
        if ("Resume".equals(menuItem)) {
            gameStateManager.resumeGame();
        } else if ("Quit".equals(menuItem)) {
            // Handle quit logic
        } else if ("New Game".equals(menuItem)) {
            gameStateManager.startGame();
            newGame();
        }
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