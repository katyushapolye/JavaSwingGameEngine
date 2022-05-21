package gametest;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputHandler implements KeyListener {
	ArrayList<GameKeyEvent> keyEvents =  new ArrayList<GameKeyEvent>();
	public InputHandler() {
		
		return;
	};
	
	
	//Possivel otimização para eventos incavalados
	public void keyPressed(KeyEvent e) {
		keyEvents.add(new GameKeyEvent(e.getKeyChar(),GameKeyEvent.EventType.Pressed));
		}

	public void keyReleased(KeyEvent e) {
		keyEvents.add(new GameKeyEvent(e.getKeyChar(),GameKeyEvent.EventType.Released));

		}
	
	
	public boolean isPoolingDone() {
		if(keyEvents.size() == 0) { 
			return false;
		}
		else{
			return true;	
		}
		}
	
	
	public GameKeyEvent poolChar() {
		if(keyEvents.size() == 0) {
			return null;
		}
		GameKeyEvent temp = keyEvents.remove(0);
		return temp;
	}

	public void keyTyped(KeyEvent e) {
		
		return;
	}

	

	
	
	
}
