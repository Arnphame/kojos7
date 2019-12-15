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

    @Override
    public void updateObstacles(String type, int id, int x, int y, int width, int height, String color){
        map.updateObstacles(type, id, x, y, width, height, color);
    }

    @Override
    public void setColor(Color color) {
        map.setColor(color);
    }

    @Override
    public void undoColor(){ map.undoColor(); }

    @Override
    public void createSnapshot(){
        map.createSnapshot();
    }
}
