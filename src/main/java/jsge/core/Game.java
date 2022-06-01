package jsge.core;

import java.awt.event.WindowEvent;
import jsge.data.Scene;
import jsge.managers.InputManager;
import jsge.managers.LogicManager;
import jsge.managers.SceneManager;
import jsge.managers.GameStateManager;
import jsge.utils.Clock;
import jsge.utils.GameState.GameStates;
import jsge.utils.Timer;

public class Game {
	//Lembrar de implementar arquitetura de singleton para isso
	private static boolean openInstance = false; 
	
	static final long SECOND_IN_NANO = 1000000000l;
	static final long TARGET_FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO / TARGET_FPS;
	
	public static double DELTA_TIME = 0;

	private GameRendererWindow gameWindow = null;
	private InputManager inputManager = null;
	private LogicManager logicManager = null;
	private static GameStateManager gameStateManager = null; // nos devemos dar autoria para os GO's muder o estado do jogo
	private static SceneManager sceneManager = null;
	
	
	
	public Game(int ScreenWidth, int ScreenHeight,Scene firstScene) {
		
		if(openInstance == true) {
			System.out.println("Game: FATAL ERROR - ONLY ONE INSTANCE ALLOWED OF GAME");
			throw new RuntimeException(new Error("Terminated - Error 0x0013 - GAME MUST HAVE ONE, AND ONLY ONE INSTANCE"));
		}
		openInstance = true;
		
		
		gameWindow = new GameRendererWindow(ScreenWidth, ScreenHeight);
		inputManager = gameWindow.getInputManager();
		logicManager = new LogicManager();
		gameStateManager = new GameStateManager();
		sceneManager = new SceneManager(firstScene);
		
		System.out.println("Game Initialized");
		
		
	}	
	public static GameStateManager getGameStateManager() {
		if(gameStateManager == null) {
			System.out.println("Game: FATAL ERROR - NOT ALLOWED TO CALL GAMESTATEMANAGER WHEN THERE ISN'T A GAME RUNNING");
			throw new RuntimeException(new Error("Terminated - Error 0x0012 - GAME MUST HAVE A VALID INSTANCE"));
			
		}
		return gameStateManager;
	}
	
	public static SceneManager getSceneManager() {
		if(sceneManager == null) {
			System.out.println("Game: FATAL ERROR - NOT ALLOWED TO CALL SCENEMANAGER WHEN THERE ISN'T A GAME RUNNING");
			throw new RuntimeException(new Error("Terminated - Error 0x0012 - GAME MUST HAVE A VALID INSTANCE"));
		}
		return sceneManager;
	}
	
	
	public void gameStart() {

		
		gameRun();
	}
	
	
	//Em desenvolvimento para acomodar multiplas scenas

	private void gameRun() {
			sceneManager.changeScene(0);
			gameLoop();
			gameDispose();
			
		}
	
	
	private void gameLoop() {
		
		jsge.utils.Clock gameClock = new Clock();
		
		while (true) {

			if (gameClock.getElapsedTimeInNanoSeconds() >= FRAME_TARGET_TIME) {
				DELTA_TIME = gameClock.resetClock();
				while (inputManager.isPoolingDone()) {
					GameKeyEvent e = inputManager.poolEvent();
					try {
						
					
					for (GameObject go : GameObject.getAllInputReceiverGameObjects()) {
						go.receiveInput(e);
					}
					}
					catch(Exception exception) {
						
					}

				}

				if (gameStateManager.getCurrentGameState() != GameStates.Halted) {
					logicManager.handleLogic(GameObject.getAllGameObjects(), DELTA_TIME);
					checkAllTimers();
					sceneManager.updateCurrentScene();
				
				}

				if (gameStateManager.getCurrentGameState() != GameStates.Halted) {
					gameWindow.renderGameObjects(GameObject.getAllGameObjects());
					gameWindow.display();
				}

				
				
				if (gameStateManager.getCurrentGameState() == GameStates.Exit) {
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
	
	private void checkAllTimers() {
		for(int i = 0;i<Timer.getAllActiveTimers().size();i++) {
			Timer.getAllActiveTimers().get(i).checkTimer();
		}		
	}
	
	
	
}
	


