package main;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class Powerup {
    int id, type, time, x,y;
    double value;
    long destroyTime;
    long activeTime;
    Random rand = new Random();

    public final void initialize() //Template method
    {
        BoostExistenceTime();
        Announce();
    }
    void BoostExistenceTime() {
        destroyTime = System.currentTimeMillis() + time * 1000;
    }
    void Announce(){ System.out.println("Boost initialized !"); }

    void BoostActiveTime() {
        activeTime = System.currentTimeMillis() + time * 1000;
    };
    abstract void render(Graphics g, DrawableBoost boost);
    abstract Rectangle getBounds(DrawableBoost boost);
}
