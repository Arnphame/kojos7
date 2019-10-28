package main;

import java.awt.*;

public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private int dir = 1;

    public Obstacle(int x, int y,int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void render(Graphics g)
    {
        g.setColor(color);
        g.fillRect(x,y,width,height);
    }
    public void tick(){
        move();
    }

    void move(){
        x+= dir;

        if(x == 500 || x == 50){
            dir*=-1;
        }
    }
}
