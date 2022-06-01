package jsge.demo.stage_1;

import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.utils.Clock;
import jsge.utils.Layers.Layer;

public class Enemy extends GameObject{
	int a = 3;

	Clock enemyClock = new Clock();
	public Enemy(String name, String pathToSprite, Transform transform, Layer initLayer, int colliderRadius) {
		super(name, pathToSprite, transform, initLayer, colliderRadius);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(double deltaTime) {
		
		
		this.transform.offsetPosition(a,0);
		if(this.transform.getPosition().X > 384+33 -16 ) a = -a;
		if(this.transform.getPosition().X<30) a = 3;
		
		super.update(deltaTime);
	}
	
	@Override
	public void onCollision(GameObject collision) {
		GameObject.destroyGameObject(this);
		super.onCollision(collision);
	}

}
