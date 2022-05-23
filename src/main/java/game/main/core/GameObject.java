package game.main.core;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.utils.*;
import game.main.utils.Utils.Layer;
import java.util.ArrayList;

public class GameObject {
	protected String name;
	protected BufferedImage sprite = null;
	protected int X = 0;
	protected int Y = 0;
	protected int degrees = 0;
	protected double[] scale =  new double[2];
	
	protected Layer layer = null;
	protected int colliderRadius = 0;
	
	
	private static int TOTAL_GAME_OBJECT_COUNT = 0;
	private static ArrayList<GameObject> totalGameObjects =  new ArrayList<GameObject>();
	
	public static ArrayList<GameObject> getAllGameObjects() {
		return totalGameObjects;
	}
	
	public static void destroyAllGameObjects() {
		TOTAL_GAME_OBJECT_COUNT=0;
		totalGameObjects.clear();
	}
	
	public GameObject(String name,String pathToSpriteImage,int initX,int initY,int rotation,Layer initLayer,int colliderRadius){
		this.name = name;
		this.layer = initLayer;
		this.X = initX;
		this.degrees = rotation;
		this.Y = initY;
		this.colliderRadius = colliderRadius;
		scale[0] = 1;
		scale[1] = 1;
		try {
		sprite = ImageIO.read(new File(pathToSpriteImage));
		}
		catch(IOException e){
			e.printStackTrace();
		}	
		
		totalGameObjects.add(this);
		TOTAL_GAME_OBJECT_COUNT++;
		System.out.println(sprite.getHeight(null));
		System.out.println(sprite.getWidth(null));
		
	}
	
	public void draw(Graphics2D g) {
		if(sprite == null) {
			System.out.println("Warning - Spriteless GameObject");
			return;
		}
		g.drawImage(sprite, applyTransform(), null);
		g.setColor(Color.GREEN);
		g.drawOval(this.X-colliderRadius,this.Y-colliderRadius, colliderRadius*2, colliderRadius*2); 
	}
	
	
	
	public Utils.Layer getLayer() {return this.layer;}
	public void setPosition(int newX,int newY) {
		this.X = newX;
		this.Y = newY;
	}
	
	public void setRotation(int degrees) {
		this.degrees = degrees;
	}
	
	public void offsetPosition(int newX,int newY) {
		this.X  +=  newX;
		this.Y +=  newY;
	}
	
	public void offsetRotation(int degrees) {
		this.degrees = this.degrees+degrees;
	}
	
	public void update(double deltaTime) {
		//System.out.println("Waring - Object default Update has not been overridden");
		
	}
	
	public int getColliderRadius() {
		return this.colliderRadius;
	}
	
	public Point getPosition() {
		return new Point(this.X,this.Y);
	}
	
	public String getName() {
		return this.name;
	}
	
	
	protected AffineTransform applyTransform() {
		AffineTransform af = new AffineTransform();
		af.translate(this.X, this.Y);
		af.rotate(Math.toRadians(degrees));
		af.translate(-sprite.getWidth()/2,-sprite.getHeight()/2);
		af.scale(this.scale[0],this.scale[1]);
	
		return af;
	}

}
