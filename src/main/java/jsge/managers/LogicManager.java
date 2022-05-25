package jsge.managers;

import java.util.*;

import jsge.core.GameObject;
import jsge.util.Point;
import jsge.util.Utils.Layer;

public class LogicManager {
	public LogicManager() {
		return;
	}

	public void handleLogic(ArrayList<GameObject> GameObjectOnScene, double deltaTime) {
		for (GameObject gm : GameObjectOnScene) {
			gm.update(deltaTime);
		}
		checkAllColisions(GameObjectOnScene);
	}

	// otimização possivel usando quadrantes de tela e tags de colisao, olhar tb
	// para otimizar pois g1g2 = g2g1
	private void checkAllColisions(ArrayList<GameObject> GameObjectOnScene) {

		// Retirar isso porque causa error ao destruir objetos numa coleção durante a
		// iteração, usar FOR padrão e
		// retirar try catch
		try {
			for (GameObject gm1 : GameObjectOnScene) {
				for (GameObject gm2 : GameObjectOnScene) {
					if (gm1.getLayer() == Layer.GAMEOBJECT && gm2.getLayer() == Layer.GAMEOBJECT) {
						if (gm1.equals(gm2)) {
							continue;
						}
						if (Point.distance(gm2.getPosition(), gm1.getPosition()) <= gm2.getColliderRadius()
								+ gm1.getColliderRadius()) {
							gm1.onCollision(gm2);
						}
					} else {
						continue;
					}

				}
			}
		} catch (Exception e) {

		}

	}

}
