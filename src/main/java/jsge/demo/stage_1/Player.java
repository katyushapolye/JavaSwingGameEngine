package jsge.demo.stage_1;
import jsge.components.AnimationController;
import jsge.components.Transform;

import jsge.data.AudioClip;
import java.awt.Graphics2D;

import jsge.core.Game;
import jsge.core.GameKeyEvent;
import jsge.core.GameObject;
import jsge.core.GameKeyEvent.EventType;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.demo.stage_1.Bullet.Tag;
import jsge.utils.Clock;
import jsge.utils.GameState.GameStates;
import jsge.utils.Layers.Layer;

public class Player extends GameObject{
	private boolean isMovingUp = false;
	private boolean isMovingDown = false;
	private boolean isMovingLeft = false;
	private boolean isMovingRight = false;
	private boolean isRotatingLeft = false;
	private boolean isRotatingRight = false;
	
	private boolean isPlayerDead = false;
	
	private boolean isShooting = false;
	private double shotCoolDownTime = 0.075d;
	private Clock playerShotClock =  new Clock();
	
	
	
	AudioClip debug;
	AudioClip debug2;
	

	
	private int playerVelocity = 280;
	
	private int rotatingVelocity = 160;
	
	public Player(String PathToImageFile,int X,int Y,int rotation) {
		super("PLAYER",PathToImageFile, new Transform(240,240),Layer.GAMEOBJECT,4,true);
		
		
		debug = new AudioClip("src/main/resources/Sounds/plst00.wav");
		debug2 = new AudioClip("src/main/resources/Sounds/damage00.wav");
		//check directory bug in windows
		System.out.println("Player: Player Parent GameObject Created");
		AnimationClip idleAnimation =  new AnimationClip();
		idleAnimation.loadAnimationSpriteSheet("Marisa_Idle","src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle",
									0.33f,4,true,false);
		AnimationClip leftAnimation = new AnimationClip();
		leftAnimation.loadAnimationSpriteSheet("Marisa_Left","src/main/resources/Assets/Marisa/Marisa_Moving_Left_Animation/Marisa_Left",
									0.23f,3,true,true);
		
		AnimationClip rightAnimation = new AnimationClip();
		rightAnimation.loadAnimationSpriteSheet("Marisa_Right","src/main/resources/Assets/Marisa/Marisa_Moving_Right_Animation/Marisa_Right",
									0.23f,3,true,false);
		
		System.out.println("Player: Player Animations Loaded");

		
		StateMachine<AnimationClip> sm =  new StateMachine<AnimationClip>(false);
		sm.addState("Marisa_Idle",idleAnimation,null,null);
		sm.addState("Marisa_Moving_Left",leftAnimation,"Marisa_Idle","Left");
		sm.addState("Marisa_Moving_Right", rightAnimation, "Marisa_Idle","Right");
		sm.setDefaultState("Marisa_Idle");
		
		sm.addTransition("Marisa_Moving_Left","Marisa_Moving_Right","Right");
		sm.addTransition("Marisa_Moving_Right","Marisa_Moving_Left","Left");
		
		System.out.println("Player: Player StateMachine Created");
		
		this.animationController =  new AnimationController(sm ,this.sprite);
		
		System.out.println("Player: Player AnimationController Created");
		
		System.out.println("Player: Player Created");

		
		
	}
	@Override
	public void onCollision(GameObject collision) {
		if(collision.getClass() ==  Enemy.class) {
			
			isPlayerDead = true;
			destroyGameObject(this);
			return;
		}
		if(collision.getClass() == Bullet.class) {
			Bullet temp = (Bullet)collision;
			if(temp.getTag() == Tag.Enemy) {
				isPlayerDead = true;
				destroyGameObject(this);
			}
		}
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
		if(isShooting) {
			playerShoot();
		}
		
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
		if(Game.getGameStateManager().getCurrentGameState() == GameStates.Running) {
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
			case 27:
				Game.getGameStateManager().changeGameState(GameStates.Paused);
				break;
			case 90:
				this.isShooting = true;
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
			case 90:
				this.isShooting = false;
				}
			}
		}
		else {
			if(e.getEventType() == EventType.Pressed) {
				switch(e.getKeyCode()) {
				case 27:
					Game.getGameStateManager().changeGameState(GameStates.Running);
					break;
			
		
				}
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
	
	private void playerShoot() {
		//check player power
		if(playerShotClock.getElapsedTimeInSeconds() >= shotCoolDownTime) {
			new Bullet(Bullet.Tag.Player,this.transform.getX(),this.transform.getY()-15,90,1500);
			debug.play();
			//debug.playTest();
			playerShotClock.resetClock();
		}
	}
		
	


}
