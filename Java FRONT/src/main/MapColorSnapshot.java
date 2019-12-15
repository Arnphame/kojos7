package main;

import java.awt.*;

public class MapColorSnapshot {
    private IMap map;
    private Color mapColor;

    public MapColorSnapshot(IMap map, Color color){
        this.map = map;
        this.mapColor = color;
    }

    public void restore(){
        map.setColor(mapColor);
    }
}
