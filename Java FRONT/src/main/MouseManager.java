package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
	
	public int x,y;
	public boolean isClicked;
	
	public MouseManager() {
		this.x=0;
		this.y=0;
		this.isClicked = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isClicked = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isClicked)
			isClicked = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x=e.getX();
		y=e.getY();
	}

}
