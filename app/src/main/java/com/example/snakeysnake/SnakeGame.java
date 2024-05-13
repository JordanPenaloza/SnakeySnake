package com.example.snakeysnake;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.ArrayList;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

@SuppressLint("ViewConstructor")
public class SnakeGame extends SurfaceView implements Runnable {
    private Thread mThread = null;
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;
    private int mLebronID = -1;
    private final int NUM_BLOCKS_WIDE = 40;
    private final int mNumBlocksHigh;
    private int mScore;
    protected Canvas mCanvas;
    private final SurfaceHolder mSurfaceHolder;
    private final Paint mPaint;
    private ArrayList<Apple> mApples;
    private final Context context;
    private volatile boolean mPlaying = false;
    private long mNextFrameTime;
    private final AssetManager assetManager;

    //game components
    private Apple mApple;
    private DeathApple mDeathApple;
    private Snake mSnake;
    private Bird mBird;
    private UI mUI;
    private PauseButton mPauseButton;
    private int pauseCount;
    //private int gameTimer;
    private Dpad dpad;
    private Lebron mLebron;
    private GameStateManager gameStateManager;
    private MainMenu mainMenu;


    public SnakeGame(Context context, Point size) {
        super(context);
        this.context = context;
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        mNumBlocksHigh = size.y / blockSize;
        gameStateManager = new GameStateManager();
        gameStateManager.pauseGame();
        assetManager = context.getAssets();
        mainMenu = new MainMenu(assetManager, size.x,size.y);

        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        setupComponents(blockSize, size);
        setupSoundPool();
    }

    private void setupComponents(int blockSize, Point size) {
        Snake.init(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        mSnake = Snake.getInstance();
        mApple = new Apple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        mDeathApple = new DeathApple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
        mBird = new Bird(size.x, size.y, blockSize, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh) );
        mUI = new UI(context.getAssets());
        mPauseButton = new PauseButton(context.getAssets());// blockSize);
        dpad = new Dpad(context.getAssets());
        mLebron = new Lebron(context, size, blockSize);
        gameStateManager = new GameStateManager();
        gameStateManager.pauseGame();
        mainMenu = new MainMenu(assetManager, size.x,size.y);
        mApples = new ArrayList<>();
    }

    private void setupSoundPool() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        mSP = new SoundPool.Builder()
                .setMaxStreams(5)
                .setAudioAttributes(audioAttributes)
                .build();

