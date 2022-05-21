package game.main.core;
import javax.swing.JFrame;
import java.util.ArrayList;
import javax.swing.JPanel;

import game.main.managers.InputManager;
import game.main.managers.RendererManager;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



@SuppressWarnings("serial")
public class RendererWindow extends JFrame implements ActionListener{
	int SCREEN_W = 800;
	int SCREEN_H = 800;
	private InputManager inputHandler = null;
	private RendererCanvas screenCanvas = null;
	private RendererManager rendererManager = null;
	
	public RendererWindow(int X,int Y) {
		inputHandler= new InputManager();
		rendererManager =  new RendererManager(this);
		addKeyListener(inputHandler);
		SCREEN_H = Y;
		SCREEN_W = X;
		if(X==0 || Y==0) {
			SCREEN_W = 800;
			SCREEN_H = 800;
		}
		
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setSize(SCREEN_W, SCREEN_H);
		super.setVisible(true);
		super.setResizable(false);
		screenCanvas = new RendererCanvas();
		this.add(screenCanvas);
		
		
		System.out.println("Renderer Window Initialized");	
	}

	
	
	public void display() {
		screenCanvas.repaint();
	}
	public void drawGameObject(GameObject gm) {
		screenCanvas.addToDrawnBuffer(gm);
	}
	
	public InputManager getInputHandler() {
		return this.inputHandler;
		
	}
	public RendererManager getRendererManager() {
		return this.rendererManager;
	}
	public void actionPerformed(ActionEvent e) {
		
		
	}

	
//Classe Interna para lidar com o Painel e fazer overrides de funções, como
//nunca sera utilizada fora de frame, fica como privada e subjulgada a classe do frame

private class RendererCanvas extends JPanel{
	
	private ArrayList<GameObject> gameObjectList = new ArrayList<GameObject>();
	public RendererCanvas() {
		this.setSize(SCREEN_W, SCREEN_H);
		this.setBackground(new Color(0,0,0));
		this.setLayout(null);
		System.out.println("Canvas Initialized");
		}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		//System.out.println("Screen has been painted");
		super.paintComponent(g2d);
		if(gameObjectList == null) {
			return;
		}
		
		for(int i = 0;i<gameObjectList.size();i++) {
		    	gameObjectList.get(i).draw(g2d);
			}
		Toolkit.getDefaultToolkit().sync();
		gameObjectList.clear();
			
		
		}
	
	public void addToDrawnBuffer(GameObject gm) {
		gameObjectList.add(gm);
	}
	
	public void clear() {
		//Deleta todo o buffer trocando a referencia, dps o GC cuida da memoria 
		gameObjectList.clear();
		repaint();
	}
	
	

	}

}


