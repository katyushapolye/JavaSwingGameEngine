//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.demo.stage_1;

import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameObject;
import jsge.data.Scene;
import jsge.prefabs.Text;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;

public class Stage_1_Scene extends Scene {
	Player player;
	GameObject BG;
	GameObject stage_BG;
	
	Enemy[] wave1 =  new Enemy[9];
	Enemy[] wave2 = new Enemy[4];
	
	static Text playerCurrentScoreText;
	Text gameFPSUI;

	public Stage_1_Scene() {
		super("stage_1");
	}

	@Override
	public void sceneBootStrap() {
		player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 320, 240, 0);
		BG = new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", new Transform(320, 240), Layer.UI);
		stage_BG = new GameObject("stage_BG", "src/main/resources/Assets/Stage_1/Stage_1_BG.jpeg", new Transform(193, 225), Layer.BACKGROUND);
		stage_BG.getTransform().setScale(1.2,1.3);
		playerCurrentScoreText = new Text("GameSubTitle","Score: ",new Transform(430,40),Layer.UI,null);
		gameFPSUI = new Text("GameSubTitle","FPS: ",new Transform(430,300),Layer.UI,null);
		Game.getSceneManager().unloadScene(Game.getSceneManager().getFirstSceneIndexByName("stage_0"));
		updatePlayerScoreUI();
		
		//stage into
		
		new Timer(()-> firstWaveStart(),2,false);
		new Timer(() -> checkForWaveCompletion(wave1),12,false);
		new Timer(()-> secondWaveStart(),8,true);
		new Timer(()-> firstWaveStart(),14,true);
		new Timer(()-> firstWaveStart(),20,true);
		
	}
	@Override
	public void sceneUpdate() {
		
		gameFPSUI.setText(String.format("FPS: %f",1.f/Game.DELTA_TIME));
	
	}
	
	//357,-10
	//91, -10
	
	private void checkForWaveCompletion(Enemy[] wave) {
		for(int i= 0;i<wave.length;i++) {
			GameObject.destroyGameObject(wave[i]);
		}
		
		 GameObject.getAllGameObjects().trimToSize();
	}
	
	private void firstWaveStart() {
		wave1[0] = new Enemy("e0", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-45,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,3.0f,80);
		wave1[1] = new Enemy("e1", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-60, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1.5f,80);
		wave1[2] = new Enemy("e2", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-75,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,3.0f,80);
		wave1[3] = new Enemy("e3", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-90, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1.5f,80);
		wave1[4] = new Enemy("e4", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-105,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,3.0f,80);
		wave1[5] = new Enemy("e5", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-120, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1.5f,80);
		wave1[6] = new Enemy("e6", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-135,40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,3.0f,80);
		wave1[7] = new Enemy("e7", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-150, 60), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,1.5f,80);
		wave1[8] = new Enemy("e8", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(-165, 40), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.SideLinear,3.0f,80);
		
	}
	
	private void secondWaveStart() {
		wave2[0] = new Enemy("e9", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(357,-10), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		wave2[1] = new Enemy("e10", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(91,-10), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		
	}
	
	public static void updatePlayerScoreUI() {
		String s = String.format("Score %d",PlayerData.getScore());
		playerCurrentScoreText.setText(s);
	}

}
