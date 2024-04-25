package com.example.snakeysnake;
import java.util.Random;
public class RNG {
    Random random = new Random();
    private int randomInt;
    public RNG() {
        this.randomInt = 0;
    }
    public int getRandom() {
        randomInt = random.nextInt(5) + 1;
        return randomInt;
    }
}
