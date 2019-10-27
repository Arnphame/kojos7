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
	private ArrayList<Arrow> arrows;
	
	public static Vector gravity = new Vector(0,0.14f);
	
	public Game(String title, int width, int height, HubConnection connection){
		this.width = width;
		this.height = height;
		this.title = title;
		this.connection = connection;
		players = new ArrayList<>();
		arrows = new ArrayList<>();
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
		ArrayList<Arrow> arrowsToRemove = new ArrayList<>();
		for (Arrow arrow : arrows) {
			arrow.tick();

			Rectangle rect = new Rectangle(0, -height, width, height*2);
			if(!rect.contains(arrow.position.x, arrow.position.y)){
				arrowsToRemove.add(arrow);
			}
		}
		for (Arrow arrow : arrowsToRemove) {
			arrows.remove(arrow);
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
		g.clearRect(0, 0, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, width, height);

		for (Player player : players) {
			player.render(g,assets);
		}

		for (Arrow arrow : arrows) {
			arrow.render(g,assets);
		}

		bs.show();
		g.dispose();
	}

	public void addPlayer(Player player){
		players.add(player);
	}

	public void addArrow(Arrow arrow){
		arrows.add(arrow);
		arrow.gravity = gravity;
	}

	public void launchArrow(Arrow arrow, boolean toServer){
		arrow.launch();
		sounds.play(sounds.arrow);
		if(toServer)
			connection.send("Shoot", arrow.position.x, arrow.position.y, arrow.velocity.x, arrow.velocity.y);
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
