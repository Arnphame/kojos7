package main;

import com.microsoft.signalr.HubConnection;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game implements Runnable{
	
	private JFrame frame;
	private Canvas canvas;
	private Thread thread;
	private Map map;
	
	public int width,height;
	public String title;
	private boolean running = false;

	private BufferStrategy bs;
	private Graphics g;
	
	public KeyManager keyManager;
	public MouseManager mouseManager;
	
	public Assets assets;
	public Sounds sounds;

	HubConnection connection;

	private ArrayList<Player> players;
	private ArrayList<Ammo> ammos;
	
	public static Vector gravity = new Vector(0,0.14f);
	
	public Game(String title, int width, int height, HubConnection connection, int mapType){
		this.width = width;
		this.height = height;
		this.title = title;
		this.connection = connection;
		//ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
		//obstacles.add();
		Obstacle obstacle = new Obstacle(100,100,50,50, Color.cyan, new VerticalMovement(50,200));
		Obstacle obstacle2 = new Obstacle(100,100,50,50, Color.cyan, new HorizontalMovement(50,200));
		this.map = new Map.Builder(0)
				.addTitle(":)")
				.setWidth(720)
				.setHeight(420)
				.addObstacles(obstacle)
				.addObstacles(obstacle2)
				.build();
		//m = new Map("x", 720, 420, mapType).addObstacle(new Obstacle(100,100,50,50, Color.cyan));
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
		for (Player player : players) {
			player.tick(this);
		}
		ArrayList<Ammo> ammosToRemove = new ArrayList<>();
		for (Ammo ammo : ammos) {
			ammo.tick();

			for (Player player : players) {
				if(player.id != ammo.shooterId && player.intersects(ammo.getBounds()))
					System.out.println("Enemy shot");
			}

			for (Obstacle obstacle : map.obstacles){
				if(ammo.getBounds().intersects(obstacle.getBounds())){
					sounds.play(sounds.pop);
					ammosToRemove.add(ammo);
				}
			}

			Rectangle rect = new Rectangle(0, -height, width, height*2);
			if(!rect.contains(ammo.getPosition().x, ammo.getPosition().y)){
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

	public void addPlayer(Player player){
		players.add(player);
	}

	public void addAmmo(Ammo ammo){
		ammos.add(ammo);
	}

	public void launchAmmo(Ammo ammo, boolean toServer){
		ammo.launch();
		String c = ammo.getClass().getSimpleName();
		switch (c){
			case "Arrow":
				sounds.play(sounds.arrow);
			case "Bullet":
				sounds.play(sounds.shot);
			default:
		}
		/*if(toServer)
			connection.send("Shoot", ammo.getPosition().x, ammo.getPosition().y, ammo.getVelocity().x, ammo.getVelocity().y);*/
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
