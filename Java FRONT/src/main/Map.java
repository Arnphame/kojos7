package main;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private static Map instance = null;
    private Map() {
        System.out.println("Singleton initialized");
    }
    public static synchronized Map getInstance() {
        if(instance == null){
            instance = new Map();
        }
        System.out.println("Returning instance");
        return instance;
    }
    private String title;
    private int width;
    private int height;
    private int type;
    private ArrayList<Obstacle> obstacles;

    public void render(Graphics g) {
        if (type == 0) {
            g.clearRect(0, 0, width, height);
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, width, height);
        }
        if (type == 1) {
            g.clearRect(0, 0, width, height);
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, width, height);
        }
        for(Obstacle o: obstacles) {
            o.tick();
            o.render(g);
        }
    }

    public static class Builder {
        private String title;
        private int width;
        private int height;
        private int type;
        private ArrayList<Obstacle> obstacles;

        public Builder(int type)
        {
            this.type = type;
            obstacles = new ArrayList<Obstacle>();
        }

        public Builder addTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder addObstacles(Obstacle obstacle)
        {
            obstacles.add(obstacle);
            return this;
        }

        public Map build()
        {
            Map map = new Map();
            map.title = this.title;
            map.width = this.width;
            map.height = this.height;
            map.obstacles = this.obstacles;

            return map;
        }
    }
}
