package main;

import java.awt.*;
import java.util.ArrayList;

public class CloudDecorator extends MapDecorator {

    Assets assets = new Assets();
    public CloudDecorator(IMap map) {
        super(map);
        assets.init();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        renderClouds(g);
    }


    private void renderClouds(Graphics g) {
        g.drawImage(assets.cloud, 200, 20, null);
    }

    @Override
    public ArrayList<Obstacle> getObstacles() {return super.getObstacles();}

}
