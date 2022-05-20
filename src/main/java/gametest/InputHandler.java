package gametest;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
	public InputHandler() {
		return;
	};
	
	public void keyReleased(KeyEvent e) {
		System.out.println("KEY RELEASED: " + e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		System.out.println("KEY PRESSED: " + e.getKeyChar());
		
		
	}

	public void keyTyped(KeyEvent e) {
		return;
		
	}

	

	
	
	
}
