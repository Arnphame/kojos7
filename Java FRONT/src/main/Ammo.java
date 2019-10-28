package main;

import java.awt.*;

public abstract class Ammo {
    public abstract Vector getPosition();

    public abstract Vector getVelocity();

    public abstract int getDamage();

    public abstract void setVelocity(Vector v);

    public abstract void tick();
    public abstract void render(Graphics g, Assets assets);
    public abstract void launch();
}
