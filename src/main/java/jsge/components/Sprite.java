package jsge.components;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {
	BufferedImage spriteTexture;
	int width;
	int height;
	int alpha;
	
	public Sprite() {
		this.width = -1;
		this.height = -1;
		this.alpha =  255;
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
	
	//Terrivelmente ineficiente, setta manualmente Alpha de cada pixel.
	public void setAlpha(int alpha) {
		if(alpha < 0 )this.alpha = 0;
		if(this.alpha + alpha > 255) {
			this.alpha = 255;
		}
	    for (int x = 0; x < this.spriteTexture.getWidth(); x++) {          
	    	for (int y = 0; y < this.spriteTexture.getHeight(); y++) {
	    		Color temp = new  Color(this.spriteTexture.getRGB(x, y),true);
	    		int red = temp.getRed();
	    		int green =  temp.getGreen();
	    		int blue = temp.getBlue();
	    		int[] rgb = {red,green,blue,alpha};
	    
	    		this.spriteTexture.setRGB(x, y, new Color(rgb[0],rgb[1],rgb[2],alpha).getRGB());
	    		System.out.println();
	    		
                        
	    		}
	    	}
	    }

	
	public void loadSprite(String pathToFile) {
		try {
			this.spriteTexture = ImageIO.read(new File(pathToFile));
			assert(this.spriteTexture.getType() ==  BufferedImage.TYPE_INT_ARGB);
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
