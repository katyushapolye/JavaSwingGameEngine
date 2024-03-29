//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END

package jsge.demo.stage_1;
import jsge.data.AnimationClip;
import jsge.data.AudioClip;
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
	
	static AnimationClip idle =  new AnimationClip("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.5f,4,true,false);
	
	public AudioClip damageSound;
	
	
	//idle.loadAnimationSpriteSheet("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.25f,4,true,false);
	static StateMachine<AnimationClip> sm = new StateMachine<AnimationClip>(false);

	Point initialPosition;
	Point finalPosition;
	Timer bulletTimer;
	
	//set somethings as static, so we will save some memory
	int enemyVelocity = 200;
	int enemyHealth = 200;
	
	
	static public enum EnemyPattern{
		
		SideLinear,
		DownLinear,
	}
	
	private EnemyPattern pattern;
	Clock enemyClock = new Clock();
	public Enemy(String name, String pathToSprite, Transform transform, Layer initLayer,EnemyPattern pattern,float shootingCooldown,int enemySpeed) {
		super(name, pathToSprite, transform, initLayer,10);
		sm.addState("Idle",idle,null,null);
		damageSound =  new AudioClip("src/main/resources/Sounds/ok00.wav");
		this.animationController = new AnimationController(sm,this.sprite);
		this.pattern = pattern;
		this.initialPosition =  transform.getPosition();
		
		this.enemyVelocity = enemySpeed;
		bulletTimer = new Timer(()-> shootPattern(),shootingCooldown,true);
		
		//Calculate final position for each pattern
		switch (pattern) {
		case DownLinear:
			this.finalPosition =  new Point(this.initialPosition.X,this.initialPosition.Y+160);
			break;
		case SideLinear:
			this.finalPosition =  new Point(this.initialPosition.X +800,this.initialPosition.Y);
			break;

		default:
			break;
		}
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onDestroy() {
		//System.out.println("AN ENEMY HAS BEEN DETROYED");
		bulletTimer.destroyTimer();
		super.onDestroy();
	}
	
	@Override
	public void update(double deltaTime) {
		//Needs improvement, glitches out on certain cases, but should work for simple patterns;
		switch(pattern) {
		case DownLinear:
			this.transform.offsetPosition(0, (int)(deltaTime*enemyVelocity));
			if(this.transform.getY() >= this.finalPosition.Y) {
				this.finalPosition.Y = -110;
				this.enemyVelocity = 0;
				new Timer(()->changeDirection(),4,false);
				
			}
			break;
		case SideLinear:
			this.transform.offsetPosition((int)(deltaTime*enemyVelocity),0);
			
			break;
		default:
			break;
		}
		
		if(this.transform.getX() > 500 || this.transform.getY() > 600 || this.transform.getY() < -100) {
			destroyGameObject(this);
		}
	//Destoy this if out of bounds
	super.update(deltaTime);	
	}
	
	@Override
	public void onCollision(GameObject collision) {
		if(collision.getClass() == Bullet.class) {
			Bullet temp = ((Bullet)collision);
			if(temp.getTag() == Tag.Player) {
				this.enemyHealth -= temp.bulletDamage;
				if(isStillAlive()) {
					destroyGameObject(collision);
					return;
				}
				else {
					destroyGameObject(collision);
					PlayerData.addScore(100);
					damageSound.play();
					Stage_1_Scene.updatePlayerScoreUI();
					GameObject.destroyGameObject(this);
					//send score to plyaer
				}

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
	
	private void changeDirection() {
		this.enemyVelocity = -80;
	}
	private void shootPattern() {
		
		switch(pattern) {
		case DownLinear:
			for(int i = 240;i<=300;i+=30 ) {
				new Bullet(Tag.Enemy,this.transform.getX(),this.transform.getY(),i,250);
			}
			break;
		case SideLinear:
			new Bullet(Tag.Enemy,this.transform.getX(),this.transform.getY(),270,100); 
			break;
		default:
			break;
		}
	}

}

