package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HPBoost extends Powerup {
    @Override
    void BoostExistenceTime() {
        destroyTime = System.currentTimeMillis() + 3000 + rand.nextInt(8000);
    }

    public HPBoost(int id, int type, double value, int x, int y, int time) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.x = x;
        this.y = y;
        this.time = time;
        setImage(Assets.hpboost);
    }

    void setImage(BufferedImage img) {
        this.img = img;
    }

    @Override
    void BoostActiveTime() {
        activeTime = 0;
    }

    @Override
    void render(Graphics g, Assets assets) {
        Graphics2D g2 = (Graphics2D) g;

        g.drawImage(img, x, y, null);
    }

    @Override
    Rectangle getBounds()
    {
        return new Rectangle(x,y, img.getWidth(), img.getHeight());

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
