package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Bullet extends Ammo {
    public Vector gravity;
    public Vector position,velocity;
    public static int length = 50;
    public int damage;

    public int getDamage() {
        return damage;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public boolean stopped = false;

    public Bullet(Vector position, Vector velocity, int damage, Vector gravity){
        this.position = position;
        this.velocity = velocity;
        this.damage = damage;
        this.gravity = gravity;
        this.stopped=true;
    }

    public void tick(){
        if(!stopped){
            velocity.add(gravity);
            velocity.setMag(Config.maxVelocity);
            position.add(velocity);
        }
    }


    public void render(Graphics g,Assets assets){
        int x2 = (int)position.x + (int)((velocity.x/velocity.getMag())*length);
        int y2 = (int)position.y + (int)((velocity.y/velocity.getMag())*length);

        float dy = y2-position.y;
        float dx = x2-position.x;
        float slope=0;

        if(dx != 0)
            slope =  dy/dx;

        float rotation = (float)Math.atan(slope);

        if(dy<0 && dx <0)
            rotation += (float)Math.PI;
        if(dy>0 && dx <0)
            rotation += (float)Math.PI;

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(position.x, position.y);
        g2.rotate(rotation);

        g2.drawImage(assets.bullet, 0,0, length,10,null);

        g2.rotate(-rotation);
        g2.translate(-position.x, -position.y);
    }

    public void launch(){
        stopped = false;
    }
}
