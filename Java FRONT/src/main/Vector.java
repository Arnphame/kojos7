package main;

public class Vector {
	
	public float x,y;
	
	public Vector(float x,float y){
		this.x=x;
		this.y=y;
	}
	
	public Vector(){
		this.x=0;
		this.y=0;
	}
	
	public Vector(Vector v){
		this.x=v.x;
		this.y=v.y;
	}
	
	public void add(Vector v){
		this.x += v.x;
		this.y += v.y;
	}
	
	public void sub(Vector v){
		this.x-=v.x;
		this.y-=v.y;
	}
	
	public Vector mul(float  scale){
		this.x*=scale;
		this.y*=scale;
		return this;
	}
	
	public float getMag(){
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public void normalize(){
		float mag = getMag();
		x/=mag;
		y/=mag;
	}
	
	public void setMag(float mag){
		normalize();
		x*=mag;
		y*=mag;
	}
	public void setXMag(float mag){
		normalize();
		x*=mag;
	}
}
