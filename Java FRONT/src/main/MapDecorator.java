package main;

import java.awt.*;
import java.util.ArrayList;

public abstract class MapDecorator implements IMap {
    private IMap map;

    public MapDecorator(IMap map) {
        this.map = map;
    }

    @Override
    public void render(Graphics g) {
        map.render(g);
    }

    @Override
    public ArrayList<Obstacle> getObstacles() {
        return map.getObstacles();
    }
}
