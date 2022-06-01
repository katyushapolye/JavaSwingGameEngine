package jsge.demo.MenuScene;

import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.AnimationClip;
import jsge.data.Scene;
import jsge.data.StateMachine;
import jsge.prefabs.ColoredPanel;
import jsge.prefabs.Text;
import jsge.utils.Callback;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;

public class MenuScene extends Scene{
	
	//UI vars
	
	MenuGameObject menu;
	ColoredPanel loadingFadeOut;
	GameObject loadingScreen;
	AnimationController loadingController;
	AnimationClip def;
	StateMachine<AnimationClip> loadingSM;
	
	
	Callback<Void> callback = (Void) -> startMenuTransition();
	Timer<Void> loadingtimer = new Timer<Void>(callback,null,3,false);
	
	private boolean isTransitionHappening = false;
	
	
	
	
	public MenuScene(String sceneName) {
		super(sceneName);
		
	
	}
	

	@Override
	public void sceneBootStrap() {
		loadingFadeOut = new ColoredPanel("debug",Layer.UI,640,480,320,240);
		//Sleight of hand pra evitar criar um objeto inteiro so pra a loading screen, animatorcontroller n atualiza se n estiver vinculado a gameobject
		loadingScreen = new GameObject("LoadingScreen","src/main/resources/Assets/Loading/Loading_0.png",new Transform(320,240),Layer.BACKGROUND);
		loadingSM = new StateMachine<AnimationClip>(false);
		def = new AnimationClip();
		def.loadAnimationSpriteSheet("default","src/main/resources/Assets/Loading/Loading",0.75f,8,true,true);
		loadingSM.addState("default", def, null,null);
		loadingController =  new AnimationController(loadingSM,loadingScreen.getSpriteComponent());
		
		
		}	
	
	@Override
	public void sceneUpdate(){
		
		 	loadingController.internalUpdate();
		 	
		 	if(isTransitionHappening) {
		 		loadingFadeOut.offsetColor(0, 0, 0,10);
		 	}
		 	
		 	
		
	}
	private Void startMenuTransition() {
		Callback<Void> callback = (Void) -> finishMenuTransition();
		Timer<Void> Transitiontimer = new Timer<Void>(callback,null,0.75,false);
		isTransitionHappening = true;
		System.out.println("Finished Loading");
		return null;

	}
	
	private Void finishMenuTransition() {
		GameObject.destroyGameObject(loadingScreen);
		GameObject.destroyGameObject(loadingFadeOut);
		menu = new MenuGameObject();
		return null;
		
	}
	
	

}
