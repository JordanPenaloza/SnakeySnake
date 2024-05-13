//package com.example.snakeysnake;
//
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.graphics.Point;
//
//public class DeathApple extends AbstractApple  {
//
//    public DeathApple(Context context, Point sr, int s) {
//        super(context, sr, s);
//    }
//
//    @Override
//    protected void setType(String type) {
//        this.type = type;
//    }
//
//    @Override
//    protected String getType() {
//        if(this.type == null) {
//            return "NULL";
//        }
//        return this.type;
//    }
//
//    @Override
//    public void spawn() {
//        mBitmapApple = BitmapFactory.decodeResource(context.getResources(), R.drawable.deathapple);
//        setType("death");
//        defaultSpawn();
//    }
//}
//

package com.example.snakeysnake;

import android.content.Context;
////import android.graphics.BitmapFactory;
import android.graphics.Point;

public class DeathApple extends AbstractApple {

    private static final String TYPE = "death";

    public DeathApple(Context context, Point spawnRange, int size) {
        super(context, spawnRange, size, R.drawable.deathapple); // Initialize with death apple image
    }

    @Override
    protected void setType(String type) {
        // Since the type is constant, this method can be effectively empty or log a warning if used improperly.
    }

    @Override
    protected String getType() {
        return TYPE;
    }

    @Override
    public void spawn() {
        resetLocation(); // Ensure apple appears at a new random location within the allowed range
    }

}
