package jsge.data;
import java.util.*;


public class StateMachine <T> {
	private State<T> currentState;
	private State<T> defaultState;
	private State<T> exitState;
	private String lastTransitionTrigger = null; //Para evitar chamadas duplicadas
	
	private boolean debugMode = false;
	
	private List<State<T>> stateList;
	
	public StateMachine(boolean debugMode) {
		stateList =  new ArrayList<State<T>>();
		this.debugMode = debugMode;
		
	}
	
	//public StateMachine(StateMachine sm) {
	//	this = sm;
	//}
	
	public void forceStateChange(String stateID) {
		State<T> temp = findStatebyID(stateID);
		if(temp == null){
			if(debugMode) System.out.println("StateMachine: Warning - State not found");
			return;
		}
		else {
			currentState = temp;
		}
			
	}
	
	public void changeState(String transitionID) {
		Transition<T> temp = null;
		for (Transition<T> t : currentState.getAllTransitions()) {
			if(t.transitionTrigger.contentEquals(transitionID)) {
				temp =t;
			}
		} 
		if(temp == null) {
			
			if(transitionID == lastTransitionTrigger) { //checar msm objeto
				if(debugMode) System.out.println("StateMachine: Warning - Duplicated transition on StateMachine, Is This Intended Behaviour? - TransitionTriggerID = " + lastTransitionTrigger);
				return; //Evita error se for enviado a msm chamada diversas vezes
				
			}
			
			if(debugMode) {
				System.out.println("StateMachine: Warning - No transition matches this ID for the current state");
			}
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
		if(temp == null || debugMode) {
			System.out.println("StateMachine: Warning - State not found");
			return;
		}
		this.defaultState = temp;
	}
	public void setExitState(String stateID) {
		State<T> temp = this.findStatebyID(stateID);
		if(temp == null) {
			if(debugMode)System.out.println("StateMachine: Warning - State not found");
			return;
		}
		this.exitState = temp;
	}
	
	public void resetMachine() {
		this.currentState = this.defaultState;
		this.lastTransitionTrigger = null;
	}
	
	public String getCurrentStateID() {
		return this.currentState.stateID;
	}
	
	//Refatorar para generalizar a maquina
	public T getCurrentStateData() {
		if(this.currentState == null) {
			System.out.println("StateMachine: SEVERE WARNING - NULL POINTER IN STATEMACHINE DATA, THIS CAN LEAD TO FATAL ERRORS - STATEID: " + currentState.stateID);
			return this.currentState.getData();

		}
		return this.currentState.getData();
	}
	
	public void addTransition(String originStateID,String destinationStateID,String transitionTrigger) {
		State<T> originState = null;
		State<T> destinationState = null;
		if(destinationStateID.contentEquals(originStateID)){
			System.out.println("StateMachine: Warning - State Cannot make a transition to itself, Aborting insertion");

		}
		for (int i = 0; i < stateList.size(); i++) {
			if(stateList.get(i).stateID.contentEquals(originStateID)) {
				originState = stateList.get(i);
			}
			if(stateList.get(i).stateID.contentEquals(destinationStateID)) {
				destinationState = stateList.get(i);
			}
			
		}
		if(originState == null || destinationState == null) {
			System.out.println("StateMachine: Warning - One of the States was not found for transition");
		}
		for (Transition<T> t : originState.getAllTransitions()) {
			if(t.transitionTrigger == transitionTrigger || t.destinationState.equals(destinationState)) {
				System.out.println("StateMachine: Warning - Duplicated Transition from State " + originStateID + " to State " + destinationStateID + ", Aborting insertion");
				return;

			}
			
		}
		originState.getAllTransitions().add(new Transition<T>(transitionTrigger, destinationState));
	}
	
	public void addState(String stateName,T data,String sourceState,String trigger) {
		if(sourceState ==  null || stateList.size() == 0) {
			System.out.println("StateMachine: No state previously set, setting " + stateName + " as default state");

			sourceState = "origin";
			stateList.add(new State<T>(stateName,data));
			defaultState =  stateList.get(0);
			currentState =  defaultState;
			exitState = defaultState;
			
		}
		else {
			State<T> originState = null;
			
			for (int i = 0; i < stateList.size(); i++) {
				if(stateList.get(i).stateID.contentEquals(stateName)) {
					System.out.println("StateMachine: Warning - Failed to create state in State in StateMachine, Duplicated stateID");
					break;
				}
				if(stateList.get(i).stateID.contentEquals(sourceState)) {
					originState = stateList.get(i);
				}
				
			}
			
			if(originState == null) {
				System.out.println("StateMachine: Warning - Failed to create state in State in StateMachine, Origin state not found");

			}
			State<T> temp = new State<T>(stateName,data);
			stateList.add(temp);
			originState.addTransition(new Transition<T>(trigger,temp));
		}
		
		
	}
	
	private State<T> findStatebyID(String ID) {
		State<T> returnState = null;
		for (State<T> state : stateList) {
			if (state.stateID.contentEquals(ID)) {
				returnState = state;
				return returnState;
			}
		}
		return returnState;
	}
	
	
	public void dumpStateMachineOnConsole() {
		System.out.println("StateMachine: Current State:  " + currentState.stateID);
		System.out.println("StateMachine: Default State: " + defaultState.stateID);
		
		for (State<T> state : stateList) {
			System.out.println("StateMachine: -State:  " + state.stateID);
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
			if(this.data == null) {
				System.out.println("StateMachine: SEVERE WARNING - NULL POINTER LOADED IN STATEMACHINE DATA, THIS CAN LEAD TO FATAL ERRORS - STATEID: " + this.stateID);

			}
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
				System.out.println("StateMachine: ===" + this.stateID + " --" + t.transitionTrigger + "--> " + t.destinationState.stateID);
			}
		}
		
	}

}
