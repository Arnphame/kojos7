package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionState;
import main.Body.Limb;

public class Player {
	
	private Body body;
	public ArrayList<Arrow> arrows;
	public static final float maxThrowVel = 10f;//at 50 pixels length
	public static final int maxThrowLength = 30; // per unit
	
	public float lastThrowVel = 0;
	
	private int startX,endX,startY,endY;

	public Sounds sounds;

	Boolean arrowIsReady = false;		//Shows if there is new arrow ready to be released on screen
	Boolean isOpponent;
	HubConnection connection;

	public Player(int x, int y, Color color, HubConnection hubConnection, Boolean isOpponent){		//For players controlled via signalR

		this.body = new Body(x, y, color);
		this.body.leftH.rot = (float)(Math.PI/3 + Math.PI);
		this.body.rightH.rot = -(float)Math.PI/3;
		this.arrows = new ArrayList<Arrow>();
		this.connection = hubConnection;
		this.isOpponent = isOpponent;
		this.sounds = new Sounds();
		this.sounds.init();

		if(isOpponent) {
			this.connection.on("Shoot", (xDim, yDim) -> {
				arrows.add(new Arrow(new Vector(body.head.x, body.head.y - body.head.r), new Vector(xDim, yDim), new Vector(), Color.LIGHT_GRAY));
				arrows.get(arrows.size() - 1).launch();
				this.sounds.play(this.sounds.arrow);
			}, Float.class, Float.class);
		}
	}
	
	public void tick(Game game){
		if(!isOpponent)								//Get input only if player is controlled locally
			getInput(game.mouseManager);

		for(int i=arrows.size()-1;i>=0;i--){
			arrows.get(i).tick(game);
			if(arrows.get(i).outside)
				arrows.remove(i);
		}
		body.tick();
	}
	
	public void render(Graphics g,Assets assets){
		for(int i=arrows.size()-1;i>=0;i--){
			arrows.get(i).render(g,assets);
		}
		body.render(g);
		
		g.setColor(Color.white);
		if(arrowIsReady){
			g.drawLine(startX, startY, endX, endY);
			g.fillOval(startX-2, startY-2, 4, 4);
			g.fillOval(endX-2, endY-2, 4, 4);
			
		}
		
		g.fillRect(body.leftL.x - 10, body.leftL.y+body.leftL.h + 1, 10 + body.body.w + 10, 10);
		
		renderPower(g);
		lastThrowVel = 0;
		
	}
	
	public void renderPower(Graphics g){
		
		g.setColor(new Color(255-(int)((lastThrowVel/maxThrowVel)*255),(int)((lastThrowVel/maxThrowVel)*255),0));
		g.fillRect(5, 5, (int)((lastThrowVel/maxThrowVel)*80), 15);
		
		g.setColor(Color.ORANGE);
		g.drawRect(5, 5, 80, 15);
		
	}
	
	public void getInput(MouseManager mouse){
		Vector vel = new Vector();
		if(mouse.isClicked){
			if (!arrowIsReady){
				startX = mouse.x;
				startY = mouse.y;

				arrows.add(new Arrow(new Vector(body.head.x,body.head.y - body.head.r), vel, new Vector(), Color.LIGHT_GRAY));
				arrowIsReady = true;
			}
			
			vel = new Vector(startX-endX , startY-endY);
			vel.mul((float) 0.1);
			if(vel.getMag()> maxThrowVel){
				vel.setMag(maxThrowVel);
			}
			lastThrowVel = vel.getMag();

			arrows.get(arrows.size()-1).vel = vel;

			endX = mouse.x;
			endY = mouse.y;
		}
		if(!mouse.isClicked){
			if(arrowIsReady){
				connection.send("Shoot", arrows.get(arrows.size()-1).vel.x, arrows.get(arrows.size()-1).vel.y);
				arrows.get(arrows.size()-1).launch();
				this.sounds.play(this.sounds.arrow);
			}
			arrowIsReady = false;
		}
	}
}
