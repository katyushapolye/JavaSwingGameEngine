package jsge.demo.utils;

import java.awt.Color;

import jsge.core.Game;
import jsge.core.GameObject;
import jsge.prefabs.Panel;
import jsge.utils.Layers.Layer;
import jsge.utils.Timer;
import jsge.utils.Callback;

public class FadeInOut {
	Panel fadeIn;
	Panel fadeOut;
	
	
	double transitionDuration;
	
	int alphaModifier = 10;
	Callback<Void> end = (Void) -> cleanUp();
	Callback<Void> middle = (Void) -> endTransition();
	
	public FadeInOut(double transitionDuration) {
		this.transitionDuration = transitionDuration;
		this.alphaModifier = (int)((255/(transitionDuration/2))/Game.TARGET_FPS)+3;
		
		startTransition();
		
	}
	
	
	
	
	private Void startTransition() {
		fadeIn = new FadePanel(true,alphaModifier);
		new Timer<Void>(middle,null,transitionDuration/2,false);
		return null;
		
	}
	
	private Void endTransition() {
		GameObject.destroyGameObject(fadeIn);
		fadeOut = new FadePanel(false,alphaModifier);
		new Timer<Void>(end,null,transitionDuration/2 - 20*(transitionDuration)/100,false);
		
		
		return null;
	}
	
	private Void cleanUp() {
		GameObject.destroyGameObject(fadeOut);
		return null;
	}
	
	
	
	
	
	
	private class FadePanel extends Panel{
		
		int alphaMod;
		public FadePanel(boolean fadeIn,int alphaMod) {
			super("FadePanel",Layer.UI,640,480,320,240,new Color(0,0,0,0));
			this.alphaMod = alphaMod;
			if(fadeIn == false) {
				this.setColor(new Color(0,0,0,255));
				this.alphaMod = -this.alphaMod;
			}
		}
		@Override
		public void update(double deltaTime) {
			this.offsetAlpha(alphaMod);
			
			super.update(deltaTime);
		}
	}

}
