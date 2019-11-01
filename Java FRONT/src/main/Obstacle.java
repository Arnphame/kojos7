package main;

import java.awt.*;

public interface Obstacle {
    void render(Graphics g);
    Shape getBounds();
    int getId();
    void setPosition(int x, int y);
}
