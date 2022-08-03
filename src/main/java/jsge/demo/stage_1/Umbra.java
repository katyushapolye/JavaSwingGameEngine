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
	
	Timer bulletTimer2;
	
	private static AudioClip damageSound =  new AudioClip("src/main/resources/Sounds/damage00.wav");
	private static AudioClip stageChangeSound =  new AudioClip("src/main/resources/Sounds/tan00.wav");
	
	private static AudioClip destroySound =  new AudioClip("src/main/resources/Sounds/cat00.wav");
	
	private boolean isMoving = false;
	
	private Point finalPosition = null;
	
	//set somethings as static, so we will save some memory
	int bossVelocity = 200;
	int bossHealth = 100000;
	
	int bossMaxHealth = 100000;
	
	boolean isAlive = true;
	
	//control flags
	
	
	boolean movingLeft = true;
	
	
	enum Phase{
		Phase0,
		Phase1,
		Phase2,
		Phase3
	}
	
	private Phase currentPhase =  Phase.Phase0;
	
	
	public Umbra() {
		//init boss by moving it from the top of screen
		super("Umbra","src/main/resources/Assets/BossSprite/BossSprite_0.png", new Transform(220,-30),Layer.GAMEOBJECT,18);
		sm.addState("Idle",idle,null,null);
		this.transform.setScale(1.5,1.5);
		this.animationController = new AnimationController(sm,this.sprite);
		this.initialPosition =  transform.getPosition();
		
		this.setPath(new Point(220,60));
		
		System.out.println("Umbra:  Boss Created, Setting Inital Path");
		new Timer(()->changePhase(Phase.Phase1),2,false);

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
			
			System.out.println("Umbra:  Entering Phase 1");
			bulletTimer = new Timer(()->shootPattern1(),0.5,true);
			bulletTimer2 = null;
			this.setPath(new Point(70,60));
			this.currentPhase = Phase.Phase1;
		}
		
		else if(phase == Phase.Phase2) {
			
			//play audio
			System.out.println("Umbra:  Entering Phase 2");
			stageChangeSound.play();
			PlayerData.addScore(5000);
			bulletTimer.destroyTimer();
			bulletTimer = new Timer(()->shootPattern1(),0.75,true);
			bulletTimer2 = new Timer(()->shootPattern2(180),0.50,true);
			this.setPath(new Point(70,60));
			this.movingLeft = false;

			this.currentPhase = Phase.Phase2;
			
			
		}
		
		else if(phase ==  Phase.Phase3) {
			PlayerData.addScore(10000);
			System.out.println("Umbra:  Entering Phase 3");
			stageChangeSound.play();
			bulletTimer.destroyTimer();
			bulletTimer2.destroyTimer();
			bulletTimer = new Timer(()->shootPattern1(),0.75,true);
			bulletTimer2 = new Timer(()->shootPattern2(380),0.50,true);
			this.setPath(new Point(70,60));
			this.movingLeft = false;

			this.currentPhase = Phase.Phase3;
			
		}
	}
	
	
	private void shootPattern1() {
		for(int i = 240;i<=300;i+=30 ) {
			new Bullet(Tag.Enemy,this.transform.getX(),this.transform.getY(),i,250);
		}
		
	}
	
	


	
	
	private void shootPattern2(int velocity) {
		new Bullet(Tag.Enemy,this.transform.getX()-10,this.transform.getY(),270,velocity);
		new Bullet(Tag.Enemy,this.transform.getX(),this.transform.getY(),270,velocity);
		new Bullet(Tag.Enemy,this.transform.getX()+10,this.transform.getY(),270,velocity);
	}

	@Override
	public void onCollision(GameObject collision) {
		if(collision.getClass() == Bullet.class) {
			Bullet temp = ((Bullet)collision);
			if(temp.getTag() == Tag.Player) {
				
				bossHealth -= temp.bulletDamage;
				destroyGameObject(collision);
				damageSound.play();
				
				PlayerData.addScore(10);
				
				//better than to check at every update
				
				if(getBossHealthPercentage() >= 0.66) {
					changePhase(Phase.Phase1);
					return;
					
				}
				else if(getBossHealthPercentage() >= 0.33 && getBossHealthPercentage() < 0.66) {
					changePhase(Phase.Phase2);
					return;
					//attack pattern 2
				}
				
				else if(getBossHealthPercentage() > 0 && getBossHealthPercentage() < 0.33) {
					changePhase(Phase.Phase3);
					return;
					//attack pattern 3
					
					
				}
				
				//check for death
				
			
							
				}
			}
		}
	
	@Override
	public void onDestroy() {
		if(bulletTimer != null) {
			bulletTimer.destroyTimer();
			bulletTimer = null;
		}
		if(bulletTimer2 != null) {
			bulletTimer2.destroyTimer();
			bulletTimer2 = null;
		}
		
		destroySound.play();
		super.onDestroy();
	}
	
	@Override
	public void update(double deltaTime) {
		if(isMoving) {
			moveToPosition();
			
		}
		if(bossHealth <=0) {
			this.isAlive = false;
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
			//debug
			if(!this.isMoving) {
				if(this.transform.getPosition().Y == 100 && this.transform.getPosition().X == 220) {
					if(this.movingLeft) {
						this.setPath(new Point(370,60));
						
					}
					
					else {
						this.setPath(new Point(70,60));
					}
					
				}
				else if(this.getTransform().getX() == 70 && this.getTransform().getY() ==  60) {
					this.movingLeft = true;
					this.setPath(new Point(220,100));
					
				}
				else if(this.getTransform().getX() == 370 && this.getTransform().getY() ==  60) {
					this.movingLeft = false;
					this.setPath(new Point(220,100));
					
				}
				
			}		
			break;
			
			//debug
		case Phase3:
			if(!this.isMoving) {
				if(this.transform.getPosition().Y == 200 && this.transform.getPosition().X == 220) {
					if(this.movingLeft) {
						this.setPath(new Point(370,60));
						
					}
					else {
						this.setPath(new Point(70,60));
					}
					
				}
				else if(this.getTransform().getX() == 70 && this.getTransform().getY() ==  60) {
					this.movingLeft = true;
					this.setPath(new Point(220,200));
					
				}
				else if(this.getTransform().getX() == 370 && this.getTransform().getY() ==  60) {
					this.movingLeft = false;
					this.setPath(new Point(220,200));
					
				}
			}		
			break;
			

		default:
			
			break;
		}
		
		super.update(deltaTime);
	}
}
