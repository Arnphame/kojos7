package main;

import java.awt.*;
import java.util.ArrayList;

public class Map implements IMap{
    private static Map instance = null;
    private MapColorHistory colorsHistory;

    Color color = Color.BLUE;
    private Map() {
        System.out.println("Singleton initialized");
        colorsHistory = new MapColorHistory();
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
    public void createSnapshot(){
        this.colorsHistory.addSnapshot(new MapColorSnapshot(this, this.color));
    }

    @Override
    public void setColor(Color color)
    {
        this.color = color;
    }

    @Override
    public void undoColor(){
        colorsHistory.undo();
    }

    @Override
    public void render(Graphics g) {
        //System.out.println(obstacles.size());
            g.clearRect(0, 0, width, height);
            g.setColor(color);
            g.fillRect(0, 0, width, height);


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
        Color c;
        switch (color.toLowerCase()) {
            case "red":
                c = Color.RED;
                break;
            case "orange":
                c = Color.ORANGE;
                break;
            default:
                c = Color.WHITE;
                break;
        }

        if(index >= 0){
            obstacles.get(index).setPosition(x,y);
        }
        else{
            switch (type.toLowerCase()){
                case "rectangle":
                case "rectangletoobstacleadapter":
                    obstacles.add(new RectangleObstacle(id,new Point(x,y),width,height, c));
                    break;
                case "circle":
                case "circletoobstacleadapter":
                    obstacles.add(new CircleObstacle(id,new Point(x,y),width, c));
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
