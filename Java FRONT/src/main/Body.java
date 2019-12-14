package main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Body implements IBodyPart {
	ArrayList<IBodyPart> parts;
	public Color color;
	int x, y;
	
	public static int headR = 6,limbW = 20,limbH = 5,bodyW = 15, bodyH = 18;
	
	public Body(int x, int y, Color color){
		this.x = x;
		this.y = y-bodyH/2-headR-1;

		this.parts = new ArrayList<>();

		this.parts.add(new Head(x, y-bodyH/2-headR-1, headR, color));
		this.parts.add(new Torso(x-bodyW/2, y-bodyH/2, bodyW, bodyH, 0, color));
		this.parts.add(new Limb(x-bodyW/2+1, y+bodyH/2+1, limbH, limbW, (float)0,limbH/2,0, color));
		this.parts.add(new Limb(x+bodyW/2-limbH, y+bodyH/2+1, limbH, limbW, (float)0,limbH/2,0, color));
		this.parts.add(new Limb(x-bodyW/2-1, y-bodyH/2+1, limbW, limbH, (float)(Math.PI/3 + Math.PI),0,limbH/2, color));
		this.parts.add(new Limb(x+bodyW/2+2, y-bodyH/2, limbW, limbH, -(float)Math.PI/3,0,limbH/2, color));
		
		this.color = color;
	}

	public void setPosition(int x, int y){
		int xOffset = x - this.x;
		int yOffset = y - this.y;

		moveX(xOffset);
		moveY(yOffset);
	}

	@Override
	public void moveX(int steps){
		for (IBodyPart part : parts) {
			part.moveX(steps);
		}
		this.x += steps;
	}

	@Override
	public void moveY(int steps){
		for (IBodyPart part : parts) {
			part.moveY(steps);
		}
		this.y += steps;
	}

	@Override
	public int getX() {
		return parts.get(0).getX();
	}

	@Override
	public int getY() {
		return parts.get(0).getY();
	}

	@Override
	public void render(Graphics g){
		for (IBodyPart part : parts) {
			part.render(g);
		}
	}

	@Override
	public boolean intersects(Rectangle rect){
		for (IBodyPart part : parts) {
			if(part.intersects(rect))
				return true;
		}
		return false;
	}

	@Override
	public void setColor(Color color) {
		for (IBodyPart part : parts) {
			part.setColor(color);
		}
	}
}	


