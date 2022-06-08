//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.utils;

public class Point {
	public int X=0;
	public int Y=0;
	public Point(int X, int Y){
		this.X = X;
		this.Y = Y;
		
	}
	
	
	
	public static float distance(Point p1,Point p2) {
		return (float) Math.sqrt((double)(Math.pow(( p1.X- p2.X),2) +   Math.pow((p1.Y-p2.Y),2)));
	}

}
