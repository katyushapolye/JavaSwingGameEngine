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
		// TODO Auto-generated method stub
		Player player = new Player("",200,200,0);
		GameObject bg = new GameObject("BG","src/main/resources/Assets/Touhou_GameBG.png",new Transform(320,240),Layer.UI,0);
		 debug = new Scene("Test");
		
		
	}	
	
	@Override
	public void sceneUpdate() {
		
	}
	

}
