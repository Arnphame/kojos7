package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HPBoost extends Powerup {

    public HPBoost(int id, int type, double value, int x, int y, int time) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.x = x;
        this.y = y;
        this.time = time;
    }

    @Override
    void Announce(){
        System.out.println("Health boost initialized. Value = " + this.value);
    }

    @Override
    void BoostActiveTime() {
        activeTime = 0;
    }

    @Override
    void render(Graphics g, DrawableBoost boost) {
        Graphics2D g2 = (Graphics2D) g;

        boost.draw(g2, x, y);
    }

    @Override
    Rectangle getBounds(DrawableBoost boost)
    {
        return new Rectangle(x,y, boost.img.getWidth(), boost.img.getHeight());
    }

    public int getId(){
        return this.id;
    }

    public int getType(){
        return this.type;
    }

    public double getValue(){
        return this.value;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getTime(){
        return this.time;
    }
}
