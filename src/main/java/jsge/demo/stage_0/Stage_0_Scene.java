//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.demo.stage_0;

import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.AnimationClip;
import jsge.data.AudioClip;
import jsge.data.Scene;
import jsge.data.StateMachine;
import jsge.demo.utils.FadeInOut;
import jsge.utils.Callback;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;

public class Stage_0_Scene extends Scene{
	
	//UI vars
	
	MenuGameObject menu;
	GameObject loadingScreen;
	AnimationController loadingController;
	AnimationClip def;
	StateMachine<AnimationClip> loadingSM;
	
	public static AudioClip bgmMenu = new AudioClip("src/main/resources/Sounds/a_new_wind_at_the_shrine.wav");
	
	Callback callback = () -> startMenuTransition();
	Timer loadingtimer = new Timer(callback,3,false);

	
	public Stage_0_Scene(String sceneName) {
		super(sceneName);
		
	
	}
	

	@Override
	public void sceneBootStrap() {
		//Sleight of hand pra evitar criar um objeto inteiro so pra a loading screen, animatorcontroller n atualiza se n estiver vinculado a gameobject
		loadingScreen = new GameObject("LoadingScreen","src/main/resources/Assets/Loading/Loading_0.png",new Transform(320,240),Layer.BACKGROUND);
		loadingSM = new StateMachine<AnimationClip>(true);
		def = new AnimationClip();
		def.loadAnimationSpriteSheet("default","src/main/resources/Assets/Loading/Loading",0.75f,8,true,true);
		loadingSM.addState("default", def, null,null);
		loadingController =  new AnimationController(loadingSM,loadingScreen.getSpriteComponent());
		
		
		
		}	
	
	@Override
	public void sceneUpdate(){
		
		 	loadingController.internalUpdate();
		 	
	}
	private Void startMenuTransition() {
		new Timer(() -> finishMenuTransition(),1.5,false);
		new FadeInOut(3);
		
		System.out.println("Finished Loading");
		return null;

	}
	
	private Void finishMenuTransition() {
		GameObject.destroyGameObject(loadingScreen);
		bgmMenu.play();
		menu = new MenuGameObject();
		return null;
		
	}
	@Override
	public void sceneExit() {
		//debug.stop();
		
		bgmMenu.stop();
		super.sceneExit();
	}
	
	

}
