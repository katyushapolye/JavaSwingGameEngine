package jsge.components;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {
	BufferedImage spriteTexture;
	int width;
	int height;
	
	public Sprite() {
		this.width = -1;
		this.height = -1;
		this.spriteTexture = null;
	}
	public Sprite(String pathToFile){
		loadSprite(pathToFile);
	}
	
	
	public void changeSprite(BufferedImage spriteTexture) {
		this.spriteTexture = spriteTexture;
		this.width =  this.spriteTexture.getWidth();
		this.height = this.spriteTexture.getHeight();
		}
	
	
	public BufferedImage getSprite() {
		return this.spriteTexture;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void loadSprite(String pathToFile) {
		try {
			this.spriteTexture = ImageIO.read(new File(pathToFile));
			this.width = spriteTexture.getWidth();
			this.height = spriteTexture.getHeight();
			
		}
		catch(Exception e) {
			System.out.println("Sprite: Warning - Failed to load file "+ pathToFile + ". This may lead to Unexpected behaviour!");
			this.width = -1;
			this.height = -1;
			this.spriteTexture = null;
		}
	}

}
