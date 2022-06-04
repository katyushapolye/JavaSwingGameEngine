package jsge.demo.stage_0;

import java.awt.Color;

import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameKeyEvent;
import jsge.core.GameKeyEvent.EventType;
import jsge.prefabs.Text;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.demo.stage_1.Stage_1_Scene;
import jsge.utils.GameState;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;


import jsge.demo.utils.FadeInOut;
public class MenuGameObject extends GameObject {
	private int currentSelectedOption = 0;
	private int pastSelectedOption = 0;
	private MenuGameObjectContainer UIOptions;
	private GameObject BG; 
	
	public MenuGameObject() {
		super("menuInputHandler", new Transform(0, 0), Layer.BACKGROUND, true);
		BG = new GameObject("BG","src/main/resources/Assets/Scratchs/Touhou_Etherial_Nightmare_BG.png",new Transform(320,240),Layer.BACKGROUND);
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
				if(this.currentSelectedOption == 0) { 
					new Timer<Void>((Void)-> loadNextStage(),null,1.5,false);
					new FadeInOut(3.0);
					GameObject.stopGameObjectReceivingInput(this);

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
		public MenuGameObjectContainer() {
			menuOptions[0] = new Text("StartOption", "Start", new Transform(295, 260), Layer.UI, null);
			menuOptions[1] = new Text("ExtraOption", "Extras", new Transform(290, 280), Layer.UI, null);
			menuOptions[2] = new Text("OptionOption", "Options", new Transform(285, 300), Layer.UI, null);
			menuOptions[3] = new Text("ExitOption", "Exit", new Transform(300, 320), Layer.UI, null);
			menuOptions[0].setColor(new Color(100,0,180));
			menuOptions[1].setColor(new Color(100,0,180));
			menuOptions[2].setColor(new Color(100,0,180));
			menuOptions[3].setColor(new Color(100,0,180));
			

		}
		
	}
}
