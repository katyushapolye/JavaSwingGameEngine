package jsge.demo.MenuScene;

import jsge.components.AnimationController;
import jsge.components.Transform;
import jsge.core.GameObject;
import jsge.data.AnimationClip;
import jsge.data.Scene;
import jsge.data.StateMachine;
import jsge.prefabs.Text;
import jsge.utils.Callback;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;

public class MenuScene extends Scene{
	
	//UI vars
	
	MenuGameObject menu;
	
	GameObject loadingScreen;
	AnimationController loadingController;
	AnimationClip def;
	StateMachine<AnimationClip> loadingSM;
	
	
	Callback<Void> callback = (Void) -> finishLoading();
	Timer<Void> timer = new Timer<Void>(callback,null,6,false);
	
	
	
	
	public MenuScene(String sceneName) {
		super(sceneName);
		
	
	}
	

	@Override
	public void sceneBootStrap() {
		loadingScreen = new GameObject("LoadingScreen","src/main/resources/Assets/Loading/Loading_0.png",new Transform(320,240),Layer.BACKGROUND);
		loadingSM = new StateMachine<AnimationClip>(false);
		
		def = new AnimationClip();
		def.loadAnimationSpriteSheet("default","src/main/resources/Assets/Loading/Loading",0.50f,8,true,true);
		loadingSM.addState("default", def, null,null);
		loadingController =  new AnimationController(loadingSM,loadingScreen.getSpriteComponent());
		
		
		}	
	
	@Override
	public void sceneUpdate(){
		
		 	loadingController.internalUpdate();
		
	}
	
	
	private Void finishLoading() {
		GameObject.destroyGameObject(loadingScreen);
		menu = new MenuGameObject();
		System.out.println("Finished Loading");
		return null;

	}
	
	

}
