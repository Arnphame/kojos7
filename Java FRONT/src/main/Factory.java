package main;

public class Factory {
    public static Ammo getAmmo(String type, Vector position, Vector velocity, int damage) {
        switch (type) {
            case "arrow":
                return new Arrow(position, velocity, damage, new Vector(0,(float)0.14));
            case "bullet":
                return new Bullet(position, velocity, damage, new Vector(0,(float)0.02));
            default:
                return null;
        }
    }
}