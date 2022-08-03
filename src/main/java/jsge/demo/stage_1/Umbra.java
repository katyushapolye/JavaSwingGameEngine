package jsge.demo.stage_1;

import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.AnimationClip;
import jsge.data.AudioClip;
import jsge.data.StateMachine;
import jsge.demo.stage_1.Bullet.Tag;
import jsge.utils.Point;
import jsge.utils.Timer;
import jsge.utils.Layers.Layer;

public class Umbra extends GameObject {
	
	static AnimationClip idle =  new AnimationClip("Idle","src/main/resources/Assets/BossSprite/BossSprite",0.5f,4,true,false);
	//idle.loadAnimationSpriteSheet("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.25f,4,true,false);
	static StateMachine<AnimationClip> sm = new StateMachine<AnimationClip>(false);

	Point initialPosition;
	Timer bulletTimer;
	
	private static AudioClip damageSound =  new AudioClip("src/main/resources/Sounds/damage00.wav");
	
	private boolean isMoving = false;
	
	private Point finalPosition = null;
	
	//set somethings as static, so we will save some memory
	int bossVelocity = 200;
	int bossHealth = 200000;
	
	int bossMaxHealth = 200000;
	
	boolean isAlive = true;
	
	//control flags
	
	
	boolean movingLeft = true;
	
	
	enum Phase{
		Phase1,
		Phase2,
		Phase3
	}
	
	private Phase currentPhase =  Phase.Phase1;
	
	
	public Umbra() {
		//init boss by moving it from the top of screen
		super("Umbra","src/main/resources/Assets/BossSprite/BossSprite_0.png", new Transform(220,-30),Layer.GAMEOBJECT,18);
		sm.addState("Idle",idle,null,null);
		this.transform.setScale(1.5,1.5);
		this.animationController = new AnimationController(sm,this.sprite);
		this.initialPosition =  transform.getPosition();
		
		this.setPath(new Point(220,60));

	}
	
	double getBossHealthPercentage() {
		
		return (double)bossHealth/(double)bossMaxHealth;
	}
	
	public void setPath(Point p) {
		this.isMoving = true;
		this.finalPosition = p;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	private void  moveToPosition() {
		if(this.transform.getPosition().X == this.finalPosition.X && this.transform.getPosition().Y == this.finalPosition.Y) {
			isMoving = false;
			finalPosition = null;
			return;
		}
		
		if(this.transform.getX() > finalPosition.X ) {
			this.transform.offsetPosition(-1,0);
			
		}
		if(this.transform.getY() > finalPosition.Y ) {
			this.transform.offsetPosition(0,-1);
			
		}
		if(this.transform.getX() < finalPosition.X ) {
			this.transform.offsetPosition(1,0);
			
		}
		if(this.transform.getY() < finalPosition.Y ) {
			this.transform.offsetPosition(0,1);
		}
		
		
	}
	
	
	private void changePhase(Phase phase) {
		if(phase == currentPhase) {
			return;
		}
		
		else if(phase == Phase.Phase1) {
			
			this.setPath(new Point(70,60));
		}
		
		else if(phase == Phase.Phase2) {
			
		}
		
		else if(phase ==  Phase.Phase3) {
			
		}
	}

	@Override
	public void onCollision(GameObject collision) {
		if(collision.getClass() == Bullet.class) {
			Bullet temp = ((Bullet)collision);
			if(temp.getTag() == Tag.Player) {
				
				bossHealth -= temp.bulletDamage;
				destroyGameObject(collision);
				damageSound.play();
				
				//better than to check at every update
				
				if(getBossHealthPercentage() >= 0.66) {
					changePhase(Phase.Phase1);
					
				}
				else if(getBossHealthPercentage() >= 0.33 && getBossHealthPercentage() < 0.66) {
					changePhase(Phase.Phase2);
					//attack pattern 2
				}
				
				else if(getBossHealthPercentage() >= 0 && getBossHealthPercentage() < 0.33) {
					changePhase(Phase.Phase3);
					//attack pattern 3
					
					
				}
				
				//check for death
				
				else {
					
					
				}
				
				
				
				
				}
			}
		}
	
	
	@Override
	public void update(double deltaTime) {
		if(isMoving) {
			moveToPosition();
			
		}
		
		switch (currentPhase) {
		case Phase1:{
			if(!this.isMoving) {
				if(this.getTransform().getX() == 70 && this.getTransform().getY() ==  60) {
					this.setPath(new Point(370,60));
				}
				
				else {
					this.setPath(new Point(70,60));
				}
			}
			
			
			break;
		}
		case Phase2:
			
			break;
			
		case Phase3:
			
			break;
		default:
			
			break;
		}
		
		super.update(deltaTime);
	}
}
