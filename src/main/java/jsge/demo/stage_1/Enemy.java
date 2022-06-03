package jsge.demo.stage_1;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Clock;
import jsge.utils.Layers.Layer;
import jsge.utils.Point;

public class Enemy extends GameObject{
	
	AnimationClip idle =  new AnimationClip("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.5f,4,true,false);
	//idle.loadAnimationSpriteSheet("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.25f,4,true,false);
	StateMachine<AnimationClip> sm = new StateMachine<AnimationClip>(true);

	Point initialPosition;
	Point finalPosition;
	
	int enemyVelocity = 200;
	int enemyHealth = 100;
	
	
	static public enum EnemyPattern{
		Straight,
		Exponential,
		Linear,
	}
	
	private EnemyPattern pattern;
	Clock enemyClock = new Clock();
	public Enemy(String name, String pathToSprite, Transform transform, Layer initLayer, int colliderRadius,EnemyPattern pattern) {
		super(name, pathToSprite, transform, initLayer, colliderRadius);
		sm.addState("Idle",idle,null,null);
		this.animationController = new AnimationController(sm,this.sprite);
		this.pattern = pattern;
		this.initialPosition =  transform.getPosition();
		
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
		GameObject.destroyGameObject(this);
		super.onCollision(collision);
	}
	
	
	//Important for scene checking the wave of enemies
	public boolean isStillAlive() {
		if(this.enemyHealth <=0) {
			return false;
			
		}
		return true;
	}

}
