package jsge.managers;

import java.util.*;

import jsge.core.Game;
import jsge.core.GameObject;
import jsge.utils.Point;
import jsge.utils.GameState.GameStates;
import jsge.utils.Layers.Layer;

public class LogicManager {
	public LogicManager() {
		return;
	}

	public void handleLogic(ArrayList<GameObject> GameObjectOnScene, double deltaTime) {
		//Retirar dps loop foreach e colocar i para evitar bugs
		try {
			for (GameObject gm : GameObjectOnScene) {
				gm.update(deltaTime);
			}
		}
		catch(Exception e) {
			
		}
		if(Game.gameStateManager.getCurrentGameState() == GameStates.Running) {
			checkAllColisions(GameObjectOnScene);
		}
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
						if (gm1.getCollider().isColliding(gm2.getCollider())) {
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
