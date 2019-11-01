package main;

public class Factory {
    public static Ammo getAmmo(String type, Vector position, Vector velocity) {
        switch (type) {
            case "arrow":
                return new Arrow(position, velocity, Config.arrowDmg, new Vector(0,(float)0.14));
            case "bullet":
                return new Bullet(position, velocity, Config.bulletDmg, new Vector(0,(float)0.02));
            default:
                return null;
        }
    }
}