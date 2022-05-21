package game.main.managers;

import java.util.ArrayList;

import game.main.core.GameObject;
import game.main.core.RendererWindow;

public class RendererManager {
	private RendererWindow window = null;
	private ArrayList<GameObject> BGLayer =  new ArrayList<GameObject>();
	private ArrayList<GameObject> UILayer =  new ArrayList<GameObject>();
	private ArrayList<GameObject> GAMEOBJECTLayer =  new ArrayList<GameObject>();
	private ArrayList<GameObject> PARTICLELayer =  new ArrayList<GameObject>();
	public RendererManager(RendererWindow renderInterface){
		this.window = renderInterface;
		
		
	}
	
	public void handleRendering(ArrayList<GameObject> objectsToRender) {
		
			filterLayers(objectsToRender);
			renderLayers();
			clearLayers();
			
			
		}
		
		
	private void filterLayers(ArrayList<GameObject> objectsToRender) {
		for(GameObject gm : objectsToRender) {
			switch(gm.getLayer()) {
			case BACKGROUND:
				BGLayer.add(gm);
				break;
			case UI:
				UILayer.add(gm);
				break;
			case GAMEOBJECT:
				GAMEOBJECTLayer.add(gm);
				break;
			case PARTICLE:
				PARTICLELayer.add(gm);
				break;
			}
		}
	}
	
	
	private void renderLayers() {
		//A ordem importa, primero é desenhado as layers de bg, dps gm, dps futuras particulas e dps UI
		for(GameObject gm : BGLayer) {
			window.drawGameObject(gm);
		}
		for(GameObject gm : GAMEOBJECTLayer) {
			window.drawGameObject(gm);
		}
		for(GameObject gm : PARTICLELayer) {
			window.drawGameObject(gm);
		}
		for(GameObject gm : UILayer) {
			window.drawGameObject(gm);
		}
	}
	
	private void clearLayers() {
		BGLayer.clear();
		GAMEOBJECTLayer.clear();
		PARTICLELayer.clear();
		UILayer.clear();
		
	}
}
