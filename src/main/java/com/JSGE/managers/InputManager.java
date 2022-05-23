package com.JSGE.managers;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import com.JSGE.core.GameKeyEvent;

public class InputManager implements KeyListener {
	ArrayList<GameKeyEvent> keyEvents =  new ArrayList<GameKeyEvent>();
	public InputManager() {
		
		return;
	};
	
	
	//Possivel otimização para eventos encavalados
	public void keyPressed(KeyEvent e) {
		
		keyEvents.add(new GameKeyEvent(e.getKeyChar(),e.getKeyCode(),GameKeyEvent.EventType.Pressed));
		}

	public void keyReleased(KeyEvent e) {
		keyEvents.add(new GameKeyEvent(e.getKeyChar(),e.getKeyCode(),GameKeyEvent.EventType.Released));

		}
	
	
	public boolean isPoolingDone() {
		if(keyEvents.size() == 0) { 
			return false;
		}
		else{
			return true;	
			}
		}
	
	
	public GameKeyEvent poolEvent() {
		if(keyEvents.size() == 0) {
			return null;
		}
		return keyEvents.remove(0); //Retorna o elemento removido, estrutura de fila
		
	}

	public void keyTyped(KeyEvent e) {
		
		return;
	}

	

	
	
	
}
