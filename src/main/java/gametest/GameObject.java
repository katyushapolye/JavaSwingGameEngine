package gametest;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class GameObject {
	protected BufferedImage sprite = null;
	protected int X = 0;
	protected int Y = 0;
	protected int degrees = 0;
	
	public GameObject(String pathToSpriteImage,int initX,int initY,int rotation) {
		this.X = initX;
		this.degrees = rotation;
		this.Y = initY;
		try {
		sprite = ImageIO.read(new File(pathToSpriteImage));
		}
		catch(IOException e){
			e.printStackTrace();
		}	
		System.out.println(sprite.getHeight(null));
		
	}
	
	public void draw(Graphics2D g) {
		if(sprite == null) {
			System.out.println("Warning - Spriteless GameObject");
			return;
		}
		//Graphics2D gtemp = sprite.createGraphics();
		g.rotate(Math.toRadians(degrees),X - (sprite.getWidth()/2),Y - (sprite.getHeight()/2));
		//g.translate(0,0);
		//g.drawImage(sprite,null,0,0);
		
		g.drawImage(this.sprite,X - (sprite.getWidth()/2),Y - (sprite.getHeight()/2),null);
	}
	
	public void offsetPosition(int newX,int newY) {
		this.X  +=  newX;
		this.Y +=  newY;
	}
	

}
