package gametest;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputHandler implements KeyListener {
	ArrayList<GameKeyEvent> keysPressed =  new ArrayList<GameKeyEvent>();
	public InputHandler() {
		
		return;
	};
	
	
	//Possivel otimização para eventos incavalados
	public void keyPressed(KeyEvent e) {
		keysPressed.add(new GameKeyEvent(e.getKeyChar(),GameKeyEvent.EventType.Pressed));
		}

	public void keyReleased(KeyEvent e) {
		keysPressed.add(new GameKeyEvent(e.getKeyChar(),GameKeyEvent.EventType.Released));

		}
	
	
	public boolean isPoolingDone() {
		if(keysPressed.size() == 0) {
			return false;
		}
		else{
			return true;	
		}
	}
	
	public GameKeyEvent poolChar() {
		if(keysPressed.size() == 0) {
			return null;
		}
		GameKeyEvent temp = keysPressed.remove(0);
		return temp;
	}

	public void keyTyped(KeyEvent e) {
		
		return;
	}

	

	
	
	
}
