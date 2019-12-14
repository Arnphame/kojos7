package main;

import java.awt.*;
import java.util.ArrayList;

public class BirdDecorator extends MapDecorator {

    Assets assets = new Assets();

    public BirdDecorator(IMap map) {
        super(map);
        assets.init();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        renderBird(g);
    }


    private void renderBird(Graphics g) {
        g.drawImage(assets.bird, 350, 35, null);
    }

    @Override
    public ArrayList<Obstacle> getObstacles() {
        return super.getObstacles();
    }


}
