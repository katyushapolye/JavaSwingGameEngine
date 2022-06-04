package jsge.demo.stage_1;

import jsge.components.Collider;
import jsge.components.Sprite;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Layers.Layer;

public class Bullet extends GameObject{
	
	static long BULLET_ID = 0;
	
	int bulletSpeed = 1500;
	
	static enum Tag{
		Enemy,
		Player
	}
	private Tag bulletTag;
	public Bullet(Tag bulletTag,int initX,int initY,int degrees,int bulletSpeed) {
		super("bullet_"+BULLET_ID,new Transform(initX,initY), Layer.GAMEOBJECT);
		this.bulletTag = bulletTag;
		this.bulletSpeed = bulletSpeed;
		this.transform.setRotation(degrees);
		if(this.bulletTag == Tag.Enemy)this.setSpriteComponent(new Sprite("src/main/resources/Assets/EarthSpiri"
				+ ""
				+ "t/EarthSpirit_Bullet.png"));
		else {
			this.setSpriteComponent(new Sprite("src/main/resources/Assets/Marisa/Marisa_Basic_Bullet.png"));
		}
		this.collider =  new Collider(5,this.transform);
		BULLET_ID++;
		
		
	}
	
	@Override
	public void update(double deltaTime) {
		 //trignomoetria para angulo
		int xComponent = (int)((this.bulletSpeed * Math.cos(Math.toRadians(this.transform.getRotation()))));
		int yComponent = (int)((this.bulletSpeed * Math.sin(Math.toRadians(this.transform.getRotation()))));
		
		this.transform.offsetPosition((int)(xComponent*deltaTime), (int)(-yComponent*deltaTime));
		
		if(this.transform.getY() >= 640 || this.transform.getY()<=0  || this.transform.getX() >= 400 || this.transform.getX() <= 0) {
			GameObject.destroyGameObject(this);
		}
		super.update(deltaTime);
	}
	
	public Tag getTag() {
		return this.bulletTag;
	}

	
}
