package jsge.data;

import jsge.utils.Clock;

public class Scene {
	
	private String sceneName;
	
	protected Clock sceneClock;
	
	public Scene(String sceneName) {
		this.sceneName = sceneName;
		this.sceneClock =  new Clock();
		
	}
	//Getters
	public String getSceneName() {
		return this.sceneName;
	}
	
	//Overwrite functions
	
	
	public void sceneBootStrap() {
		//Called on scene change
	}
	
	public void sceneUpdate() {
		//Called after processing all logic and collisions, inside gameloop
	}
	
	public void sceneExit() {
		//Called on scene change, if it is being the scene changed from
	}
	
	
	
	
	

}
