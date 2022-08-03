//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.demo.stage_1;

import java.awt.Color;
import java.awt.Font;
import java.io.File;

import jsge.components.Transform;
import jsge.core.Game;
import jsge.core.GameObject;
import jsge.data.AudioClip;
import jsge.data.Scene;
import jsge.prefabs.Text;
import jsge.utils.Layers.Layer;
import jsge.utils.Point;
import jsge.utils.Timer;

public class Stage_1_Scene extends Scene {
	Player player;
	GameObject BG;
	GameObject stage_BG;
	
	Enemy[] wave1 =  new Enemy[6];
	Enemy[] wave2 = new Enemy[6];
	
	Timer wave2Timer;
	Timer wave1Timer;
	
	CutsceneHandler dialogue =  null;
	
	static Text playerCurrentScoreText;
	static Text gameFPSUI;
	
	static Text bossHealthText;
	static Text bossHealthPercentageText;
	
	static Text currentBGText;
	static Text currentBGNameText;
	
	static Text playerCurrentLivesStaticText;
	static Text playerCurrentLivesCounterText;
	
	Text introText = null;
	Text introSubText = null;
	
	Timer playerAnim = null;
	
	Umbra umbra = null;
	
	static boolean isEndingSequenceHappening = false;
	
	static boolean isBossSequenceHappeing = false;
	
	static boolean isPlayerMoving = false;
	
	public static AudioClip bgmStage = new AudioClip("src/main/resources/Sounds/lets_live_in_a_lovely_cemetery.wav");
	
	public static AudioClip bgmBoss = new AudioClip("src/main/resources/Sounds/the_girl_who_played_with_peoples_shapes.wav");
	
	Timer fadeinTimer = null;
	
	
	
	
	private void fadeInIntroText() {
		
		introText.offSetAlpha(5);
		introSubText.offSetAlpha(5);
		if(introText.getAlpha() == 255) {
			fadeinTimer.destroyTimer();
			fadeinTimer = null;
			
		}
		
		
	}
	

	private void destroyIntroText() {
		
		GameObject.destroyGameObject(introSubText);
		GameObject.destroyGameObject(introText);
	}
	

	public Stage_1_Scene() {
		super("stage_1");
	}

