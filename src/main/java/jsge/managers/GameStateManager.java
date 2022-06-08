//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.managers;
import jsge.data.StateMachine;

import jsge.utils.GameState.GameStates;;

public class GameStateManager {
	
	private StateMachine<GameStates> sm = null;
	
	public GameStateManager(){
		this.sm =  new StateMachine<GameStates>(true);
		this.sm.addState("Running",GameStates.Running,null,null);
		this.sm.addState("Halted",GameStates.Halted,"Running","Halt");
		this.sm.addState("Paused",GameStates.Paused,"Running","Pause");
		this.sm.addState("Exit", GameStates.Exit,"Running","Exit");
		
		
		//sm.dumpStateMachineOnConsole();
	
	}
	
	public GameStates getCurrentGameState() {
		return this.sm.getCurrentStateData();
		}
	public void changeGameState(GameStates gameState) {
		if(sm.getCurrentStateData() == gameState) {
			return;
		}
		//Yes the state machine is overkill
		else {
			sm.forceStateChange(gameState.toString());
		}
		
	}

}
