package main;

import java.awt.*;

public interface Ammo {

    Vector getPosition();

    Vector getVelocity();

    int getDamage();

    void setDamage(int dmg);

    void setVelocity(Vector v);

    void setShooterId(int shooterId);

    int getShooterId();

    Rectangle getBounds();

    void tick();
    void render(Graphics g, Assets assets);
    void launch();

    boolean isActive();
}
