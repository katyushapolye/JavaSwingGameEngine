//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.demo.stage_1;

import java.awt.Color;

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
	static Text gameFPSUI;
	
	static Text playerCurrentLivesStaticText;
	static Text playerCurrentLivesCounterText;

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
		
		playerCurrentLivesStaticText =  new Text("currentLivesStatic","Current Lives: ",new Transform(430,80),Layer.UI,null);
		playerCurrentLivesCounterText = new Text("CurrentLivesCounter","99",new Transform(600,80),Layer.UI,null);
		playerCurrentLivesCounterText.setSize(18);
		
		
		Game.getSceneManager().unloadScene(Game.getSceneManager().getFirstSceneIndexByName("stage_0"));
		updatePlayerScoreUI();
		updatePlayerLivesUI();
		//stage into
		
		//new Timer(()-> firstWaveStart(),6,true);
		//new Timer(() -> checkForWaveCompletion(wave1),12,true);
		new Timer(()-> secondWaveStart(),10,true);
		new Timer(() -> checkForWaveCompletion(wave2),20,true);

		
	}
	@Override
	public void sceneUpdate() {
		
		gameFPSUI.setText(String.format("FPS: %f",1.f/Game.DELTA_TIME));
		if(player.isPlayerDead() == true) {
			PlayerData.decreaseLife();
			updatePlayerLivesUI();
			player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 320, 240, 0);

			
		}
	
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
	public static void updatePlayerLivesUI() {
		
		String s = String.format("%d",PlayerData.getLifes());
		
		playerCurrentLivesCounterText.setText(s);
		
		switch (PlayerData.getLifes()) {
		case 0:
			playerCurrentLivesCounterText.setColor(new Color(230,10,10));
			break;

		case 1:
			playerCurrentLivesCounterText.setColor(new Color(230,230,10));
			break;
		case 2:
			playerCurrentLivesCounterText.setColor(new Color(10,230,10));
			break;
		default:
			playerCurrentLivesCounterText.setColor(new Color(10,10,230));

			break;
		}
		
	
	}

}
