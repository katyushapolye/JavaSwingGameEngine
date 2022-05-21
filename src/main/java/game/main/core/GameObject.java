package game.main.core;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.main.core.Utils.*;


public class GameObject {
	protected BufferedImage sprite = null;
	protected int X = 0;
	protected int Y = 0;
	protected int degrees = 0;
	protected double[] scale =  new double[2];
	protected Layer layer = null;
	
	public GameObject(String pathToSpriteImage,int initX,int initY,int rotation,Layer initLayer){
		this.layer = initLayer;
		this.X = initX;
		this.degrees = rotation;
		this.Y = initY;
		scale[0] = 1;
		scale[1] = 1;
		try {
		sprite = ImageIO.read(new File(pathToSpriteImage));
		}
		catch(IOException e){
			e.printStackTrace();
		}	
		System.out.println(sprite.getHeight(null));
		System.out.println(sprite.getWidth(null));
		
	}
	
	public void draw(Graphics2D g) {
		if(sprite == null) {
			System.out.println("Warning - Spriteless GameObject");
			return;
		}
		//g.drawOval(this.X-(sprite.getWidth()/2),this.Y-(sprite.getHeight()/2), 30, 30); colisor interessante
		g.drawImage(sprite, applyTransform(), null);
	}
	
	
	public Layer getLayer() {return this.layer;}
	public void setPosition(int newX,int newY) {
		this.X = newX;
		this.Y = newY;
	}
	
	public void setRotarion(int degrees) {
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
	
	private AffineTransform applyTransform() {
		AffineTransform af = new AffineTransform();
		af.translate(this.X, this.Y);
		af.rotate(Math.toRadians(degrees));
		af.translate(-sprite.getWidth()/2,-sprite.getHeight()/2);
		af.scale(this.scale[0],this.scale[1]);
	
		return af;
	}

}
