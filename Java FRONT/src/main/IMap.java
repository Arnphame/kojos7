package main;

import java.awt.*;
import java.util.ArrayList;

public interface IMap {
    void render(Graphics g);
    ArrayList<Obstacle> getObstacles();
    void updateObstacles(String type, int id, int x, int y, int width, int height, String color);
}
