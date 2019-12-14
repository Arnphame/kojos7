package main;

import java.awt.*;

public class Head implements IBodyPart{
    int x,y,r;
    Color color;

    public Head(int x,int y,int r,Color color){
        this.x=x;
        this.y=y;
        this.r=r;
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
    public void render(Graphics g){
        g.setColor(color);
        g.fillOval(x-r, y-r, 2*r, 2*r);
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
        return new Rectangle(x-r, y-r, 2*r, 2*r);
    }

}
