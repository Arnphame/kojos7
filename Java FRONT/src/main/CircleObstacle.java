package main;


import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleObstacle implements Obstacle{
    private int id;
    private Point position;
    private int radius;
    private Color color;
    public Ellipse2D bounds;

    public CircleObstacle(int id, Point position, int radius, Color color) {
        this.id = id;
        this.position = position;
        this.radius = radius;
        this.color = color;
        this.bounds = new Ellipse2D.Float(position.x, position.y, radius*2, radius*2);
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(color);
        g.fillOval(position.x,position.y,radius*2,radius*2);

        g.setColor(Color.black);
        g.drawOval((int)bounds.getX(), (int)bounds.getY(), radius*2, radius*2);
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
        this.bounds = new Ellipse2D.Float(position.x, position.y, radius, radius);
    }
}
