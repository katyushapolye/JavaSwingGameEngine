package jsge.components;

import jsge.utils.Point;

public class Transform {
	
	protected int X = 0;
	protected int Y = 0;
	protected int degrees = 0;
	protected double[] scale =  new double[2];
	
	public Transform() {
		this.scale[0] = 1d;
		this.scale[1] = 1d;
		this.X = 0;
		this.Y = 0;
		this.degrees = 0;
		
	}
	
	public Transform(int x ,int y) {
		this.scale[0] = 1d;
		this.scale[1] = 1d;
		this.X = x;
		this.Y = y;
		this.degrees = 0;
		
	}
	
	
	public Transform(int x, int y,int rotation ,double[] scale) {
		if(scale.length != 2) {
			System.out.println("Warning - Scale not valid for transform, setting to (1,1)");
			this.scale[0] = 1d;
			this.scale[1] = 1d;
			this.X = x;
			this.Y = y;
			this.degrees = rotation;
			
		}
		else {
			this.scale[0] = scale[0];
			this.scale[1] = scale[1];
			this.X = x;
			this.Y = y;
			this.degrees = rotation;
			
		}
		
	}
	
	public Point getPosition() {
		return new Point(this.X,this.Y);
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public int getRotation() {
		return this.degrees;
	}
	
	public double[] getScale(){
		return this.scale;
	}
	
	public void setPosition(Point position) {
		this.X = position.X;
		this.Y = position.Y;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public void setRotation(int degrees) {
		this.degrees = degrees;
		
	}
	
	public void setScale(double xScale,double yScale) {
		this.scale[0] = xScale;
		this.scale[1] = yScale;
	}
	
	
	public void offsetPosition(int xOffset,int yOffset) {
		this.X = this.X + xOffset;
		this.Y = this.Y + yOffset;
	}
	
	public void offsetRotation(int degreesOffset) {
		this.degrees = this.degrees + degreesOffset;
	}
	
	
	

}
