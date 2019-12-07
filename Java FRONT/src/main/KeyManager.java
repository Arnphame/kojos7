package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	
	public boolean up,down,left,right;
	
	public KeyManager() {
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				up = true;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				down = true;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				right = true;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				left = true;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
				up = false;
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				down = false;
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				right = false;
				break;
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				left = false;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
