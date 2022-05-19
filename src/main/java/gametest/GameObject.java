package gametest;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;


public class GameObject {
	private Image sprite = null;
	private int X = 0;
	private int Y = 0;
	
	public GameObject(String pathToSpriteImage,int initX,int initY) {
		this.X = initX;
		this.Y = initY;
		ImageIcon ii = new ImageIcon(pathToSpriteImage);
		sprite = ii.getImage();
		System.out.println(sprite.getHeight(null));
		
	}
	
	public void draw(Graphics g) {
		if(sprite == null) {
			System.out.println("Warning - Spriteless GameObject");
			return;
		}
		g.drawImage(this.sprite,X,Y,null);
	}
	
	public void updatePosition(int newX,int newY) {
		this.X  +=  newX;
		this.Y +=  newY;
	}
	

}
