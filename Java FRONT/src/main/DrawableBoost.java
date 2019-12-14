package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawableBoost {
    BufferedImage img;
    public DrawableBoost(BufferedImage img){
        this.img = img;
    }

    public void draw(Graphics2D g, int x, int y){
        g.drawImage(this.img, x, y, null);
    }
}
