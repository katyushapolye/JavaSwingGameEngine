package jsge.demo.stage_1;

import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;

public class Stage_1_Scene extends Scene {
	Player player;
	GameObject BG;
	
	Enemy[] wave1 =  new Enemy[2];

	public Stage_1_Scene() {
		super("stage_1");
	}

	@Override
	public void sceneBootStrap() {
		player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 320, 240, 0);
		BG = new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", new Transform(320, 240), Layer.UI);
		
		//stage into
		
		new Timer<Void>((Void)-> firstWaveStart(),null,2,false);
		
	}
	
	private Void firstWaveStart() {
		wave1[0] = new Enemy("debug1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(357, -10), 
				Layer.GAMEOBJECT, 40,Enemy.EnemyPattern.Linear);
		wave1[1] = new Enemy("debug2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(91, -10), 
				Layer.GAMEOBJECT, 40,Enemy.EnemyPattern.Linear);
	return null;
		
	}

}
