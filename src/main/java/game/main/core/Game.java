package game.main.core;

import java.util.ArrayList;

import game.main.managers.InputManager;
import game.main.managers.LogicManager;

import game.main.objects.Player;
import game.main.utils.Utils.Layer;

public class Game {
	final long SECOND_IN_NANO = 1000000000l;
	final long FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO / FPS;

	private RendererWindow gameWindow = null;
	private InputManager inputHandler = null;
	private LogicManager logicManager = null;

	public Game(int ScreenWidth, int ScreenHeight) {
		gameWindow = new RendererWindow(ScreenWidth, ScreenHeight);
		inputHandler = gameWindow.getInputHandler();
		logicManager = new LogicManager();

	}

	// Check deltaTime, esta sendo calculado errado, deve ser o tempo desde o ultimo
	// frame (tick completo)

	public void run() {
		long tickStart = System.nanoTime();
		long deltaTime = 1l;// Tempo desde ultimo frame
		double deltaTimeInSeconds = 1;
		// debug 4 now
		Player player = new Player("src/main/resources/Player_Sprite.png",400,400, 0);
		new GameObject("BG","src/main/resources/Game_BG.png", 400, 400, 0, Layer.BACKGROUND,0);

		for (int i = 0; i < 1; i++) {
			new GameObject(("ENEMY_"+i) ,"src/main/resources/Player_Sprite.png", 30 + (i * 80), 100, i * 20,Layer.GAMEOBJECT,25);
		}
		

		while (true) {
			if (System.nanoTime() - tickStart >= FRAME_TARGET_TIME) {
				deltaTime = System.nanoTime() - tickStart;
				deltaTimeInSeconds = (double) deltaTime / 1000000000;
				tickStart = System.nanoTime();
				// System.out.printf("DeltaTime:%f Seconds%n",deltaTimeInSeconds);
				// System.out.printf("FPS:%d%n",SECOND_IN_NANO/deltaTime);
				// Check Game State

				// Pool Input
				while (inputHandler.isPoolingDone()) {
					GameKeyEvent e = inputHandler.poolEvent();
					player.sendInput(e);

				}

				// Game Logic

				logicManager.handleLogic(GameObject.getAllGameObjects(), deltaTimeInSeconds);

				// Drawn and then Display

				gameWindow.renderGameObjects(GameObject.getAllGameObjects());
				gameWindow.display();

			}
			// System.out.printf("DeltaTime:%d nanoSeconds%n",deltaTime);

			// THread.sleep não é preciso e erra o tempo de sleep, perdendo fps, deixe assim
			// por enquanto até uma ideia melhor
			else {
				
			}

		}

	}

}
