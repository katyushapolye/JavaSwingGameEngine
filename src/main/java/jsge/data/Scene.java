package jsge.data;

import jsge.core.Game;

public class Scene {
	
	private String sceneName;
	
	public Scene(String sceneName) {
		this.sceneName = sceneName;
		
	}
	//Getters
	
	
	public String getSceneName() {
		return this.sceneName;
	}
	
	//Overwrite functions
	
	
	public void sceneBootStrap() {
		//Called on scene loading, not on gameloop
	}
	
	public void sceneUpdate() {
		//Called after processing all logic and collisions, inside gameloop
	}
	
	public void sceneExit() {
		//Called on scene unloading, not on game loop
	}
	
	
	
	
	

}
