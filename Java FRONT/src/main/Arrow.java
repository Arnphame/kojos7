package main;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Arrow extends Ammo {

	public Vector gravity;
	public Vector position,velocity;
	public static int length = 50;
	public int damage;
	private float rotation;

	public int getDamage() {
		return damage;
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return velocity;
	}


	public boolean stopped = false;
	
	public Arrow(Vector position, Vector velocity, int damage, Vector gravity){
		this.position = position;
		this.velocity = velocity;
		this.damage = damage;
		this.gravity = gravity;
		this.stopped = true;
	}

	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	public void tick(){
		if(!stopped){
			velocity.add(gravity);
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
		
		rotation = (float)Math.atan(slope);
		
		if(dy<0 && dx <0)
			rotation += (float)Math.PI;
		if(dy>0 && dx <0)
			rotation += (float)Math.PI;
			
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(position.x, position.y);
		g2.rotate(rotation);
		
		g2.drawImage(assets.arrow, 0,0, length,10,null);
		
		g2.rotate(-rotation);
		g2.translate(-position.x, -position.y);
	}

	public Rectangle getBounds(){
		AffineTransform tx = new AffineTransform();
		tx.translate(position.x, position.y);
		tx.rotate(rotation);
		Assets assets = new Assets();
		assets.init();
		Rectangle shape = new Rectangle(0, 0, length, 10);
		Shape newShape = tx.createTransformedShape(shape);
		return newShape.getBounds();
	}
	public void launch(){
		stopped = false;
	}
}
