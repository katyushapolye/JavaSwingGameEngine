package jsge.managers;

import java.util.*;

import jsge.core.Game;
import jsge.core.GameObject;
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
		if(Game.getGameStateManager().getCurrentGameState() == GameStates.Running) {
			checkAllColisions(GameObjectOnScene);
		}
	}

	// otimização possivel usando quadrantes de tela e tags de colisao, olhar tb <- prova a indução de que precisamos chegar sempre n-1 na passada n, sendo n, n-1, n-2 etc 
	//peça pro raphael explicar
	// para otimizar pois g1g2 = g2g1
	private void checkAllColisions(ArrayList<GameObject> GameObjectOnScene) {
		// analisar se o trycatch é realmente necessario, objetos se destruindo e o indice dando segfault pois esta altersndo o obejto durante a iteração
		try {
			for(int i = 0;i<GameObjectOnScene.size();i++) {
				for(int j = 0;j<GameObjectOnScene.size();j++) {
					if (GameObjectOnScene.get(i).getLayer() == Layer.GAMEOBJECT && GameObjectOnScene.get(j).getLayer() == Layer.GAMEOBJECT) {
						if (GameObjectOnScene.get(i).equals(GameObjectOnScene.get(j))) {
							continue;
						}
						if (GameObjectOnScene.get(i).getCollider().isColliding(GameObjectOnScene.get(j).getCollider())) {
							GameObjectOnScene.get(i).onCollision(GameObjectOnScene.get(j));
						}
					} else {
						continue;
					}
				}
				
			}
			
		}
		catch(Exception e) {
			
		}
		

	}
	}

	
	
