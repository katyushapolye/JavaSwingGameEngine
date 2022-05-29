package jsge.core;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jsge.components.Transform;
import jsge.data.Scene;
import jsge.demo.Player;
import jsge.managers.InputManager;
import jsge.managers.LogicManager;
import jsge.managers.GameStateManager;
import jsge.utils.Clock;
import jsge.utils.GameState.GameStates;
import jsge.utils.Layers.Layer;

public class Game {
	final long SECOND_IN_NANO = 1000000000l;
	final long FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO / FPS;

	private GameRendererWindow gameWindow = null;
	private InputManager inputManager = null;
	private LogicManager logicManager = null;
	
	
	public static GameStateManager gameStateManager = null; // nos devemos dar autoria para os GO's muder o estado do jogo

	
	
	private Scene currentGameScene = null;
	private Scene nextScene = null;
	

	private Map<Integer,Scene> gameScenes = null;
	
	public Game(int ScreenWidth, int ScreenHeight) {
		
		gameWindow = new GameRendererWindow(ScreenWidth, ScreenHeight);
		inputManager = gameWindow.getInputManager();
		logicManager = new LogicManager();
		gameStateManager = new GameStateManager();
		gameScenes = new HashMap<Integer,Scene>();
		
	}
	
	public void gameStart(Scene firstScene) {
		gameScenes.put(0,firstScene);
		this.currentGameScene = firstScene;
		gameRun();
	}
	
	
	//Em desenvolvimento para acomodar multiplas scenas

	private void gameRun() {
			while(true) {
			Player player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png",200,200,0);
			GameObject bg = new GameObject("BG","src/main/resources/Assets/Touhou_GameBG.png",new Transform(320,240),Layer.UI,0);
			gameLoop();
			
			
			if(gameStateManager.getCurrentGameState() == GameStates.Exit) {
				gameDispose();
				}
			}

		}
	
	
	private void gameLoop() {
		
		jsge.utils.Clock gameClock = new Clock();
		
		while (true) {

			if (gameClock.getElapsedTimeInNanoSeconds() >= FRAME_TARGET_TIME) {
				double deltaTime = gameClock.resetClock();
				while (inputManager.isPoolingDone()) {
					GameKeyEvent e = inputManager.poolEvent();
					for (GameObject go : GameObject.getAllInputReceiverGameObjects()) {
						go.receiveInput(e);
					}

				}

				if (gameStateManager.getCurrentGameState() != GameStates.Halted) {
					logicManager.handleLogic(GameObject.getAllGameObjects(), deltaTime);
					currentGameScene.sceneUpdate();
				}

				if (gameStateManager.getCurrentGameState() != GameStates.Halted) {
					gameWindow.renderGameObjects(GameObject.getAllGameObjects());
					gameWindow.display();
				}

				
				
				if (gameStateManager.getCurrentGameState() == GameStates.Exit) {
					return;
				}
				if (gameStateManager.getCurrentGameState() == GameStates.LoadNextScene) {
					
					return;
						
					
				}

			} else {
				try {
					Thread.sleep((FRAME_TARGET_TIME - gameClock.getElapsedTimeInNanoSeconds()) / 1000000);
				} catch (Exception e) {

				}

			}

		}
		
	}
	
	
	
	//Might load next scene later
	private void gameDispose() {
		System.out.println("GameExit");
		gameWindow.dispatchEvent(new WindowEvent(gameWindow, WindowEvent.WINDOW_CLOSING));
	}
}
	


