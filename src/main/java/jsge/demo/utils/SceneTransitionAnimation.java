package jsge.demo.utils;

import java.awt.Color;

import jsge.core.GameObject;
import jsge.prefabs.Panel;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;
import jsge.utils.Callback;

public class SceneTransitionAnimation {
	Panel fadeIn;
	Panel fadeOut;
	
	
	double transitionDuration;
	Callback<Void> end = (Void) -> cleanUp();
	Callback<Void> middle = (Void) -> endTransition();
	
	public SceneTransitionAnimation(double transitionDuration) {
		this.transitionDuration = transitionDuration;
		
		startTransition();
		
	}
	
	
	
	
	private Void startTransition() {
		fadeIn = new FadePanel(true);
		Timer<Void> middleTimer = new Timer<Void>(middle,null,transitionDuration/2,false);
		return null;
		
	}
	
	private Void endTransition() {
		GameObject.destroyGameObject(fadeIn);
		fadeOut = new FadePanel(false);
		Timer<Void> endTimer = new Timer<Void>(end,null,transitionDuration/2,false);
		
		
		return null;
	}
	
	private Void cleanUp() {
		GameObject.destroyGameObject(fadeOut);
		return null;
	}
	
	
	
	
	
	
	private class FadePanel extends Panel{
		int alphaMod = 10;
		public FadePanel(boolean fadeIn) {
			super("FadePanel",Layer.UI,640,480,320,240,new Color(0,0,0,0));
			if(fadeIn == false) {
				this.setColor(new Color(0,0,0,255));
				alphaMod = -10;
			}
		}
		@Override
		public void update(double deltaTime) {
			this.offsetAlpha(alphaMod);
			
			super.update(deltaTime);
		}
	}

}
