package jsge.data;
import java.util.*;


public class StateMachine <T> {
	private State<T> currentState;
	private State<T> defaultState;
	private State<T> exitState;
	private String lastTransitionTrigger = null; //Para evitar chamadas duplicadas
	
	
	//REUTILIZAR USANDO GENERICO PARA MAQUINA DE ESTADO DO JOGO <- REFATORAR
	private ArrayList<State<T>> stateList;
	
	public StateMachine() {
		stateList =  new ArrayList<State<T>>();
		
	}
	
	//public StateMachine(StateMachine sm) {
	//	this = sm;
	//}
	
	public void forceStateChange(String stateID) {
		State<T> temp = findStatebyID(stateID);
		if(temp == null){
			System.out.println("Error - State not found");
			return;
		}
		else {
			currentState = temp;
		}
			
	}
	
	public void changeState(String transitionID) {
		Transition<T> temp = null;
		for (Transition<T> t : currentState.getAllTransitions()) {
			if(t.transitionTrigger == transitionID) {
				temp =t;
			}
		} 
		if(temp == null) {
			
			if(transitionID == lastTransitionTrigger) {
				System.out.println("Warning - Duplicated transition on StateMachine, Is This Intended Behaviour? - TransitionTriggerID = " + lastTransitionTrigger);
				return; //Evita error se for enviado a msm chamada diversas vezes
				
			}
			
			System.out.println("Error - No transition matches this ID for the current state");
			return;
		
		
		}
		 currentState = temp.destinationState;
		 lastTransitionTrigger = temp.transitionTrigger;
		
	}
	
	public void exitMachine() {
		this.currentState = this.exitState;
	}
	
	public void setDefaultState(String stateID) {
		State<T> temp = this.findStatebyID(stateID);
		if(temp == null) {
			System.out.println("Error - State not found");
			return;
		}
		this.defaultState = temp;
	}
	public void setExitState(String stateID) {
		State<T> temp = this.findStatebyID(stateID);
		if(temp == null) {
			System.out.println("Error - State not found");
			return;
		}
		this.exitState = temp;
	}
	
	public void resetMachine() {
		this.currentState = this.defaultState;
	}
	
	public String getCurrentStateID() {
		return this.currentState.stateID;
	}
	
	//Refatorar para generalizar a maquina
	public T getCurrentStateData() {
		return this.currentState.getData();
	}
	
	public void addState(String stateName,T data,String sourceState,String trigger,boolean twoWay) {
		if(sourceState ==  null || stateList.size() == 0) {
			System.out.println("No state previously set, setting " + stateName + " as default state");

			sourceState = "origin";
			stateList.add(new State<T>(stateName,data));
			defaultState =  stateList.get(0);
			currentState =  defaultState;
			exitState = defaultState;
			
		}
		else {
			State<T> originState = null;
			
			for (int i = 0; i < stateList.size(); i++) {
				if(stateList.get(i).stateID == stateName) {
					System.out.println("Error - Failed to create state in State in StateMachine, Duplicated stateID");
					break;
				}
				if(stateList.get(i).stateID == sourceState) {
					originState = stateList.get(i);
				}
				
			}
			
			if(originState == null) {
				System.out.println("Error - Failed to create state in State in StateMachine, Origin state not found");

			}
			State<T> temp = new State<T>(stateName,data);
			stateList.add(temp);
			originState.addTransition(new Transition<T>(trigger,temp));
			if(twoWay) {
				temp.addTransition(new Transition<T>(trigger,originState));
			}
			
		}
		
		
	}
	
	private State<T> findStatebyID(String ID) {
		State<T> returnState = null;
		for (State<T> state : stateList) {
			if (state.stateID == ID) {
				returnState = state;
				return returnState;
			}
		}
		return returnState;
	}
	
	
	public void dumpStateMachineOnConsole() {
		System.out.println("Current State:  " + currentState.stateID);
		System.out.println("Default State: " + defaultState.stateID);
		
		for (State<T> state : stateList) {
			System.out.println("-State:  " + state.stateID);
			state.dumpTransitions();
		}
	}
	
	private class Transition<V>{
		private State<V> destinationState = null;
		private String transitionTrigger = null;
		public Transition(String transitionTrigger,State<V> destinationState) {
			this.destinationState =  destinationState;
			this.transitionTrigger = transitionTrigger;
			
		}
		
	}
	private class State<U>{
		public String stateID;
		private U data = null;
		private ArrayList<Transition<U>> transitions = null;
		
		public State(String stateID,U data){
			transitions = new ArrayList<Transition<U>>();
			this.data = data;
			this.stateID = stateID;
		}
		private ArrayList<Transition<U>> getAllTransitions(){
			return this.transitions;
		}
		
		public void addTransition(Transition<U> transition) {
			transitions.add(transition);	
		}
		
		public U getData() {
			return this.data;
		}
		public void dumpTransitions() {
			for (Transition<U> t : transitions) {
				System.out.println("===" + this.stateID + " --" + t.transitionTrigger + "--> " + t.destinationState.stateID);
			}
		}
		
	}

}
