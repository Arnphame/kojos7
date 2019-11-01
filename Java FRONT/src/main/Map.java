package main;

import java.awt.*;
import java.util.ArrayList;

public class Map implements IMap{
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
    public ArrayList<Obstacle> obstacles;

    @Override
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
            o.render(g);
        }
    }

    @Override
    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    @Override
    public void updateObstacles(String type, int id, int x, int y, int width, int height, String color) {
        int index = findObstacle(id);
        if(index >= 0){
            obstacles.get(index).setPosition(x,y);
        }
        else{
            switch (type.toLowerCase()){
                case "rectangle":
                    obstacles.add(new RectangleObstacle(id,new Point(x,y),width,height, Color.orange));
                    break;
                case "circle":
                    obstacles.add(new CircleObstacle(id,new Point(x,y),width, Color.orange));
                    break;
            }
        }
    }

    int findObstacle(int id){
        for (int i = 0; i < obstacles.size(); i++) {
            if(obstacles.get(i).getId() == id)
                return i;
        }
        return -1;
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
