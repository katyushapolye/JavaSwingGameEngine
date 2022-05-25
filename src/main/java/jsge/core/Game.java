package jsge.core;

import jsge.util.Clock;
import jsge.managers.InputManager;
import jsge.managers.LogicManager;
import jsge.prefabs.Player;
import jsge.util.Utils.Layer;

public class Game {
	final long SECOND_IN_NANO = 1000000000l;
	final long FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO / FPS;

	private GameRendererWindow gameWindow = null;
	private InputManager inputHandler = null;
	private LogicManager logicManager = null;

	public Game(int ScreenWidth, int ScreenHeight) {
		gameWindow = new GameRendererWindow(ScreenWidth, ScreenHeight);
		inputHandler = gameWindow.getInputHandler();
		logicManager = new LogicManager();

	}

	// Check deltaTime, esta sendo calculado errado, deve ser o tempo desde o ultimo
	// frame (tick completo)

	public void run() {
		// Scene Loading
		Player player = new Player("src/main/resources/Assets/Marisa_Idle_01.png", 30, 33, 0);
		new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", 320, 240, 0, Layer.BACKGROUND, 0);
		
		// Scene loading end, fazer mais tarde

		jsge.util.Clock gameClock = new Clock();
		while (!(player.isPlayerDead())) {
			if (gameClock.getElapsedTimeInNanoSeconds() >= FRAME_TARGET_TIME) {
				double deltaTime = gameClock.resetClock();
				System.out.println("FPS: " + (1.f/ (float) deltaTime));

				// Pool Input
				while (inputHandler.isPoolingDone()) {
					GameKeyEvent e = inputHandler.poolEvent();
					player.sendInput(e);

				}

				// Game Logic

				logicManager.handleLogic(GameObject.getAllGameObjects(), deltaTime);

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
