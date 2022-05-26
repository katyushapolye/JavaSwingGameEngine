package jsge.core;

import jsge.util.Clock;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
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
		new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", 320, 240, 0, Layer.UI, 0);
		
		
		Player player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_0.png", 30, 33, 0);
		
		
		// Scene loading end, fazer mais tarde
		//Devem ser declarados assim pois há java.awt.clock
		jsge.util.Clock gameClock = new Clock();
		
		
		
		//DEBUG DEBUG DEBUG DEBUG
		
		jsge.util.Clock animationClock = new Clock();
		int i = 0;
		AnimationClip ac =  new AnimationClip();
		ac.loadAnimationSpriteSheet("Marisa_Idle","src/main/resources/Assets/Marisa/Marisa_Idle",0.33f,4,true);
		StateMachine<AnimationClip> sm =  new StateMachine<AnimationClip>();
		sm.addState("Marisa_Idle",ac,null,null,false);
		sm.addState("Marisa_Moving_Left",ac,"Marisa_Idle","Left",true);
		sm.forceStateChange("Marisa_Moving_Left");
		
		//ENDDEBUG ENDDEBUG ENDDEBUG
		
		
		while (!(player.isPlayerDead())) {
			if (gameClock.getElapsedTimeInNanoSeconds() >= FRAME_TARGET_TIME) {
				double deltaTime = gameClock.resetClock();
				//System.out.println("FPS: " + (1.f/ (float) deltaTime));

				// Pool Input
				while (inputHandler.isPoolingDone()) {
					GameKeyEvent e = inputHandler.poolEvent();
					player.sendInput(e);

				}

				// Game Logic

				logicManager.handleLogic(GameObject.getAllGameObjects(), deltaTime);

				// Drawn and then Display
				
				
			
				
				//DEBUG --  DEBUG -- DEBUG -- DEBUG -- DEBUG
				
				if(animationClock.getElapsedTimeInSeconds() >= ac.getAnimationLength()/ac.getAnimationFrameCount()) {
					animationClock.resetClock();
					player.setSprite(ac.getAnimationFrame(i%ac.getAnimationFrameCount()));
					if(i%2 == 0) {
						sm.changeState("Left");
					}
					else {
						sm.changeState("Left");
					}
					System.out.println(sm.getCurrentStateID());
					//System.out.println("Update sprite");
					i++;
					
				}
				
				//ENDDEBUG --  ENDDEBUG -- ENDDEBUG -- ENDDEBUG

				

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
