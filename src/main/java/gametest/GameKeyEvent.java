package gametest;

public class GameKeyEvent {
	private char keyChar;
	private int keyCode;
	private EventType eventType;
	
	public GameKeyEvent(char keyChar,int keyCode,EventType eventType) {
		this.keyChar =  keyChar;
		this.keyCode = keyCode;
		this.eventType =  eventType;
	}
	public char getKeyChar() {return this.keyChar;}
	
	public EventType getEventType() {return this.eventType;}
	
	public int getKeyCode() {return this.keyCode;}
	
	public static enum EventType{
		Pressed,
		Released,
		
	}

}
