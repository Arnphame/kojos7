package main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Body {
	
	public Head head;
	public Limb leftH,rightH,leftL,rightL;
	public Torso torso;
	public Color color;
	
	public static int headR = 6,limbW = 20,limbH = 5,bodyW = 15, bodyH = 18;
	
	public Body(int x, int y, Color color){
		
		head = new Head(x, y-bodyH/2-headR-1, headR);
		torso = new Torso(x-bodyW/2, y-bodyH/2, bodyW, bodyH, 0);

		leftL = new Limb(x-bodyW/2+1, y+bodyH/2+1, limbH, limbW, (float)0,limbH/2,0);
		rightL = new Limb(x+bodyW/2-limbH, y+bodyH/2+1, limbH, limbW, (float)0,limbH/2,0);
		
		leftH = new Limb(x-bodyW/2-1, y-bodyH/2+1, limbW, limbH, (float)Math.PI,0,limbH/2);
		rightH = new Limb(x+bodyW/2+2, y-bodyH/2, limbW, limbH, 0,0,limbH/2);
		
		this.color = color;
	}

	public void setPosition(int x, int y){
		head.x = x;
		head.y = y-bodyH/2-headR-1;

		torso.x = x-bodyW/2;
		torso.y = y-bodyH/2;

		leftL.x = x-bodyW/2+1;
		leftL.y = y+bodyH/2+1;

		rightL.x = x+bodyW/2-limbH;
		rightL.y = y+bodyH/2+1;

		leftH.x = x-bodyW/2-1;
		leftH.y = y-bodyH/2+1;

		rightH.x = x+bodyW/2+2;
		rightH.y = y-bodyH/2;
	}

	public int[] getPosition(){
		int[] position = new int[2];
		position[0] = head.x;
		position[1] = head.y + bodyH/2 + headR + 1;
		return position;
	}

	public void moveX(int steps){
		head.x += steps;
		leftH.x += steps;
		rightH.x += steps;
		leftL.x += steps;
		rightL.x += steps;
		torso.x += steps;
	}

	public void moveY(int steps){
		head.y += steps;
		leftH.y += steps;
		rightH.y += steps;
		leftL.y += steps;
		rightL.y += steps;
		torso.y += steps;
	}
	
	public void render(Graphics g){
		head.render(g, color);
		torso.render(g, color);
		leftH.render(g, color);
		rightH.render(g, color);
		leftL.render(g, color);
		rightL.render(g, color);
	}

	public boolean intersects(Rectangle rect){
		return head.getBounds().intersects(rect)
				|| leftH.getBounds().intersects(rect)
				|| rightH.getBounds().intersects(rect)
				|| leftL.getBounds().intersects(rect)
				|| rightL.getBounds().intersects(rect)
				|| torso.getBounds().intersects(rect);
	}

	class Head{
		int x,y,r;
		
		public Head(int x,int y,int r){
			this.x=x;
			this.y=y;
			this.r=r;
		}
		
		public void render(Graphics g, Color color){
			g.setColor(color);
			g.fillOval(x-r, y-r, 2*r, 2*r);
		}
		public Rectangle getBounds(){
			return new Rectangle(x-r, y-r, 2*r, 2*r);
		}
		
	}	
	
	class Limb{
		int x,y,w,h;
		float rot;
		int jointX,jointY;
		
		public Limb(int x,int y,int w,int h, float rot,int jointX , int jointY){
			this.x=x;
			this.y=y;
			this.rot=rot;
			this.w=w;
			this.h=h;
			this.jointX = jointX;
			this.jointY = jointY;
		}
		
		public void render(Graphics g1,Color color){
			Graphics2D g = (Graphics2D)g1;
			
			
			g.translate(x + jointX, y + jointY);
			g.rotate(-rot);
			g.translate(-jointX, -jointY);
			
			g.setColor(color);
			g.fillRect(0, 0, w, h);
			
			g.translate(jointX,jointY);
			g.rotate(rot);
			g.translate(-x-jointX,-y-jointY);
		}

		public Rectangle getBounds(){
			AffineTransform tx = new AffineTransform();
			tx.translate(x + jointX, y + jointY);
			tx.rotate(-rot);
			Rectangle shape = new Rectangle(0, 0, w, h);
			Shape newShape = tx.createTransformedShape(shape);
			return newShape.getBounds();
		}
	}
	
	class Torso{
		int x,y,w,h;
		float rot;
		
		public Torso(int x,int y,int w,int h, float rot){
			this.x=x;
			this.y=y;
			this.rot=rot;
			this.w=w;
			this.h=h;
		}	
		
		public void render(Graphics g1,Color color){
			Graphics2D g = (Graphics2D)g1;
			g.setColor(color);

			g.translate(x, y);
			g.rotate(-rot);
			
			g.fillRect(0, 0, w, h);
			
			g.rotate(rot);
			g.translate(-x,-y);
		}

		public Rectangle getBounds(){
			AffineTransform tx = new AffineTransform();
			tx.translate(x, y);
			tx.rotate(-rot);
			Rectangle shape = new Rectangle(0, 0, w, h);
			Shape newShape = tx.createTransformedShape(shape);
			return newShape.getBounds();
		}
	}
	
}	


