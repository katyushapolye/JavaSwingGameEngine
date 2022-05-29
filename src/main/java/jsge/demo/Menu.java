package jsge.demo;

import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.prefabs.Text;
import jsge.utils.Layers.Layer;

public class Menu extends Scene{
	
	Scene debug = null;

	Text sample = null;
	public Menu(String sceneName) {
		super(sceneName);
	
	}
	

	@Override
	public void sceneBootStrap() {
		//
		Player player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png",200,200,0);
		GameObject bg = new GameObject("BG","src/main/resources/Assets/Touhou_GameBG.png",new Transform(320,240),Layer.UI);
		sample = new Text("sample","My_Sample_Text",new Transform(40,30),Layer.UI,null);
		
	}	
	
	@Override
	public void sceneUpdate(){
		sample.text  = "FPS: " + Double.toString((1.d/Game.DELTA_TIME));
		
		//System.out.println(this.sceneClock.getElapsedTimeInSeconds());
		
	}
	

}
