package jsge.components;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {
	BufferedImage spriteTexture;
	int width;
	int height;
	byte alpha;
	
	public Sprite() {
		this.width = -1;
		this.height = -1;
		this.alpha = (byte) 255;
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
	public void setAlpha(double alphaPercentage) {       
	    for (int x = 0; x < this.spriteTexture.getWidth(); x++) {          
        for (int y = 0; y < this.spriteTexture.getHeight(); y++) {
                //
            int argb = this.spriteTexture.getRGB(x, y); //always returns TYPE_INT_ARGB
            int alpha = (argb >> 24) & 0xff;  //isolate alpha

            alpha *= alphaPercentage; //similar distortion to tape saturation (has scrunching effect, eliminates clipping)
            alpha &= 0xff;      //keeps alpha in 0-255 range

            argb &= 0x00ffffff; //remove old alpha info
            argb |= (alpha << 24);  //add new alpha info
            this.spriteTexture.setRGB(x, y, argb);            
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
