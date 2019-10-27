package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionState;
import main.Body.Limb;

public class Player {
	
	private Body body;
	public Arrow arrow;
	public static final float maxThrowVel = 10f;//at 50 pixels length
	public static final int maxThrowLength = 30; // per unit
	
	public float lastThrowVel = 0;
	
	private int startX,endX,startY,endY;

	Boolean arrowIsReady = false;		//Shows if there is new arrow ready to be released on screen
	Boolean isLocalPlayer;
	HubConnection connection;

	public Player(int x, int y, Color color, Boolean isLocalPlayer){		//For players controlled via signalR

		this.body = new Body(x, y, color);
		this.body.leftH.rot = (float)(Math.PI/3 + Math.PI);
		this.body.rightH.rot = -(float)Math.PI/3;
		this.isLocalPlayer = isLocalPlayer;

		/*if(isLocalPlayer) {
			this.connection.on("Shoot", (xDim, yDim) -> {
				arrows.add(new Arrow(new Vector(body.head.x, body.head.y - body.head.r), new Vector(xDim, yDim), new Vector(), Color.LIGHT_GRAY));
				arrows.get(arrows.size() - 1).launch();
				this.sounds.play(this.sounds.arrow);
			}, Float.class, Float.class);
		}*/
	}
	
	public void tick(Game game){
		if(isLocalPlayer)								//Get input only if player is controlled locally
			getInput(game);
	}
	
	public void render(Graphics g,Assets assets){
		body.render(g);
		
		g.setColor(Color.white);
		if(arrowIsReady){
			g.drawLine(startX, startY, endX, endY);
			g.fillOval(startX-2, startY-2, 4, 4);
			g.fillOval(endX-2, endY-2, 4, 4);
		}
		
		renderPower(g);
		lastThrowVel = 0;
	}
	
	public void renderPower(Graphics g){
		g.setColor(new Color(255-(int)((lastThrowVel/maxThrowVel)*255),(int)((lastThrowVel/maxThrowVel)*255),0));
		g.fillRect(5, 5, (int)((lastThrowVel/maxThrowVel)*80), 15);
		g.setColor(Color.ORANGE);
		g.drawRect(5, 5, 80, 15);
	}
	
	public void getInput(Game game){
		if(game.mouseManager.isClicked){
			if (!arrowIsReady){
				startX = game.mouseManager.x;
				startY = game.mouseManager.y;

				arrow = prepareArrow();
				game.addArrow(arrow);
				arrowIsReady = true;
			}
			
			Vector vel = new Vector(startX-endX , startY-endY);
			vel.mul((float) 0.1);
			if(vel.getMag()> maxThrowVel){
				vel.setMag(maxThrowVel);
			}
			lastThrowVel = vel.getMag();

			arrow.velocity = vel;

			endX = game.mouseManager.x;
			endY = game.mouseManager.y;
		}
		if(!game.mouseManager.isClicked){
			if(arrowIsReady){
				game.launchArrow(arrow, true);
				//game.sounds.play(game.sounds.arrow);
			}
			arrowIsReady = false;
		}
	}

	public Arrow prepareArrow(){
		return new Arrow(new Vector(body.head.x,body.head.y - body.head.r), new Vector(), Color.LIGHT_GRAY, new Vector());
	}
}
