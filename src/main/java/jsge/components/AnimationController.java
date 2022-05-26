package jsge.components;

import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.util.Clock;
import java.awt.image.BufferedImage;

public class AnimationController {
	private StateMachine<AnimationClip> currentStateMachine = null;
	private BufferedImage sprite = null;
	private Clock animationClock = null;

	//Lembrar de trocar BUffered image por classe encapsulada sprite num futuro proximo
	AnimationController(StateMachine<AnimationClip> stateMachine,BufferedImage spriteToControl ){
		this.currentStateMachine = stateMachine;
		this.sprite = spriteToControl;
		this.animationClock = new Clock();
	}
	
	public void sendTrigger(String triggerID) {
		this.currentStateMachine.changeState(triggerID);
	}
	
	public void updateAnimationController() {
		
	}
	
	
	
	
	
	
}
