package main;

import java.awt.*;
import java.util.ArrayList;

public interface IMap {
    void render(Graphics g);
    ArrayList<Obstacle> getObstacles();
}
