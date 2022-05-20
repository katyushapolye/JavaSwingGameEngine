package gametest;

public class GameKeyEvent {
	private char keyChar;
	private EventType eventType;
	
	public GameKeyEvent(char keyChar,EventType eventType) {
		this.keyChar =  keyChar;
		this.eventType =  eventType;
	}
	public char getKeyChar() {return this.keyChar;}
	
	public EventType getEventType() {return this.eventType;}
	
	static enum EventType{
		Pressed,
		Released,
		
	}

}
