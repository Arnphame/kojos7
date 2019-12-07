package main;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game implements Runnable{
	
	private JFrame frame;
	private Canvas canvas;
	private Thread thread;
	private IMap map;
	
	public int width,height;
	public String title;
	private boolean running = false;

	private BufferStrategy bs;
	private Graphics g;
	
	public KeyManager keyManager;
	public MouseManager mouseManager;
	
	public Assets assets;
	public Sounds sounds;

	Subject gameSubject;

	private ArrayList<Player> players;
	private ArrayList<Ammo> ammos;

	Rectangle gameBounds;
	
	public static Vector gravity = new Vector(0,0.14f);
	
	public Game(String title, int width, int height, Subject gameSubject, int mapType){
		this.width = width;
		this.height = height;
		this.title = title;
		this.gameBounds = new Rectangle(0, -height, width, height*2);
		this.gameSubject = gameSubject;
		this.map = new BirdDecorator(new SunDecorator(new CloudDecorator(new Map.Builder(0)
				.addTitle(":)")
				.setWidth(width)
				.setHeight(height)
				.build())));
		players = new ArrayList<>();
		ammos = new ArrayList<>();

		initDisplay();
	}
	
	public void initDisplay(){
		frame = new JFrame(title);
		frame.setSize(width,height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width,height));
		canvas.setMaximumSize(new Dimension(width,height));
		canvas.setMinimumSize(new Dimension(width,height));
		
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
		
		keyManager = new KeyManager();
		frame.addKeyListener(keyManager);
		
		mouseManager = new MouseManager();
		frame.addMouseListener(mouseManager);
		frame.addMouseMotionListener(mouseManager);
		canvas.addMouseListener(mouseManager);
		canvas.addMouseMotionListener(mouseManager);
	}
	
	public void init(){
		assets = new Assets();
		assets.init();
		
		sounds = new Sounds();
		sounds.init();
	}
	
	public void tick(){
		Graphics2D g2 = (Graphics2D)g;
		for (Player player : players) {
			player.tick(this);
		}
		ArrayList<Ammo> ammosToRemove = new ArrayList<>();
		for (Ammo ammo : ammos) {
			ammo.tick();

			if(!ammo.isActive())
				break;

			for (Player player : players) {
				if(player.id != ammo.getShooterId() && player.intersects(ammo.getBounds())){
					ammosToRemove.add(ammo);
					player.applyDamage(ammo.getDamage());
				}
			}

			for (Obstacle obstacle : map.getObstacles()){
				if(obstacle.getBounds().intersects(ammo.getBounds())){
					sounds.play(sounds.pop);
					ammosToRemove.add(ammo);
				}
			}

			if(!gameBounds.contains(ammo.getPosition().x, ammo.getPosition().y)){
				ammosToRemove.add(ammo);
			}
		}
		for (Ammo ammo : ammosToRemove) {
			ammos.remove(ammo);
		}
	}
	
	public void render(){
		bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		// Setting-up background
		map.render(g);

		for (Player player : players) {
			player.render(g);
		}

		for (Ammo ammo : ammos) {
			ammo.render(g,assets);
		}

		bs.show();
		g.dispose();
	}

	public void updateObstacle(String type, int id, int x, int y, int width, int height, String color){
		map.updateObstacles(type, id, x, y, width, height, color);
	}

	public void addPlayer(Player player){
		players.add(player);
	}

	public void addAmmo(Ammo ammo){
		ammos.add(ammo);
	}

	public void launchAmmo(Ammo ammo, boolean toServer){
		ammo.launch();
		String c = ammo.getClass().getSimpleName().toLowerCase();
		switch (c){
			case "arrow":
				sounds.play(sounds.arrow);
				break;
			case "bullet":
				sounds.play(sounds.shot);
				break;
			case "grenade":
				sounds.play(sounds.grenade);
				break;
			default:
				System.out.println(":)");
		}
		if(toServer)
            gameSubject.send("Shoot", ammo.getPosition().x, ammo.getPosition().y, ammo.getVelocity().x, ammo.getVelocity().y, c);
	}

	public void movePlayer(int steps){
		if(gameSubject.isAlive()){
			gameSubject.send("MovePlayer", steps);
		}
	}

	public Player getOpponent(){
		for (Player player: players) {
			if(!player.isLocalPlayer)
				return player;
		}
		return null;
	}

	public void setOpponentPosition(int x, int y){
		Player opponent = players.get(0);

		for (Player player: players) {
			if(!player.isLocalPlayer)
				opponent = player;
		}

		opponent.setPosition(x, y);
	}

	public void setOpponentMovement(String movementType){
		Player opponent = players.get(0);

		for (Player player: players) {
			if(!player.isLocalPlayer)
				opponent = player;
		}

		IMovementState movementState;
		switch (movementType) {
			case "RightMovement":
				movementState = new RightMovement(opponent);
				break;
			case "LeftMovement":
				movementState = new LeftMovement(opponent);
				break;
			case "UpwardsMovement":
				movementState = new UpwardsMovement(opponent);
				break;
			case "DownwardsMovement":
				movementState = new DownwardsMovement(opponent);
				break;
			default:
				movementState = new Stationary(opponent);
		}
		opponent.setMovementState(movementState);
	}
	
	public void run(){
		init();
		long lastTime = System.nanoTime(),now,timer=0;
		double delta =0,  nsPerTick = 1000000000/60;
		int frames = 0;
		
		while(running){
			now = System.nanoTime();
			timer += now-lastTime;
			delta += (now-lastTime)/nsPerTick;
			lastTime=now;
			if(delta >= 1){
				tick();
				render();
				frames++;
				delta--;
			}
			if(timer >= 1000000000){
				frame.setTitle(title + " FPS: " + frames);
				frames = 0;
				timer = 0;
			}
		}	
	}
	
	public void start(){
		if(!running){
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop(){
		if(running){
			try {
				thread.join();
			} catch (InterruptedException e) {
				System.err.println("Error while terminating the thread!");
			}
		}
	}
}
