package main;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Assets {
	
	private BufferedImage loadImage(String file){
		try{
			return ImageIO.read(Assets.class.getResource(file));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
//	private BufferedImage getSubImage(BufferedImage image, int x,int y,int width,int height){
//		return image.getSubimage(x, y, width, height);
//	}
	
	public BufferedImage arrow;
	public BufferedImage bullet;
	public BufferedImage cloud;
	public BufferedImage bird;
	public BufferedImage sun;
	public BufferedImage grenade;
	public BufferedImage hpboost;
	
	public void init(){
		arrow = loadImage("/arrow.png");
		bullet = loadImage("/bullet.png");
		cloud = loadImage("/cloud.png");
		bird = loadImage("/bird.png");
		sun = loadImage("/sun.png");
		grenade = loadImage("/grenade.png");
		hpboost = loadImage("/hpboost.png");
	}
	
	
}
