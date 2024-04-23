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
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class SnakeGame extends SurfaceView implements Runnable, Drawable {
    private Thread mThread = null;
    private long mNextFrameTime;
    private volatile boolean mPlaying = false;
    private volatile boolean mPaused = true;
    private SoundPool mSP;
    private int mEat_ID = -1;
    private int mCrashID = -1;
    private int mLebronID = -1;
    private final int NUM_BLOCKS_WIDE = 40;
    private int mNumBlocksHigh;
    private int mScore;
    protected Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    protected Paint mPaint;
    private Snake mSnake;
    private Apple mApple;
    private Bird mBird;
    private UI mUI;
    private PauseButton mPauseButton;
    private int pauseCount;
    private int gameTimer;
    private Dpad dpad;
    private Lebron mLebron;

    public SnakeGame(Context context, Point size) {
        super(context);
        int blockSize = size.x / NUM_BLOCKS_WIDE;
        mNumBlocksHigh = size.y / blockSize;

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
        }
        mSurfaceHolder = getHolder();
        mPaint = new Paint();

        mApple = new Apple(context,
                new Point(NUM_BLOCKS_WIDE,
                        mNumBlocksHigh),
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
    public void newGame() {
        mSnake.spawn(NUM_BLOCKS_WIDE, mNumBlocksHigh);
        mApple.spawn();
        mLebron.spawn();
        mScore = 0;
        mNextFrameTime = System.currentTimeMillis();
        pauseCount = 0;
        gameTimer = 0;
    }
    @Override
    public void run() {

        while (mPlaying) {
            if (!mPaused && updateRequired()) {
                update();
            }
            draw(mCanvas, mPaint);
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
        gameTimer++;
        mSnake.move();
        mBird.move();
        if(mSnake.checkDinner(mApple.getLocation())){
            // Polymorphism Example: If score is odd, spawn reg apple, if even spawn green apple
            if((mScore % 2) == 0) {
                mApple.spawn("green");
                mBird.spawn(mSnake.getLocation().y);
            }
            else if ((mScore % 2) == 1) {
                mApple.spawn();
            }

            mScore = mScore + 1;
            mSP.play(mEat_ID, 1, 1, 0, 0, 1);

        }
        if (mBird.isActive() && mSnake.getLocation().equals(mBird.getPosition())){
            mScore--;
            mBird.spawn(mSnake.getLocation().y);
        }

        if (mSnake.detectDeath()) {
            mSP.play(mCrashID, 1, 1, 0, 0, 1);

            pauseCount = 0;
            mPaused =true;
        }

    }

    public void draw(Canvas canvas, Paint paint) {

        if (mSurfaceHolder.getSurface().isValid()) {

            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.argb(255, 26, 180, 100));
            mUI.displayPoints(mCanvas, mScore);
            mUI.displayNames(mCanvas);
            mPauseButton.displayPauseButton(mCanvas);
            mApple.draw(mCanvas, mPaint);
            mSnake.draw(mCanvas, mPaint);
            mBird.draw(mCanvas, mPaint);
            paint.setColor(Color.BLACK);
            dpad.draw(mCanvas, mPaint);
            mLebron.draw(mCanvas, mPaint);

            if(mPaused && pauseCount == 0){
                mUI.displayTapToPlayMessage(mCanvas);

            }
            else if(mPaused && pauseCount >= 1) {
                mUI.displayContinueMsg(mCanvas);
            }
            mSurfaceHolder.unlockCanvasAndPost(mCanvas);

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            // Initial Game Start on Pause

            if(dpad.dpadTouched(motionEvent)) {
                switch(dpad.dpadString) {
                    case "bottom":
                        mSnake.switchHeading("DOWN");
                        break;
                    case "top":
                        mSnake.switchHeading("UP");
                        break;
                    case "right":
                        mSnake.switchHeading("RIGHT");
                        break;
                    case "left":
                        mSnake.switchHeading("LEFT");
                        break;
                    default:
                        break;
                }
            }
            if (mPaused && pauseCount == 0) {
                mPaused = false;
                newGame();
                return true;
            }
            // Game has started, But pause button was clicked
            else if (!mPaused && mPauseButton.pauseButtonClicked(motionEvent)) {
                System.out.println(mPauseButton.pauseButtonClicked(motionEvent));
                pauseCount++;
                mPaused = true;
                return true;
            }
            // Game is paused but we want to resume
            else if (mPaused && pauseCount >= 1 && mPauseButton.pauseButtonClicked(motionEvent)) {
                mPaused = false;
                resume();
            }

        }
        return true;
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