package jsge.demo.stage_1;

import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.utils.Point;
import jsge.utils.Timer;
import jsge.utils.Layers.Layer;

public class Umbra extends GameObject {
	
	static AnimationClip idle =  new AnimationClip("Idle","src/main/resources/Assets/BossSprite/BossSprite",0.5f,4,true,false);
	//idle.loadAnimationSpriteSheet("Idle","src/main/resources/Assets/EarthSpirit/EarthSpirit",0.25f,4,true,false);
	static StateMachine<AnimationClip> sm = new StateMachine<AnimationClip>(false);

	Point initialPosition;
	Point finalPosition;
	Timer bulletTimer;
	
	//set somethings as static, so we will save some memory
	int bossVelocity = 200;
	int bossHealth = 20000;
	
	
	public Umbra() {
		//init boss by moving it from the top of screen
		super("Umbra","src/main/resources/Assets/BossSprite/BossSprite_0.png", new Transform(240,240),Layer.GAMEOBJECT,18);
		sm.addState("Idle",idle,null,null);
		this.transform.setScale(1.5,1.5);
		this.animationController = new AnimationController(sm,this.sprite);
		this.initialPosition =  transform.getPosition();

	}
}
