package game.main.core;

import java.util.ArrayList;

import game.main.core.Utils.Layer;
import game.main.managers.InputManager;
import game.main.managers.RendererManager;
import game.main.objects.Player;

public class Game {
	final long SECOND_IN_NANO = 1000000000l;
	final long FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO / FPS;

	private RendererWindow gameWindow = null;
	private InputManager inputHandler = null;
	private RendererManager rendererManager = null;

	public Game(int ScreenWidth, int ScreenHeight) {
		gameWindow = new RendererWindow(ScreenWidth, ScreenHeight);
		inputHandler = gameWindow.getInputHandler();
		rendererManager = gameWindow.getRendererManager();

	}

	// Check deltaTime, esta sendo calculado errado, deve ser o tempo desde o ultimo
	// frame (tick completo)

	public void run() {
		long tickStart = System.nanoTime();
		long deltaTime = 1l;// Tempo desde ultimo frame
		double deltaTimeInSeconds = 1;
		// debug 4 now
		Player player = new Player("src/main/resources/Player_Sprite.png", 400, 400, 0);
		ArrayList<GameObject> GAME_OBJECTS_IN_SCENE = new ArrayList<GameObject>();
		GAME_OBJECTS_IN_SCENE.add(player);
		GAME_OBJECTS_IN_SCENE.add(new GameObject("src/main/resources/Game_BG.png", 400, 400, 0, Layer.BACKGROUND));
		// debug 4 now

		for (int i = 0; i < 10; i++) {
			GAME_OBJECTS_IN_SCENE.add(new GameObject("src/main/resources/Player_Sprite.png", 30 + (i * 80), 100, i * 20,
					Layer.GAMEOBJECT));
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

				for (GameObject gm : GAME_OBJECTS_IN_SCENE) {
					gm.update(deltaTimeInSeconds);
				}

				// Drawn and then Display

				rendererManager.handleRendering(GAME_OBJECTS_IN_SCENE);
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