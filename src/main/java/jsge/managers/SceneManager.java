package jsge.managers;

import java.util.HashMap;
import java.util.Map.Entry;

import jsge.core.GameObject;
import jsge.data.Scene;

public class SceneManager {
	
	private Scene currentLoadedScene = null;
	private HashMap<Integer,Scene> loadedScenes = null;
	
	
	public SceneManager(Scene firstScene) {
		loadedScenes = new HashMap<Integer, Scene>();
		loadedScenes.put(0, firstScene);
		currentLoadedScene = firstScene;
		
	}
	
	public void updateCurrentScene() {
		this.currentLoadedScene.sceneUpdate();
	}
	
	public Scene getCurrentLoadedScene() {
		return this.currentLoadedScene;
	}
	
	public void changeScene(int sceneIndex) {
		if(sceneIndex <=-1) {
			System.out.println("SceneManager: Warning - Index of negative value has been given, Aborting");
			return;
		}
		currentLoadedScene.sceneExit();
		GameObject.destroyAllGameObjects();
		currentLoadedScene = loadedScenes.getOrDefault(sceneIndex,null);
		System.out.println("Loaded Scene - " +currentLoadedScene.getSceneName());
		if(currentLoadedScene == null) {
			System.out.println("SceneManager: FATAL ERROR - Scene of index "+ sceneIndex + " not found");
			throw new RuntimeException(new Error("Terminated - Error 0x000A - Scene not found in memory"));
		}
		currentLoadedScene.sceneBootStrap();
		
	}
	
	//Apenas carrega a scena na memoria, nao a executa
	public void loadScene(Scene scene) {
		if(scene == null) {
			System.out.println("SceneManager: FATAL ERROR - Loading null references into memory is not allowed");
			throw new RuntimeException(new Error("Terminated - Error 0x000B - Null reference cannot be loaded"));
		}
		loadedScenes.put(loadedScenes.size(),scene);
	}
	
	public void unloadScene(int index) {
		loadedScenes.remove(index);
	}
	
	public int getFirstSceneIndexByName(String name){
		for (Entry<Integer,Scene> entry: loadedScenes.entrySet()) {
			if(entry.getValue().getSceneName() == name) {
				return entry.getKey();
				}
			
		}
		return -1;
	}
	
	

}