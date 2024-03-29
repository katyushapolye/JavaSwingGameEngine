//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.components;

import jsge.core.Game;
import jsge.data.AnimationClip;
import jsge.data.StateMachine;
import jsge.utils.Clock;
import jsge.utils.GameState.GameStates;


public class AnimationController {
	private StateMachine<AnimationClip> currentStateMachine = null;
	private Sprite spriteToControl = null;
	private Clock animationClock = null;
	
	private float currentAnimationLength = 0;
	private int currentAnimationFrames = 0;
	private AnimationClip currentAnimationClip = null;
	private int currentFrame = 0;
	

	//Lembrar de trocar GameOBject por classe encapsulada sprite num futuro proximo
	public AnimationController(StateMachine<AnimationClip> stateMachine,Sprite spriteToControl){
		this.currentStateMachine = stateMachine;
		this.spriteToControl = spriteToControl;
		this.animationClock = new Clock();
		internalUpdate(this.currentStateMachine.getCurrentStateData());
	}
	
	public void sendTrigger(String triggerID) {
		this.currentStateMachine.changeState(triggerID);
		internalUpdate(currentStateMachine.getCurrentStateData());
		
		
	}
	
	public void resetState() {
		this.currentStateMachine.resetMachine();
		internalUpdate(currentStateMachine.getCurrentStateData());

	}
	
	public void internalUpdate() {
		if(currentAnimationClip.updatesOnPause() == false && Game.getGameStateManager().getCurrentGameState() == GameStates.Paused) {
			return;
		}
		if(animationClock.getElapsedTimeInSeconds() >= currentAnimationLength/(float)currentAnimationFrames){
			animationClock.resetClock();
			spriteToControl.changeSprite(currentAnimationClip.getAnimationFrame(currentFrame%currentAnimationFrames));
			currentFrame++;
			
			//Optimização aqui, tentar fazer o check uma vez só
			//Garante que vai resetar a maquina se a animação nao tiver em loop, n sei quando isso vai ser util, fazer 
			//que quando uma animação nao loopar, ela tenha uma animação de saida dela
			if(!currentAnimationClip.getAnimationLooping() || currentFrame == currentAnimationFrames) {
				this.currentStateMachine.resetMachine();
				internalUpdate(currentStateMachine.getCurrentStateData());
				
				
			}
			
			
		}
		
	}
	
	
	
	private void internalUpdate(AnimationClip newClip) {
		this.currentAnimationClip = newClip;
		this.currentAnimationFrames = currentAnimationClip.getAnimationFrameCount();
		this.currentAnimationLength = currentAnimationClip.getAnimationLength();
	}
	
	
	
	
	
}
