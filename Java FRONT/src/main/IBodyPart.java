package main;

import java.awt.*;

public interface IBodyPart {
    void moveX(int dir);
    void moveY(int dir);
    int getX();
    int getY();
    void render(Graphics g);
    boolean intersects(Rectangle rect);
    void setColor(Color color);
}
