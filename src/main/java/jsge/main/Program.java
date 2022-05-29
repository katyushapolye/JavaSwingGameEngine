package jsge.main;

import jsge.core.Game;
import jsge.data.Scene;
import jsge.demo.Menu;

public class Program {


	public static void main(String[] args){
		Game game = new Game(800,600);
		Scene firstScene = new Menu("Menu");
		game.gameStart(firstScene);
		
		return;
		
	}
}