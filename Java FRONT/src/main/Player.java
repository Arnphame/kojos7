package main;

import java.awt.*;
import java.util.Random;

public class Player {

	public ModifyAmmo modifyAmmo = new ModifyAmmo();
	
	private Body body;
	public Ammo ammo;
	public Gun gun;
	public static final float maxThrowVel = 10f;//at 50 pixels length
	
	public float lastThrowVel = 0;
	
	private int startX,endX,startY,endY;

	Boolean ammoIsReady = false;		//Shows if there is new ammo ready to be released on screen
	Boolean isLocalPlayer;
	float health = 100;
	public int id;

	public int speedMultiplier;

	IMovementState movementState;

	public Player(int id, int x, int y, Color color, Boolean isLocalPlayer){		//For players controlled via signalR
		this.body = new Body(x, y, color);
		this.body.leftH.rot = (float)(Math.PI/3 + Math.PI);
		this.body.rightH.rot = -(float)Math.PI/3;
		this.isLocalPlayer = isLocalPlayer;
		this.id = id;
		gun = new Gun(5, "arrow");
		this.speedMultiplier = 3;
		movementState = new Stationary(this);
	}
	
	public void tick(Game game){
		if(isLocalPlayer)								//Get input only if player is controlled locally
			getInput(game);
		move();
	}
	
	public void render(Graphics g){
		body.render(g);
		g.setColor(Color.white);
		if(ammoIsReady){
			g.drawLine(startX, startY, endX, endY);
			g.fillOval(startX-2, startY-2, 4, 4);
			g.fillOval(endX-2, endY-2, 4, 4);
		}

		renderPower(g);
		renderHealth(g);
		lastThrowVel = 0;
	}
	
	public void renderPower(Graphics g){
		g.setColor(new Color(255-(int)((lastThrowVel/maxThrowVel)*255),(int)((lastThrowVel/maxThrowVel)*255),0));
		g.fillRect(5, 5, (int)((lastThrowVel/maxThrowVel)*80), 15);
		g.setColor(Color.ORANGE);
		g.drawRect(5, 5, 80, 15);
	}

	public void renderHealth(Graphics g){
		int width = 35;
		int height = 7;
		g.setColor(new Color(255-(int)((health/100)*255),(int)((health/100)*255),0));
		g.fillRect(body.head.x - width/2, body.head.y - body.head.r - height*2, (int)((health/100)*width), height);
		g.setColor(Color.ORANGE);
		g.drawRect(body.head.x - width/2, body.head.y - body.head.r- height*2, width, height);
	}
	
	public void getInput(Game game){
		if(game.mouseManager.isClicked){
			if (!ammoIsReady){
				startX = game.mouseManager.x;
				startY = game.mouseManager.y;

				ammo = prepareAmmo();
				game.addAmmo(ammo);
				ammoIsReady = true;
			}
			
			Vector vel = new Vector(startX-endX , startY-endY);
			if(vel.x == 0)
				vel.x = 1;
			vel.mul((float) 0.1);
			if(vel.getMag()> maxThrowVel){
				vel.setMag(maxThrowVel);
			}
			lastThrowVel = vel.getMag();

			ammo.setVelocity(vel);

			endX = game.mouseManager.x;
			endY = game.mouseManager.y;
		}
		if(!game.mouseManager.isClicked){
			if(ammoIsReady){
				game.launchAmmo(ammo, true);
			}
			ammoIsReady = false;
		}
		int[] position = this.body.getPosition();
		if(game.keyManager.left && !movementState.getClass().getSimpleName().equals("LeftMovement")){
			setMovementState(new LeftMovement(this));
			if(isLocalPlayer)
				game.gameSubject.send("ChangeMovement", "LeftMovement", position[0], position[1]);
		}
		else if(game.keyManager.right && !movementState.getClass().getSimpleName().equals("RightMovement")) {
			setMovementState(new RightMovement(this));
			if(isLocalPlayer)
				game.gameSubject.send("ChangeMovement", "RightMovement", position[0], position[1]);
		}
		else if(game.keyManager.up && !movementState.getClass().getSimpleName().equals("UpwardsMovement")) {
			setMovementState(new UpwardsMovement(this));
			if(isLocalPlayer)
				game.gameSubject.send("ChangeMovement", "UpwardsMovement", position[0], position[1]);
		}
		else if(game.keyManager.down && !movementState.getClass().getSimpleName().equals("DownwardsMovement")) {
			setMovementState(new DownwardsMovement(this));
			if(isLocalPlayer)
				game.gameSubject.send("ChangeMovement", "DownwardsMovement", position[0], position[1]);
		}
		else if(!game.keyManager.isDirectionKeyPressed() && !movementState.getClass().getSimpleName().equals("Stationary")) {
			setMovementState(new Stationary(this));
			if(isLocalPlayer)
				game.gameSubject.send("ChangeMovement", "Stationary", position[0], position[1]);
		}
	}

	public boolean intersects(Rectangle rect){
		return body.intersects(rect);
	}

	void move(){
		movementState.move();
	}

	public void setPosition(int x, int y){
		this.body.setPosition(x,y);
	}

	public void moveX(int steps){
		this.body.moveX(steps*speedMultiplier);
	}

	public void moveY(int steps){
		this.body.moveY(steps*speedMultiplier);
	}

	public void setMovementState(IMovementState movementState) {
		this.movementState = movementState;
	}

	public Ammo prepareAmmo(){

		if(gun.ammoCount == 0) {
			gun.switchType();
		}
			Ammo ammo = Factory.getAmmo(gun.gunType, new Vector(body.head.x + 20, body.head.y - body.head.r - 20), new Vector());
			ammo.setShooterId(this.id);
			modifyAmmo.addCommand(new GunCommand(gun, GunAction.decrease, 1));
			//modifyAmmo.undo();
			return ammo;
	}

	public void applyDamage(int amount){
		if(health - amount > 0){
			health -= amount;
		}
		else{
			health = 0;
		}
	}
}
