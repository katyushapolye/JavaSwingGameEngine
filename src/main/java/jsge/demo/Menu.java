package jsge.demo;

import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.utils.Layers.Layer;

public class Menu extends Scene{
	Scene debug = null;

	public Menu(String sceneName) {
		super(sceneName);
	
	}
	

	@Override
	public void sceneBootStrap() {
		//
		Player player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png",200,200,0);
		GameObject bg = new GameObject("BG","src/main/resources/Assets/Touhou_GameBG.png",new Transform(320,240),Layer.UI,0);
		Scene p1 = new Scene("Phase-1");
		Game.getSceneManager().loadScene(p1);
		
		
	}	
	
	@Override
	public void sceneUpdate(){
		System.out.println(this.sceneClock.getElapsedTimeInSeconds());
		if(this.sceneClock.getElapsedTimeInSeconds() >= 30.f) {
			Game.getSceneManager().changeScene(Game.getSceneManager().getFirstSceneIndexByName("Phase-1"));
		}
		
	}
	

}