        mEat_ID = loadSound("get_apple.ogg");
        mCrashID = loadSound("snake_death.ogg");
        mLebronID = loadSound("lebron.ogg");
    }

    private int loadSound(String filename) {
        try {
            return mSP.load(assetManager.openFd(filename), 1);
        } catch (IOException e) {
            Log.e("Load Sound", "Could not load file: " + filename, e);
            return -1;
        }
    }

    // Game loop
    @Override
    public void run() {
        while (mPlaying) {
            if (updateRequired() && gameStateManager.isRunning()) {
                update();
            }
            draw();
        }
    }

    public boolean updateRequired() {
        final long TARGET_FPS = 50;//10;
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
        if (!mSurfaceHolder.getSurface().isValid())return;
        try {
            mCanvas = mSurfaceHolder.lockCanvas();

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beach);
            mCanvas.drawBitmap(bitmap, 0, 0, null);


            mSnake.draw(mCanvas, mPaint);
            for (Apple apple : mApples) {
                apple.draw(mCanvas, mPaint);
            }
            mBird.draw(mCanvas, mPaint);
            mLebron.draw(mCanvas, mPaint);
            mDeathApple.draw(mCanvas, mPaint);

            mUI.displayPoints(mCanvas, mScore);

            if (gameStateManager.isRunning() && !gameStateManager.isPaused()) {
                mPauseButton.displayPauseButton(mCanvas);
                dpad.draw(mCanvas, mPaint);
            }

            // Check if the game is over and display the game over menu
            if (gameStateManager.isGameOver()) {
                mainMenu.setGameOver(true);
                mainMenu.displayMenu(mCanvas);
                mUI.displayGameOver(mCanvas);  // Display the "GAME OVER" message
            } else if (gameStateManager.isPaused() || gameStateManager.isInitial()) {
                mainMenu.setGameOver(gameStateManager.isGameOver() || gameStateManager.isInitial());
                mainMenu.displayMenu(mCanvas);
            }
        }finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void drawGameObjects(Canvas canvas) {
        mSnake.draw(canvas, mPaint);
        for (Apple apple : mApples) {
            apple.draw(canvas, mPaint);
        }
        mBird.draw(canvas, mPaint);
        mLebron.draw(canvas, mPaint);
        mDeathApple.draw(canvas, mPaint);
    }



    // Touch event logic for pausing and resuming game

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
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

                if (gameStateManager.isGameOver() || gameStateManager.isPaused() || gameStateManager.isInitial()) {
                    if (mainMenu.isTouchOnMenu(x, y)) {

                        String menuItem = mainMenu.menuItemClicked(motionEvent);
                        if (menuItem != null) {
                            handleMenuItemClick(menuItem);
                            return true;
                        }

                    }
                    // Ensure no state change if clicked outside the menu during INITIAL state
                    if (gameStateManager.isInitial() && !mainMenu.isTouchOnMenu(x, y)) {
                        return true;
                    }
                    return true;

                }

                if (mPauseButton.pauseButtonClicked(motionEvent)) {
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

        if (gameStateManager.isPaused() && pauseCount == 0) {
            if (gameStateManager.isInitial() && !mainMenu.isTouchOnMenu(x, y)) {
                return true;
            }
            gameStateManager.startGame();
            newGame();
            return true;
        }
        return true;
    }






    private void handleMenuItemClick(String menuItem) {
        switch (menuItem) {
            case "Resume":
                gameStateManager.resumeGame();
                break;
            case "Quit":
                Log.d("Game State", "Game state after quitting: " + gameStateManager.getCurrentStateName());
                ((SnakeActivity) getContext()).finish();
//                gameStateManager.gameOver();
                break;
            case "New Game":
                newGame();
                gameStateManager.startGame();
                break;
        }

    }

    // Handle snake eating any object
    private void handleSnakeEating() {
        //Point snakeHead = mSnake.getLocation();
        for (int i = 0; i < mApples.size(); i++) {
            if (mSnake.checkDinner(mApples.get(i).getLocation())) {
                handleEatingApple(i);
                break; // Exit the loop after eating an apple
            }
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
    private void handleEatingApple(int eatenApple) {
        //String appleColor = mApples.get(eatenApple).getType();
        if ("green".equals(mApples.get(eatenApple).getType())) {
            mBird.spawn(mSnake.getLocation().y);
            mDeathApple.spawn();
        }
        // If the apple that was eaten is a golden apple, give the player 10 points
        if ("gold".equals(mApples.get(eatenApple).getType())) {
            mScore += 10;
        }
        if ("red".equals(mApples.get(eatenApple).getType())) {
            mScore += 1;
        }
        if ("blue".equals(mApples.get(eatenApple).getType())) {
            mScore += 1;
            mSnake.cutSnake();

        }
        if ("purple".equals(mApples.get(eatenApple).getType())) {
            mScore -= 20;
        }
        String newAppleColor = mApple.generateType();
        mApples.get(eatenApple).spawn(newAppleColor);
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
        gameStateManager.gameOver();  // Set the game state to 'GameOver'
        mainMenu.setGameOver(true);   // Update the MainMenu to show the game over menu
        Log.d("Game State", "Game state after death: " + gameStateManager.getCurrentStateName());
    }


    private void handleBirdCollision() {
        playSound(mCrashID);
        gameStateManager.gameOver();
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

    public void createApples(int numApples, int blockSize) {
        mApples.clear();
        for (int i = 0; i < numApples; i++) {
            Apple apple = new Apple(context, new Point(NUM_BLOCKS_WIDE, mNumBlocksHigh), blockSize);
            apple.spawn(mApple.generateType()); // Specify the color of the apple
            mApples.add(apple);
        }
    }

    public void newGame() {
        stopThread();
        mSnake.spawn(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        createApples(10, mApple.getSize());
        mLebron.spawn();
        mScore = 0;
        mNextFrameTime = System.currentTimeMillis();
        pauseCount = 0;

        gameTimer = 0;
        if (gameStateManager.isInitial()) {
            gameStateManager.startGame();
        } else {
            gameStateManager = new GameStateManager();  // Reset to initial state
        }

        mSnake.setSpeed(1);
        startThread();
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