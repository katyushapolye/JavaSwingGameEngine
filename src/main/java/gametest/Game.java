package gametest;

import gametest.game.Player;

public class Game {
	final long SECOND_IN_NANO = 1000000000l;
	final long FPS = 60l;
	final long FRAME_TARGET_TIME = SECOND_IN_NANO/FPS;
	
	
	
	private RendererPanel gameWindow = null;
	private InputHandler inputHandler = null;
	
	public Game(int ScreenWidth,int ScreenHeight) {
		gameWindow = new RendererPanel(ScreenWidth, ScreenHeight);
		inputHandler = gameWindow.getInputHandler();
		
	}
	
	//Check deltaTime, esta sendo calculado errado, deve ser o tempo desde o ultimo frame (tick completo)
	
	public void run() {
		long tickStart =  System.nanoTime();
		long deltaTime = 0l;//Tempo desde ultimo frame
		double deltaTimeInSeconds = 0;
		GameObject player = new Player("src/main/resources/Player_Sprite.png",400,400,0);
		GameObject BG = new GameObject("src/main/resources/Game_BG.png",400,400,0);
		GameObject[] debugArray =  new GameObject[10];
		for(int i =0;i<10;i++) {
			debugArray[i] = new GameObject("src/main/resources/Player_Sprite.png",30+(i*80),100,0);
			}
		
		while(true) {
			if(System.nanoTime() - tickStart >= FRAME_TARGET_TIME) {
				deltaTime = System.nanoTime() - tickStart;
				deltaTimeInSeconds = (double)deltaTime/1000000000;
				tickStart = System.nanoTime();	
				//Check Game State
				
				//Pool Input
				while(inputHandler.isPoolingDone()) {
					GameKeyEvent event = inputHandler.poolChar();
					if(event.getEventType() ==  GameKeyEvent.EventType.Pressed) {
						switch(event.getKeyChar()) {
						case 'w':
							player.offsetPosition(0,(int)(-400*deltaTimeInSeconds));
							break;	
						case 's':
							player.offsetPosition(0,(int)(400*deltaTimeInSeconds));
							break;
						case 'd':
							player.offsetRotation(+10);
							break;
						case 'a':
							player.offsetRotation(-10);
							break;
						
							}
						}
					}	
				
				

				
				//Game Logic
				
				
				//Drawn and then Display
				//check render order of stuff <-- MAX PRIORITY!!! seems to be working, but you will never know, test multiple targets
				
				gameWindow.DrawGameObject(BG);
				gameWindow.DrawGameObject(player);
				for(int i =0;i<10;i++) {
					gameWindow.DrawGameObject(debugArray[i]);
					}
				
				
				
				gameWindow.display();
				
				}
				
				//System.out.printf("DeltaTime:%d nanoSeconds%n",deltaTime);
				//System.out.printf("DeltaTime:%f Seconds%n",deltaTimeInSeconds);
				//System.out.printf("FPS:%d%n",SECOND_IN_NANO/deltaTime);
				
			//THread.sleep não é preciso e erra o tempo de sleep, perdendo fps, deixe assim por enquanto até uma ideia melhor
			else {
				/*
				int sleepTime = (int) (frameTargetTime - (tickStart-System.nanoTime()));
				
				try {
					
					Thread.sleep(sleepTime/1000000);
					}
				catch(Exception e){
					System.out.println("SleepAwake");
					}
					
					
				
				}
				*/
				}
			}
		
	}
	
	
}
			
			
			

