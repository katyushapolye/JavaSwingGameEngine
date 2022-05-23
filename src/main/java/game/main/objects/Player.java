package game.main.objects;
import java.awt.Color;
import java.awt.Graphics2D;


import game.main.core.GameKeyEvent;
import game.main.core.GameObject;
import game.main.core.GameKeyEvent.EventType;
import game.main.utils.Utils.Layer;

public class Player extends GameObject{
	private boolean isMovingUp = false;
	private boolean isMovingDown = false;
	private boolean isMovingLeft = false;
	private boolean isMovingRight = false;
	private boolean isRotatingLeft = false;
	private boolean isRotatingRight = false;
	

	
	private int playerVelocity = 400;
	
	private int rotatingVelocity = 200;
	
	public Player(String PathToImageFile,int X,int Y,int rotation) {
		super("PLAYER",PathToImageFile, X, Y,rotation,Layer.GAMEOBJECT,40);
		
		
	}
	
	@Override
	public void update(double deltaTime) {
		//System.out.println("X: " + this.X + "Y: " +this.Y);
		if(isMovingUp) {
			this.offsetPosition(0, (int)(deltaTime*-playerVelocity));
		}
		if(isMovingDown) {
			this.offsetPosition(0, (int)(deltaTime*playerVelocity));
		}
		if(isMovingLeft) {
			this.offsetPosition((int)(deltaTime*-playerVelocity),0);
		}
		if(isMovingRight) {
			this.offsetPosition((int)(deltaTime*playerVelocity),0);
		}
		if(isRotatingRight) {
			this.offsetRotation((int)(deltaTime*rotatingVelocity));
		}
		if(isRotatingLeft) {
			this.offsetRotation((int)(deltaTime*-rotatingVelocity));
		}
		//System.out.println("X: " + this.X + " Y: " + this.Y);
		
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(sprite == null) {
			System.out.println("Warning - Spriteless GameObject");
			return;
		}
		
		g.drawImage(sprite, super.applyTransform(), null);
		g.setColor(Color.GREEN);
		g.drawOval(this.X-colliderRadius,this.Y-colliderRadius, colliderRadius*2, colliderRadius*2); 
	}
	
	public void sendInput(GameKeyEvent e) {
		if(e.getEventType() == EventType.Pressed) {
			switch(e.getKeyCode()) {
			case 38:
				this.isMovingUp = true;
				break;
			case 40:
				this.isMovingDown = true;
				break;
			case 37:
				this.isMovingLeft = true;
				break;
			case 39:
				this.isMovingRight = true;
				break;
			case 88:
				this.playerVelocity = 200;
				break;
			case 81:
				this.isRotatingLeft = true;
				break;
			case 69:
				this.isRotatingRight = true;
				break;
				}
			}
			
		
		if(e.getEventType() == EventType.Released) {
			switch(e.getKeyCode()) {
			case 38:
				this.isMovingUp = false;
				break;
			case 40:
				this.isMovingDown = false;
				break;
			case 37:
				this.isMovingLeft = false;
				break;
			case 39:
				this.isMovingRight = false;
				break;
			case 88:
				this.playerVelocity = 400;
				break;
			case 81:
				this.isRotatingLeft = false;
				break;
			case 69:
				this.isRotatingRight = false;
				break;
				}
			}
	
		
	}
		
	


}
