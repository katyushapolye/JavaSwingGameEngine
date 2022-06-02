package jsge.demo;

import jsge.core.Game;
import jsge.data.Scene;
import jsge.demo.stage_0.Stage_0_Scene;
import jsge.prefabs.Text;

public class Program {


	public static void main(String[] args){
		Text.setDefaultFont("src/main/resources/Assets/Font/kongtext.ttf");
		
		Scene firstScene = new Stage_0_Scene("Menu");
		Game game = new Game(800,600,firstScene);
		game.gameStart();
		
		
		return;
		
	}
}