	@Override
	public void sceneBootStrap() {
		bgmStage.setLoop(true);
		bgmStage.play();
		this.sceneClock.resetClock();
		isEndingSequenceHappening = false;
		player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", 220, 380, 0);
		
		BG = new GameObject("BG", "src/main/resources/Assets/Touhou_GameBG.png", new Transform(320, 240), Layer.UI);
		
		stage_BG = new GameObject("stage_BG", "src/main/resources/Assets/Stage_1/Stage_1_BG.jpeg", new Transform(193, 225), Layer.BACKGROUND);
		stage_BG.getTransform().setScale(1.2,1.3);
		
		playerCurrentScoreText = new Text("playerCurrentScore","Score: ",new Transform(430,40),Layer.UI,null);
		gameFPSUI = new Text("gameFPSUI","FPS: ",new Transform(430,300),Layer.UI,null);
		
		playerCurrentLivesStaticText =  new Text("currentLivesStatic","Current Lives: ",new Transform(430,80),Layer.UI,null);
		playerCurrentLivesCounterText = new Text("CurrentLivesCounter","99",new Transform(600,80),Layer.UI,null);
		
		currentBGText = new Text("CurrentBG","Current BGM",new Transform(465,250),Layer.UI,null);
		currentBGNameText = new Text("CurrentBGName","Let's Live in A Lovely Cemetery",new Transform(430,270),Layer.UI,null);
		currentBGNameText.setSize(6);
		
		introText = new Text("IntroText","STAGE 1 - START",new Transform(110,200),Layer.UI,null);
		try {
		introSubText = new Text("IntroSubText","In A World Without Rules, Danmaku Is Nonsense..." ,new Transform(80,230),Layer.UI,Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/Assets/Font/kirnberg.ttf")).deriveFont(12));
		}
		catch(Exception e){
			
		}
		
		introText.setSize(16);
		introText.setColor(new Color(70,230,70,0));
		introSubText.setSize(22);
		introSubText.setColor(new Color(187,187,0,0));
		
		fadeinTimer =  new Timer(()->fadeInIntroText(),0.05,true);
		
		
		
		playerCurrentLivesCounterText.setSize(18);
		
		
		Game.getSceneManager().unloadScene(Game.getSceneManager().getFirstSceneIndexByName("stage_0"));
		updatePlayerScoreUI();
		updatePlayerLivesUI();
		
		
		//stage into
		//do not let these be recurrent without any checks behind
		wave1Timer = new Timer(()-> firstWaveStart(),6,true);
		//new Timer(() -> checkForWaveCompletion(wave1),12,true);
		wave2Timer  = new Timer(()-> secondWaveStart(),10,true);
		
		new Timer(()->destroyIntroText(),6,false);
		
		//new Timer(() -> checkForWaveCompletion(wave2),20,true);

		
	}
	@Override
	public void sceneUpdate() {
		
		
		gameFPSUI.setText(String.format("FPS: %f",1.f/Game.DELTA_TIME));
		
		if(isEndingSequenceHappening) {
				
			return;
		}
		
		//System.out.println(this.sceneClock.getElapsedTimeInSeconds());
		if(dialogue != null) {
			if(dialogue.hasEnded) {
				
				dialogue.destroyDialogue();
				dialogue = null;
				System.out.println("Stage_1_SceneHandler: Control Received from CutsceneHandler, defaulting GameState, Starting BossFight Sequence");
				player.endInvencibility();
				GameObject.startGameObjectReceivingInput(player);
				bgmBoss.setLoop(true);
				bgmBoss.play();
				umbra = new Umbra();
				
				bossHealthText =  new Text("BossHealthTxt","Boss Health:",new Transform(420,180),Layer.UI,null); 
				
				bossHealthPercentageText =  new Text("BossHealthPercentage",(String.format("%d",((int)umbra.getBossHealthPercentage())*100))+"%",new Transform(565,180),Layer.UI,null); 
				currentBGNameText.setSize(5);
				currentBGNameText.getTransform().setPosition(new Point(435, 270));
				currentBGNameText.setText("The Girl Who Played With People's Shapes");
				
				//start bossfight
			}
			
		}
		
		if(this.sceneClock.getElapsedTimeInSeconds() >= 96) {
			bgmStage.stop();
			
			if(isPlayerMoving == true) {
				
				if(player.moveToPosition(new Point(220,340)) && isBossSequenceHappeing == true) {
					isPlayerMoving = false;
					//System.out.println("Player Has ended it's sequence");
					playerAnim.destroyTimer();
					
					
					
					//start dialogue sequence
					
					dialogue = new CutsceneHandler();
					
					currentBGNameText.getTransform().setPosition(new Point(490,270));
					currentBGNameText.setText("Bad Apple!!");
					
					
					
					
					
				}
			}
			if(isBossSequenceHappeing == false) {
				
			
			wave1Timer.destroyTimer();
			wave2Timer.destroyTimer();
			startBossSequence();
			isBossSequenceHappeing = true;
			isPlayerMoving = true;
			player.startInvencibility();
			}
			
			if(umbra != null) {
				
				bossHealthPercentageText.setText((String.format("%.2f",((umbra.getBossHealthPercentage())*100)))+"%");
				bossHealthPercentageText.setColor(new Color((int)(255*(1-umbra.getBossHealthPercentage())),(int)(255*umbra.getBossHealthPercentage()),0));
				//Gradiente para cor
				
				
				//update health
			}
		}
		
		
		if(player.isPlayerDead() == true) {
			PlayerData.decreaseLife();
			if(PlayerData.getLifes() <= -1) {
				if(bgmBoss != null) {
					bgmBoss.stop();
				}
				if(bgmStage != null) {
					bgmStage.stop();
				}
				//System.out.println("GAME OVER!");
				gameOverSequence();
				
				//new Timer(()->System.exit(0),4,false);
				return;
				
			}
			
			updatePlayerLivesUI();
			player = new Player("src/main/resources/Assets/Marisa/Marisa_Idle_Animation/Marisa_Idle_0.png", this.player.getTransform().getX(), this.player.getTransform().getY(), 0);

			
		}
		
		if(umbra != null && umbra.isAlive == false) {
			
			GameObject.destroyGameObject(bossHealthPercentageText);
			GameObject.destroyGameObject(bossHealthText);
			player.startInvencibility();
			stageClearSequence();
			//isEndingSequenceHappening = true;
			
			
			
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
		
	}
	
	private void secondWaveStart() {
		wave2[0] = new Enemy("e9", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(357,-30), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		wave2[2] = new Enemy("e9", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(327,-20), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		wave2[4] = new Enemy("e9", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(297,-10), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		
		
		
		wave2[1] = new Enemy("e10", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(91,-30), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		wave2[3] = new Enemy("e11", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(121,-20), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		wave2[5] = new Enemy("e12", "src/main/resources/Assets/EarthSpirit/EarthSpirit_0.png",new Transform(151,-10), 
				Layer.GAMEOBJECT,Enemy.EnemyPattern.DownLinear,1.5f,80);
		
	}
	
	public static void scenereset() {
		isEndingSequenceHappening = false;
		
		isBossSequenceHappeing = false;
		
		isPlayerMoving = false;
		
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
	
	private void startBossSequence() {
		GameObject.stopGameObjectReceivingInput(player);
		playerAnim = new Timer(()->startMovingPlayer(),0.01,true);
		
		
		
	}
	
	private void startMovingPlayer() {
		player.moveToPosition(new Point(220,340));
	}

	
	private void gameOverSequence() {
		checkForWaveCompletion(wave1);
		checkForWaveCompletion(wave2);
		wave1Timer.destroyTimer();
		wave2Timer.destroyTimer();
		isEndingSequenceHappening = true;
		
		new GameOverMenuGameObject(false);

		
	}
	
	private void stageClearSequence() {
		bgmBoss.stop();
		GameObject.destroyGameObject(umbra);
		GameObject.stopGameObjectReceivingInput(player);
		isEndingSequenceHappening = true;
		player.playerReset();
		PlayerData.addScore(25000);
		PlayerData.addScore(PlayerData.getLifes()*1000);
		new GameOverMenuGameObject(true);

		
	}


}
