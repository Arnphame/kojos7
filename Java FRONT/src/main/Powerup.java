package main;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Powerup {
    int id, type, time, x,y;
    double value;
    long destroyTime;
    long activeTime;
    BufferedImage img;
    Random rand = new Random();

    public final void templateMethod()
    {
        BoostExistenceTime();
    }
    void BoostExistenceTime() {
        destroyTime = System.currentTimeMillis() + 2000 + rand.nextInt(4000);
    }
    void BoostActiveTime() {
        activeTime = System.currentTimeMillis() + time*1000;
    };
    abstract void render(Graphics g, Assets assets);
     abstract Rectangle getBounds();
}
