package main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Torso implements IBodyPart{
    int x,y,w,h;
    float rot;
    Color color;

    public Torso(int x,int y,int w,int h, float rot, Color color){
        this.x=x;
        this.y=y;
        this.rot=rot;
        this.w=w;
        this.h=h;
        this.color = color;
    }

    @Override
    public void moveX(int dir) {
        this.x += dir;
    }

    @Override
    public void moveY(int dir) {
        this.y += dir;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void render(Graphics g1){
        Graphics2D g = (Graphics2D)g1;
        g.setColor(color);

        g.translate(x, y);
        g.rotate(-rot);

        g.fillRect(0, 0, w, h);

        g.rotate(rot);
        g.translate(-x,-y);
    }

    @Override
    public boolean intersects(Rectangle rect) {
        return getBounds().intersects(rect);
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public Rectangle getBounds(){
        AffineTransform tx = new AffineTransform();
        tx.translate(x, y);
        tx.rotate(-rot);
        Rectangle shape = new Rectangle(0, 0, w, h);
        Shape newShape = tx.createTransformedShape(shape);
        return newShape.getBounds();
    }
}
