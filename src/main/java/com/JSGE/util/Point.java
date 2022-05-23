package com.JSGE.util;

public class Point {
	private int X=0;
	private int Y=0;
	public Point(int X, int Y){
		this.X = X;
		this.Y = Y;
		
	}
	
	public static float distance(Point p1,Point p2) {
		return (float) Math.sqrt((double)(Math.pow(( p1.X- p2.X),2) +   Math.pow((p1.Y-p2.Y),2)));
	}

}
