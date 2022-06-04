package jsge.demo.stage_1;

import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;

public class Stage_1_Scene extends Scene {
	Player player;
	GameObject BG;
	GameObject stage_BG;
	
	Enemy[] wave1 =  new Enemy[4];

	public Stage_1_Scene() {
		super("stage_1");
	}

	@Override
	public void sceneBootStrap() {
		player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 320, 240, 0);
		BG = new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", new Transform(320, 240), Layer.UI);
		stage_BG = new GameObject("stage_BG", "src/main/resources/Assets/Stage_1/Stage_1_BG.jpeg", new Transform(193, 225), Layer.BACKGROUND);
		stage_BG.getTransform().setScale(1.2,1.3);
		
		
		//stage into
		
		new Timer<Void>((Void)-> firstWaveStart(),null,2,false);
		
	}
	
	//357,-10
	//91, -10
	
	private Void firstWaveStart() {
		wave1[0] = new Enemy("debug1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-15,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[1] = new Enemy("debug2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-30, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[2] = new Enemy("debug1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-75,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[3] = new Enemy("debug2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-90, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		//wave1[2] = new Enemy("debug1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(327, -10), 
				//Layer.GAMEOBJECT,Enemy.EnemyPattern.Linear);
		//wave1[3] = new Enemy("debug2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(121, -10), 
				//Layer.GAMEOBJECT,Enemy.EnemyPattern.Linear);
	return null;
		
	}

}
