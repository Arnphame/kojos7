package main;


import java.awt.*;

public class RectangleObstacle implements Obstacle {
    private int id;
    private Point position;
    private int width;
    private int height;
    private Color color;
    public Rectangle bounds;

    public RectangleObstacle(int id, Point position,int width, int height, Color color) {
        this.id = id;
        this.position = position;
        this.width = width;
        this.height = height;
        this.color = color;
        this.bounds = new Rectangle(position.x,position.y,width,height);
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(color);
        g.fillRect(position.x,position.y,width,height);

        g.setColor(Color.black);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    @Override
    public Shape getBounds() {
        return bounds;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setPosition(int x, int y) {
        this.position.x = x;
        this.position.y = y;
        updateBounds();
    }

    public void updateBounds(){
        bounds.x = position.x;
        bounds.y = position.y;
    }
}
