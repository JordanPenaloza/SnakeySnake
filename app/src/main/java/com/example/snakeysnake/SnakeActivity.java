package com.example.snakeysnake;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.media.MediaPlayer;

import java.io.IOException;

public class SnakeActivity extends Activity {
    private SnakeGame mSnakeGame;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mSnakeGame = new SnakeGame(this, size);

        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("backyardbeach.ogg");
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the callback to the SurfaceHolder of SnakeGame's SurfaceView
        mSnakeGame.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSnakeGame.startThread();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                // Respond to surface changes if necessary.
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSnakeGame.stopThread();
            }
        });

        setContentView(mSnakeGame);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSnakeGame != null) {
            mSnakeGame.resume(); // This method should handle resuming the game's thread and state
        }
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSnakeGame != null) {
            mSnakeGame.pause(); // This method should handle pausing the game's thread and state
        }
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
