//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END

package jsge.demo.stage_0;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import jsge.components.Sprite;
import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameKeyEvent;
import jsge.core.GameKeyEvent.EventType;
import jsge.prefabs.Text;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.demo.stage_1.PlayerData;
import jsge.demo.stage_1.Stage_1_Scene;
import jsge.utils.Clock;
import jsge.utils.GameState;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;


import jsge.demo.utils.FadeInOut;
public class MenuGameObject extends GameObject {
	private int currentSelectedOption = 0;
	private int pastSelectedOption = 0;
	private MenuGameObjectContainer UIOptions;
	
	private ScoreboardGameObjectContainer scoreBoard; 
	private GameObject BG; 
	private boolean isShowingScore = false;
	
	private Sprite bgScoreSprite;
	private Sprite bgMainMenuScoreSprite;
	
	public MenuGameObject() {
		
		super("menuInputHandler", new Transform(0, 0), Layer.BACKGROUND, true);
		isShowingScore = false;
		BG = new GameObject("BG","src/main/resources/Assets/Scratchs/Touhou_Etherial_Nightmare_BG.png",new Transform(320,240),Layer.BACKGROUND);
		
		bgMainMenuScoreSprite = BG.getSpriteComponent();
		bgScoreSprite =  new Sprite("src/main/resources/Assets/Scratchs/scoreBG.png");
		
		
		Scene stage_1 = new Stage_1_Scene();
		Game.getSceneManager().loadScene(stage_1);
		
		BG.getTransform().setScale(0.90,0.80);
		
		BG.getSpriteComponent().setAlpha(255);
		
		UIOptions = new MenuGameObjectContainer();
		UIOptions.menuOptions[0].getTransform().offsetPosition(5,0);
		UIOptions.menuOptions[0].setColor(new Color(150,0,128));
		pastSelectedOption = 0;

	}

	@Override
	public void receiveInput(GameKeyEvent e) {
		if (e.getEventType() == EventType.Pressed)  {
			if(isShowingScore) {
				//esc to go back
				if(e.getKeyCode() == KeyEvent.VK_SPACE) {
					isShowingScore = false;
					scoreBoard.destroyScoreBoard();
					scoreBoard = null;
					UIOptions = new MenuGameObjectContainer();
					UIOptions.menuOptions[0].getTransform().offsetPosition(5,0);
					UIOptions.menuOptions[0].setColor(new Color(150,0,128));
					BG.setSpriteComponent(bgMainMenuScoreSprite);
					return;
					
				}
				return;
			}
			switch (e.getKeyCode()) {
			case 38:
				pastSelectedOption = currentSelectedOption;
				currentSelectedOption--;
				break;
			case 40:
				pastSelectedOption = currentSelectedOption;
				currentSelectedOption++;
				break;
			case 10:
				if(this.currentSelectedOption == 1) {
					//entering scoreboard
					UIOptions.destroyMenuObjects();
					UIOptions = null;
					currentSelectedOption = 0;
					pastSelectedOption = 0;
					isShowingScore = true;
					scoreBoard =  new ScoreboardGameObjectContainer();
					BG.setSpriteComponent(bgScoreSprite);
					return;
					
					

					
				}
				if(this.currentSelectedOption == 0) { 
					new Timer (()-> loadNextStage(),1.5,false);
					new FadeInOut(3.0);
					GameObject.stopGameObjectReceivingInput(this);
					this.isShowingScore = false;

				}
				else if(this.currentSelectedOption == 3) {
					Game.getGameStateManager().changeGameState(GameState.GameStates.Exit);
				}
			
			default:
				return;
			}
		if(currentSelectedOption >3) {
			currentSelectedOption = 0;
		}
		if(currentSelectedOption<0) {
			currentSelectedOption = 3;
		}
		
				UIOptions.menuOptions[currentSelectedOption].getTransform().offsetPosition(5,0);
				UIOptions.menuOptions[currentSelectedOption].setColor(new Color(150,0,128));
				UIOptions.menuOptions[pastSelectedOption].getTransform().offsetPosition(-5,0);
				UIOptions.menuOptions[pastSelectedOption].setColor(new Color(100,0,180));
			
			
		
			
		}
		
		//Check for deviation because of holding down the trigger, engine limitations maybe
		
		

	}
	
	@Override
	public void update(double deltaTime) {
		
		
		
		//debug
	}
	
	private Void loadNextStage() {
		Game.getSceneManager().changeScene(Game.getSceneManager().getFirstSceneIndexByName("stage_1"));
		return null;
	}
	

	

