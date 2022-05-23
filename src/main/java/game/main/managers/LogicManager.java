package game.main.managers;

import java.util.*;

import game.main.core.GameObject;
import game.main.utils.Point;
import game.main.utils.Utils.Layer;

public class LogicManager {
	public LogicManager() {
		return;
	}

	public void handleLogic(ArrayList<GameObject> GameObjectOnScene,double deltaTime) {
		for(GameObject gm : GameObjectOnScene) {
			gm.update(deltaTime);
		}
		checkAllColisions(GameObjectOnScene);
	}
	
	
	//otimização possivel usando quadrantes de tela e tags de colisao, olhar tb para otimizar pois g1g2 = g2g1
	private void checkAllColisions(ArrayList<GameObject> GameObjectOnScene) {
		for(GameObject gm1 : GameObjectOnScene) {
			for(GameObject gm2: GameObjectOnScene) {
				if(gm1.getLayer() == Layer.BACKGROUND || gm1.getLayer() ==  Layer.UI || gm2.getLayer() == Layer.BACKGROUND) {
					continue;
				}
				if(gm1.equals(gm2)) {
					continue;
				}
				if(Point.distance(gm2.getPosition(), gm1.getPosition()) <= gm2.getColliderRadius()+gm1.getColliderRadius()) {
					System.out.println("COLISION DETECTED BETWEEN " + gm1.getName() + " AND " +gm2.getName());
				}
				
				
			}
			
		}
		
	}

}
