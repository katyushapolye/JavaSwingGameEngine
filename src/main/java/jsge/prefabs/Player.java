package jsge.prefabs;
import jsge.components.AnimationController;
import java.awt.Color;
import java.awt.Graphics2D;

import jsge.core.GameKeyEvent;
import jsge.core.GameObject;
import jsge.core.GameKeyEvent.EventType;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.util.Utils.Layer;

public class Player extends GameObject{
	private boolean isMovingUp = false;
	private boolean isMovingDown = false;
	private boolean isMovingLeft = false;
	private boolean isMovingRight = false;
	private boolean isRotatingLeft = false;
	private boolean isRotatingRight = false;
	
	private boolean isPlayerDead = false;
	

	
	private int playerVelocity = 420;
	
	private int rotatingVelocity = 200;
	
	public Player(String PathToImageFile,int X,int Y,int rotation) {
		super("PLAYER",PathToImageFile, X, Y,rotation,Layer.GAMEOBJECT,5);
		
		//Player State Machine Initialization
		AnimationClip idleAnimation =  new AnimationClip();
		idleAnimation.loadAnimationSpriteSheet("Marisa_Idle","src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle",
									0.25f,4,true);
		AnimationClip leftAnimation = new AnimationClip();
		leftAnimation.loadAnimationSpriteSheet("Marisa_Left","src/main/resources/Assets/Marisa/Marisa_Moving_Left_Animation/Marisa_Left",
									0.25f,3,true);
		
		AnimationClip rightAnimation = new AnimationClip();
		rightAnimation.loadAnimationSpriteSheet("Marisa_Right","src/main/resources/Assets/Marisa/Marisa_Moving_Right_Animation/Marisa_Right",
									0.25f,3,true);

		
		//BUG GRAVE - INVESTIGAR
		StateMachine<AnimationClip> sm =  new StateMachine<AnimationClip>();
		sm.addState("Marisa_Idle",idleAnimation,null,null,false);
		sm.addState("Marisa_Moving_Left",leftAnimation,"Marisa_Idle","Left",false);
		sm.addState("Marisa_Moving_Right", rightAnimation, "Marisa_Idle","Right", false);
		sm.setDefaultState("Marisa_Idle");
		
		sm.dumpStateMachineOnConsole();
		
		this.animationController =  new AnimationController(sm ,this);
		
		
	}
	@Override
	public void onCollision(GameObject collision) {
		GameObject.destroyGameObject(this);
	}
	@Override
	public void update(double deltaTime) {
		this.animationController.updateAnimationController();
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
		//384x448
		//System.out.println("X: " + this.X + " Y: " + this.Y);
		checkPlayerBounds();
		
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(sprite == null) {
			//System.out.println("Warning - Spriteless GameObject");
			return;
		}
		
		g.drawImage(sprite, super.applyTransform(), null);
		g.setColor(Color.GREEN);
		g.drawOval(this.X-colliderRadius,this.Y-colliderRadius, colliderRadius*2, colliderRadius*2); 
	}
	
	
	public boolean isPlayerDead() {
		return this.isPlayerDead;
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
				this.animationController.sendTrigger("Left");
				break;
			case 39:
				this.isMovingRight = true;
				this.animationController.sendTrigger("Right");

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
				this.animationController.resetState();
				break;
			case 39:
				this.isMovingRight = false;
				this.animationController.resetState();
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
	
	private void checkPlayerBounds() {
		//ainda estou testando com as screen bounds
		if(this.X >= 384+33 -16) {
			this.X = 384+33 -16;
		}
		if(this.X <= 33+16) {
			this.X = 33+16;
		}
		if(this.Y <= 16+15) {
			this.Y = 16+15;
		}
		if(this.Y >= 448+16-15) {
			this.Y = 448+16-15;
		}
	}
		
	


}
