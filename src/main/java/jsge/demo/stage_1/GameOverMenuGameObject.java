package jsge.demo.stage_1;

import java.awt.Color;

import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameKeyEvent;
import jsge.core.GameObject;
import jsge.core.GameKeyEvent.EventType;
import jsge.demo.utils.FadeInOut;
import jsge.prefabs.Text;
import jsge.utils.GameState;
import jsge.utils.Timer;
import jsge.utils.Layers.Layer;

public class GameOverMenuGameObject extends GameObject {
	
	int currentSelectedOption = 0;
	int pastSelectedOption = 0;
	GameOverMenuGameObjectContainer UIOptions = new GameOverMenuGameObjectContainer(); 
	public GameOverMenuGameObject() {
		
		super("gameOvermenuInputHandler", new Transform(0, 0), Layer.BACKGROUND, true);
		UIOptions.menuOptions[0].getTransform().offsetPosition(5,0);
		UIOptions.menuOptions[0].setColor(new Color(0,255,0));


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
					//yes

				}
				else if(this.currentSelectedOption == 1) {
					new Timer (()-> loadNextStage(),1.5,false);
					new FadeInOut(3.0);
					GameObject.stopGameObjectReceivingInput(this);
				}
			
			default:
				return;
			}
		if(currentSelectedOption >1) {
			currentSelectedOption = 0;
		}
		if(currentSelectedOption<0) {
			currentSelectedOption = 1;
		}
		
				UIOptions.menuOptions[currentSelectedOption].getTransform().offsetPosition(5,0);
				UIOptions.menuOptions[currentSelectedOption].setColor(new Color(0,255,0));
				UIOptions.menuOptions[pastSelectedOption].getTransform().offsetPosition(-5,0);
				UIOptions.menuOptions[pastSelectedOption].setColor(new Color(255,255,255));
			
			
		
			
		}
		
		//Check for deviation because of holding down the trigger, engine limitations maybe
		
		

	}
	
	private Void loadNextStage() {
		Game.getSceneManager().changeScene(Game.getSceneManager().getFirstSceneIndexByName("stage_0"));
		return null;
	}
	
	private void softGameReset() {
		//reset all player lives and scores
		//load from menu
	}
	

	
	
	private class GameOverMenuGameObjectContainer {
		Text[] menuOptions = new Text[2];
		Text gameOverContinueText;
		Text gameOverText;
		public GameOverMenuGameObjectContainer() {
			gameOverText = new Text("gameOverText","GAME OVER! ",new Transform(80,170),Layer.UI,null);
			gameOverContinueText = new Text("GameTitle","Do you wish to \n"
					+ "save your score?",new Transform(40,200),Layer.UI,null);
			gameOverText.setSize(30f);
			gameOverText.setColor(new Color(210,40,40));
			menuOptions[0] = new Text("yesOption", "Yes", new Transform(205, 220), Layer.UI, null);
			menuOptions[1] = new Text("noOption", "No", new Transform(210, 238), Layer.UI, null);
			
			
			
			
			

		}
		
	}
}