	private class MenuGameObjectContainer {
		Text[] menuOptions = new Text[4];
		Text gameTitle;
		Text subTitle;
		public MenuGameObjectContainer() {
			gameTitle = new Text("GameTitle","Touhou",new Transform(225,200),Layer.UI,null);
			gameTitle.setSize(30f);
			gameTitle.setColor(new Color(210,40,40));
			subTitle = new Text("GameSubTitle","Extra",new Transform(280,220),Layer.UI,null);
			menuOptions[0] = new Text("StartOption", "Start", new Transform(295, 260), Layer.UI, null);
			menuOptions[1] = new Text("ExtraOption", "Extras", new Transform(290, 280), Layer.UI, null);
			menuOptions[2] = new Text("OptionOption", "Options", new Transform(285, 300), Layer.UI, null);
			menuOptions[3] = new Text("ExitOption", "Exit", new Transform(300, 320), Layer.UI, null);
			menuOptions[0].setColor(new Color(100,0,180));
			menuOptions[1].setColor(new Color(100,0,180));
			menuOptions[2].setColor(new Color(100,0,180));
			menuOptions[3].setColor(new Color(100,0,180));
			

		}
		
		public void destroyMenuObjects() {
			GameObject.destroyGameObject(gameTitle);
			GameObject.destroyGameObject(subTitle);
			GameObject.destroyGameObject(menuOptions[0]);
			GameObject.destroyGameObject(menuOptions[1]);
			GameObject.destroyGameObject(menuOptions[2]);
			GameObject.destroyGameObject(menuOptions[3]);
			
		}
		
	}
	

	private class ScoreboardGameObjectContainer {
		//May crash the engine, can take shortcuts for optimization
		String[] names = new String[10];
		int[] scores = new int[10];
		
		Text[] namesUI = new Text[10];
		Text[] scoresUI = new Text[10];
		Text leaveText;
		Timer animationTimer;
		
		int animationCount = 0;
		
	
		
		public ScoreboardGameObjectContainer() {
			readFromDataFile();
			System.out.println("control");
			leaveText = new Text("leave","Press SPACE to leave...",new Transform(65,430),Layer.UI,null);
			leaveText.setColor(new Color(230,230,230));
			
			for(int i = 0;i<10;i++) {
				namesUI[i] = new Text("name" + i, (i+1)+ "- " +names[i], new Transform(30,35*i + 70), Layer.UI, null);
				namesUI[i].setSize(20);
				namesUI[i].setColor(new Color(255,230,230));
				
				scoresUI[i] = new Text("name" + i, String.valueOf(scores[i]), new Transform(300,35*i + 70), Layer.UI, null);
				scoresUI[i].setSize(20);
				scoresUI[i].setColor(new Color(230,230,230));
				
				scoresUI[i].getSpriteComponent().toggleVisibility();
				namesUI[i].getSpriteComponent().toggleVisibility();
				}
				
					
			namesUI[9].getTransform().offsetPosition(-20,0);
			
			namesUI[0].setColor(new Color(212,175,55));
			namesUI[1].setColor(new Color(192,192,192));
			namesUI[2].setColor(new Color(205,127,50));
			scoresUI[0].setColor(new Color(212,175,55));
			scoresUI[1].setColor(new Color(192,192,192));
			scoresUI[2].setColor(new Color(205,127,50));
			animationTimer =  new Timer(()->animationStep(),0.05,true);
			
			
			
		}
		
		private void animationStep() {
			namesUI[animationCount].getSpriteComponent().toggleVisibility();
			scoresUI[animationCount].getSpriteComponent().toggleVisibility();
			animationCount++;
			if(animationCount == 10) {
				
				//dodgy code right here
				animationTimer.destroyTimer();
				
			}
		}
		
		private void readFromDataFile() {
			ArrayList<String> lines =  new ArrayList<String>();
			
			try (BufferedReader br = new BufferedReader(new FileReader(new File(PlayerData.dataFilePath)))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			       lines.add(line);
			    }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//bubble sort for names, slow but fast to code
			boolean isSorted = false;
			String temp;
			while(isSorted == false) {
				isSorted = true;
				for(int i=0;i<lines.size()-1;i++) {
					int a = Integer.valueOf(lines.get(i).substring((lines.get(i).indexOf('-')+1)));
					int b = Integer.valueOf(lines.get(i+1).substring((lines.get(i+1).indexOf('-')+1)));
					if(a <= b) {
						temp = lines.get(i);
						lines.set(i, lines.get(i+1));
						lines.set(i+1, temp);
						isSorted = false;
						
						
					}
					
					
				}
				
			}
			
			for(int i=0;i<10;i++) {
				if(i>=lines.size()) {
					names[i] = "";
					scores[i] = 0;
					continue;
				}
				names[i] = lines.get(i).substring(0,lines.get(i).indexOf('-') );
				scores[i] = Integer.valueOf( lines.get(i).substring(lines.get(i).indexOf('-')+1));
				
			}
			
			
			
			
			
		}
		
		public void destroyScoreBoard() {
			for(int i = 0;i<10;i++) {
				GameObject.destroyGameObject(namesUI[i]);
				GameObject.destroyGameObject(scoresUI[i]);
				 GameObject.destroyGameObject(leaveText);

			}
		}
	}
}
