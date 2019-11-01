package main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet implements Ammo {
    public int shooterId;
    public Vector gravity;
    public Vector position,velocity;
    public static int length = 30;
    public int damage;
    private float rotation;
    public Rectangle bounds;


    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    @Override
    public Vector getPosition() {
        return position;
    }

    @Override
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
        this.bounds = new Rectangle((int)position.x,(int)position.y,3,10);
    }

    @Override
    public void tick(){
        if(!stopped){
            velocity.add(gravity);
            velocity.setMag(Config.maxVelocity);
            position.add(velocity);

        }
        updateBound();
    }

    @Override
    public void render(Graphics g,Assets assets){
        int x2 = (int)position.x + (int)((velocity.x/velocity.getMag())*length);
        int y2 = (int)position.y + (int)((velocity.y/velocity.getMag())*length);

        float dy = y2-position.y;
        float dx = x2-position.x;
        float slope=0;

        if(dx != 0)
            slope =  dy/dx;

        rotation = (float)Math.atan(slope);

        if(dy<0 && dx <0)
            rotation += (float)Math.PI;
        if(dy>0 && dx <0)
            rotation += (float)Math.PI;

        Graphics2D g2 = (Graphics2D) g;
        g2.translate(position.x, position.y);
        g2.rotate(rotation);

        g2.drawImage(assets.bullet, 0, 0, null);

        g2.rotate(-rotation);
        g2.translate(-position.x, -position.y);

        g2.setColor(Color.white);
        g2.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds(){

        return bounds;
    }
    public void updateBound() {
        int x2 = (int) position.x + (int) ((velocity.x / velocity.getMag()) * length);
        int y2 = (int) position.y + (int) ((velocity.y / velocity.getMag()) * length);
        bounds.x = x2;
        bounds.y = y2;
    }

    @Override
    public void launch(){
        stopped = false;
    }

    @Override
    public void setShooterId(int shooterId) {
        this.shooterId = shooterId;
    }

    @Override
    public int getShooterId(){
        return shooterId;
    }
}
