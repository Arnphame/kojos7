package main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sounds {
	
	public AudioClip arrow;
	public AudioClip shot;
	public AudioClip pop;
	public AudioClip grenade;

	public AudioClip loadSound(String file){
		return Applet.newAudioClip(Sounds.class.getResource(file));
	}
	
	public void init(){
		arrow = loadSound("/arrow2.wav");
		shot = loadSound("/shot.wav");
		pop = loadSound("/pop.wav");
		grenade = loadSound("/grenade.wav");
	}
	
	
	public void play(AudioClip sound){
		Thread t = new Thread(){
			public void run(){
				sound.play();
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
