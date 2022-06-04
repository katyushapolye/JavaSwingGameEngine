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
	
	Enemy[] wave1 =  new Enemy[9];
	Enemy[] wave2 = new Enemy[4];

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
		
		new Timer(()-> firstWaveStart(),2,false);
		new Timer(() -> checkForWaveCompletion(wave1),13,false);
		new Timer(()-> secondWaveStart(),15,true);
		
	}
	
	//357,-10
	//91, -10
	
	private void checkForWaveCompletion(Enemy[] wave) {
		for(int i= 0;i<wave.length;i++) {
			GameObject.destroyGameObject(wave[i]);
		}
		
		 GameObject.getAllGameObjects().trimToSize();
	}
	
	private Void firstWaveStart() {
		wave1[0] = new Enemy("e0", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-45,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[1] = new Enemy("e1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-60, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[2] = new Enemy("e2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-75,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[3] = new Enemy("e3", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-90, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[4] = new Enemy("e4", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-105,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[5] = new Enemy("e5", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-120, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[6] = new Enemy("e6", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-135,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[7] = new Enemy("e7", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-150, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		wave1[8] = new Enemy("e8", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-165, 40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1f,80);
		//wave1[2] = new Enemy("debug1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(327, -10), 
				//Layer.GAMEOBJECT,Enemy.EnemyPattern.Linear);
		//wave1[3] = new Enemy("debug2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(121, -10), 
				//Layer.GAMEOBJECT,Enemy.EnemyPattern.Linear);
	return null;
		
	}
	
	private void secondWaveStart() {
		wave2[0] = new Enemy("e9", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(357,-10), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1f,80);
		wave2[1] = new Enemy("e10", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(91,-10), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1f,80);
		
	}

}
