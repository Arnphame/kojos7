package main;

import java.awt.*;

public class Obstacle {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;
    private Movement movement;
    private int dir = 1;

    public Obstacle(int x, int y,int width, int height, Color color, Movement movementStrategy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.movement = movementStrategy;
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
        Vector newPos = movement.move(new Vector(this.x, this.y));
        this.x = (int)newPos.x;
        this.y = (int)newPos.y;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
