package jsge.demo.stage_1;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.demo.stage_1.Bullet.Tag;
import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Clock;
import jsge.utils.Layers.Layer;
import jsge.utils.Point;
import jsge.utils.Timer;

public class Enemy extends GameObject{
	
	AnimationClip idle =  new AnimationClip("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.5f,4,true,false);
	//idle.loadAnimationSpriteSheet("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.25f,4,true,false);
	StateMachine<AnimationClip> sm = new StateMachine<AnimationClip>(true);

	Point initialPosition;
	Point finalPosition;
	Timer<Void> bulletTimer;
	
	int enemyVelocity = 200;
	int enemyHealth = 100;
	
	
	static public enum EnemyPattern{
		Straight,
		Exponential,
		Linear,
	}
	
	private EnemyPattern pattern;
	Clock enemyClock = new Clock();
	public Enemy(String name, String pathToSprite, Transform transform, Layer initLayer,EnemyPattern pattern) {
		super(name, pathToSprite, transform, initLayer,10);
		sm.addState("Idle",idle,null,null);
		this.animationController = new AnimationController(sm,this.sprite);
		this.pattern = pattern;
		this.initialPosition =  transform.getPosition();
		
		bulletTimer = new Timer<Void>((Void)-> shootPattern(),null,0.8f,true);
		
		//Calculate final position for each pattern
		switch (pattern) {
		case Linear:
			this.finalPosition =  new Point(this.initialPosition.X,this.initialPosition.Y+160);
			break;

		default:
			break;
		}
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDestroy() {
		try {
			bulletTimer.destroyTimer();
		}
		catch(Exception e) {
			
		}
		super.onDestroy();
	}
	
	@Override
	public void update(double deltaTime) {
		//Needs improvement, glitches out on certain cases, but should work for simple patterns;
		if(this.transform.getPosition().X >=this.finalPosition.X && this.transform.getPosition().Y >= this.finalPosition.Y) {
			super.update(deltaTime);
			return;
		}
		switch(pattern) {
		case Linear:
			this.transform.offsetPosition(0, (int)(deltaTime*enemyVelocity));
		default:
			break;
		}
		
		
	super.update(deltaTime);	
	}
	
	@Override
	public void onCollision(GameObject collision) {
		if(collision.getClass() == Bullet.class) {
			Bullet temp = ((Bullet)collision);
			if(temp.getTag() == Tag.Player) {
				bulletTimer.destroyTimer();
				bulletTimer = null;
				GameObject.destroyGameObject(this);
				
				//Send Score to PlayerData -> 100
			}
		}
	}
	
	
	//Important for scene checking the wave of enemies
	public boolean isStillAlive() {
		if(this.enemyHealth <=0) {
			return false;
			
		}
		return true;
	}
	
	private Void shootPattern() {
		
		switch(pattern) {
		case Linear:
			for(int i = 240;i<=320;i+=30 ) {
				new Bullet(Tag.Enemy,this.transform.getX(),this.transform.getY(),i,120);
			}
			
			
			break;
		default:
			break;
		}
	
	return null;
	}

}

