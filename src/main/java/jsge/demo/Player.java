package jsge.demo;
import jsge.components.AnimationController;
import jsge.components.Transform;

import java.awt.Graphics2D;

import jsge.core.GameKeyEvent;
import jsge.core.GameObject;
import jsge.core.GameKeyEvent.EventType;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.utils.Layers.Layer;

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
		super("PLAYER",PathToImageFile, new Transform(240,240),Layer.GAMEOBJECT,10,true);
		
		//Player State Machine Initialization
		AnimationClip idleAnimation =  new AnimationClip();
		idleAnimation.loadAnimationSpriteSheet("Marisa_Idle","src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle",
									0.25f,4,true);
		AnimationClip leftAnimation = new AnimationClip();
		leftAnimation.loadAnimationSpriteSheet("Marisa_Left","src/main/resources/Assets/Marisa/Marisa_Moving_Left_Animation/Marisa_Left",
									0.23f,3,true);
		
		AnimationClip rightAnimation = new AnimationClip();
		rightAnimation.loadAnimationSpriteSheet("Marisa_Right","src/main/resources/Assets/Marisa/Marisa_Moving_Right_Animation/Marisa_Right",
									0.23f,3,true);

		
		StateMachine<AnimationClip> sm =  new StateMachine<AnimationClip>(false);
		sm.addState("Marisa_Idle",idleAnimation,null,null);
		sm.addState("Marisa_Moving_Left",leftAnimation,"Marisa_Idle","Left");
		sm.addState("Marisa_Moving_Right", rightAnimation, "Marisa_Idle","Right");
		sm.setDefaultState("Marisa_Idle");
		
		sm.addTransition("Marisa_Moving_Left","Marisa_Moving_Right","Right");
		
		sm.addTransition("Marisa_Moving_Right","Marisa_Moving_Left","Left");
		
		this.animationController =  new AnimationController(sm ,this.sprite);
		
		
	}
	@Override
	public void onCollision(GameObject collision) {
		GameObject.destroyGameObject(this);
	}
	@Override
	public void update(double deltaTime) {
		this.animationController.internalUpdate();
		//System.out.println("X: " + this.X + "Y: " +this.Y);
		if(isMovingUp) {
			this.transform.offsetPosition(0, (int)(deltaTime*-playerVelocity));
		}
		if(isMovingDown) {
			this.transform.offsetPosition(0, (int)(deltaTime*playerVelocity));
		}
		if(isMovingLeft) {
			this.transform.offsetPosition((int)(deltaTime*-playerVelocity),0);
		}
		if(isMovingRight) {
			this.transform.offsetPosition((int)(deltaTime*playerVelocity),0);
		}
		if(isRotatingRight) {
			this.transform.offsetRotation((int)(deltaTime*rotatingVelocity));
		}
		if(isRotatingLeft) {
			this.transform.offsetRotation((int)(deltaTime*-rotatingVelocity));
		}
		checkAnimationState();
		checkPlayerBounds();
		
		super.update(deltaTime);
				
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}
	
	
	public boolean isPlayerDead() {
		return this.isPlayerDead;
	}
	@Override
	public void receiveInput(GameKeyEvent e) {
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
			case 16:
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
			case 16:
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
	
	
	private void checkAnimationState() {
		if((isMovingLeft && isMovingRight) || isMovingLeft == false && isMovingRight == false ) {
			this.animationController.resetState();
		}
		else if(isMovingLeft) {
			this.animationController.sendTrigger("Left");	
		}
		else if(isMovingRight) {
			this.animationController.sendTrigger("Right");
		}
		
	}
	
	private void checkPlayerBounds() {
		//ainda estou testando com as screen bounds
		if(this.transform.getX() >= 384+33 -16) {
			this.transform.setX(384+33 -16);
		}
		if(this.transform.getX() <= 33+16) {
			this.transform.setX(33+16);
		}
		if(this.transform.getY() <= 16+15) {
			this.transform.setY(16+15);
		}
		if(this.transform.getY() >= 448+16-15) {
			this.transform.setY(448+16-15);
		}
	}
		
	


}
