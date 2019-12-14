package main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Limb implements IBodyPart{
    int x,y,w,h;
    float rot;
    int jointX,jointY;
    Color color;

    public Limb(int x,int y,int w,int h, float rot,int jointX , int jointY, Color color){
        this.x=x;
        this.y=y;
        this.rot=rot;
        this.w=w;
        this.h=h;
        this.jointX = jointX;
        this.jointY = jointY;
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


        g.translate(x + jointX, y + jointY);
        g.rotate(-rot);
        g.translate(-jointX, -jointY);

        g.setColor(color);
        g.fillRect(0, 0, w, h);

        g.translate(jointX,jointY);
        g.rotate(rot);
        g.translate(-x-jointX,-y-jointY);
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
        tx.translate(x + jointX, y + jointY);
        tx.rotate(-rot);
        Rectangle shape = new Rectangle(0, 0, w, h);
        Shape newShape = tx.createTransformedShape(shape);
        return newShape.getBounds();
    }
}
