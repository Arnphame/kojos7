package main;

import java.awt.*;
import java.util.ArrayList;

public class SunDecorator extends MapDecorator {

    Assets assets = new Assets();
    public SunDecorator(IMap map) {
        super(map);
        assets.init();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        renderSun(g);
    }


    private void renderSun(Graphics g) {
        g.drawImage(assets.sun, Config.gameWidth-assets.sun.getWidth()+5, 0, null);
    }

    @Override
    public ArrayList<Obstacle> getObstacles() {return super.getObstacles();}


}
