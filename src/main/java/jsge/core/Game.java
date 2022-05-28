package jsge.core;

import jsge.components.Transform;
import jsge.demo.Player;
import jsge.managers.InputManager;
import jsge.managers.CollisionManager;
import jsge.utils.Clock;
import jsge.utils.Layers.Layer;

public class Game {
	final long SECOND_IN_NANO = 1000000000l;
	final long FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO / FPS;

	private GameRendererWindow gameWindow = null;
	private InputManager inputHandler = null;
	private CollisionManager collisionManager = null;

	public Game(int ScreenWidth, int ScreenHeight) {
		gameWindow = new GameRendererWindow(ScreenWidth, ScreenHeight);
		inputHandler = gameWindow.getInputManager();
		collisionManager = new CollisionManager();

	}
	

	public void run() {
		// Scene Loading
		new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png",new Transform(320,240),Layer.GAMEOBJECT,50,50);
		Player player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 30, 33, 0);
	
		
		
		jsge.utils.Clock gameClock = new Clock();
		while (!(player.isPlayerDead())) {
			if (gameClock.getElapsedTimeInNanoSeconds() >= FRAME_TARGET_TIME) {
				double deltaTime = gameClock.resetClock();
				//System.out.println("FPS: " + (1.f/ (float) deltaTime));
				// Pool Input and send to all gameobjects that receive it
				while (inputHandler.isPoolingDone()) {
					GameKeyEvent e = inputHandler.poolEvent();
					for (GameObject go : GameObject.getAllInputReceiverGameObjects()) {
						go.receiveInput(e);
					}

				}

				// Game Logic
				collisionManager.handleLogic(GameObject.getAllGameObjects(), deltaTime);

				// Drawn and then Display
				
				
			
	

				

				gameWindow.renderGameObjects(GameObject.getAllGameObjects());
				gameWindow.display();

			} else {
				try {
					Thread.sleep((FRAME_TARGET_TIME - gameClock.getElapsedTimeInNanoSeconds()) / 1000000);
				} catch (Exception e) {

				}

			}

		}

	}
}
	
	
