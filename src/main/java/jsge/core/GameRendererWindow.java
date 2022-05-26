package jsge.core;
import javax.swing.JFrame;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

import jsge.managers.InputManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



@SuppressWarnings("serial")
public class GameRendererWindow extends JFrame implements ActionListener{
	int SCREEN_W = 640;
	int SCREEN_H = 480;
	
	int SCREEN_DEFAULT_SIZE_W = 640;
	int SCREEN_DEFAULT_SIZE_H = 480;
	
	private InputManager inputHandler = null;
	private RendererCanvas screenCanvas = null;
	private RendererManager rendererManager = null;
	private RenderingHints renderingHints = null;
	
	public GameRendererWindow(int X,int Y) {
		this.setResizable(false);
		
		inputHandler= new InputManager();
		rendererManager =  new RendererManager(this);
		addKeyListener(inputHandler);
		SCREEN_H = Y;
		SCREEN_W = X;
		if(X==0 || Y==0) {
			SCREEN_W = 640;
			SCREEN_H = 480;
		}
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//super.setSize(SCREEN_W, SCREEN_H);
		screenCanvas = new RendererCanvas();
		this.setLayout(new BorderLayout());
		
		this.add(screenCanvas,BorderLayout.CENTER);
		
		this.setUndecorated(false);
		this.setVisible(true);
		this.pack();
		
		
		renderingHints =  new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		
		System.out.println("Renderer Window Initialized");	
	}

	
	
	public void display() {
		screenCanvas.repaint();
	}
	public void renderGameObjects(ArrayList<GameObject> objectsToRender) {
		this.rendererManager.handleRendering(objectsToRender);
	}
	private void drawGameObject(GameObject gm) {
		screenCanvas.addToDrawnBuffer(gm);
	}
	
	public InputManager getInputHandler() {
		return this.inputHandler;
		
	}
	public void actionPerformed(ActionEvent e) {
		
		
	}

	
//Classe Interna para lidar com o Painel e fazer overrides de funções, como
//nunca sera utilizada fora de frame, fica como privada e subjulgada a classe do frame
	private class RendererManager {
		private GameRendererWindow window = null;
		private ArrayList<GameObject> BGLayer =  new ArrayList<GameObject>();
		private ArrayList<GameObject> UILayer =  new ArrayList<GameObject>();
		private ArrayList<GameObject> GAMEOBJECTLayer =  new ArrayList<GameObject>();
		private ArrayList<GameObject> PARTICLELayer =  new ArrayList<GameObject>();
		public RendererManager(GameRendererWindow renderInterface){
			this.window = renderInterface;
			
			
		}
		
		public void handleRendering(ArrayList<GameObject> objectsToRender) {
			
				filterLayers(objectsToRender);
				renderLayers();
				clearLayers();
				
				
			}
			
			
		private void filterLayers(ArrayList<GameObject> objectsToRender) {
			for(GameObject gm : objectsToRender) {
				switch(gm.getLayer()) {
				case BACKGROUND:
					BGLayer.add(gm);
					break;
				case UI:
					UILayer.add(gm);
					break;
				case GAMEOBJECT:
					GAMEOBJECTLayer.add(gm);
					break;
				case PARTICLE:
					PARTICLELayer.add(gm);
					break;
				}
			}
		}
		
		
		private void renderLayers() {
			//A ordem importa, primero é desenhado as layers de bg, dps gm, dps futuras particulas e dps UI
			for(GameObject gm : BGLayer) {
				window.drawGameObject(gm);
			}
			for(GameObject gm : GAMEOBJECTLayer) {
				window.drawGameObject(gm);
			}
			for(GameObject gm : PARTICLELayer) {
				window.drawGameObject(gm);
			}
			for(GameObject gm : UILayer) {
				window.drawGameObject(gm);
			}
		}
		
		private void clearLayers() {
			BGLayer.clear();
			GAMEOBJECTLayer.clear();
			PARTICLELayer.clear();
			UILayer.clear();
			
		}
	}

private class RendererCanvas extends JPanel{
	
	private ArrayList<GameObject> gameObjectList = new ArrayList<GameObject>();
	public RendererCanvas() {
		this.setPreferredSize(new Dimension(SCREEN_W,SCREEN_H));
		this.setBackground(new Color(0,0,0));
		this.setLayout(null);
		System.out.println("Canvas Initialized");
		}

	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.scale((float)this.getWidth()/(float)SCREEN_DEFAULT_SIZE_W,(float)this.getHeight()/(float)SCREEN_DEFAULT_SIZE_H);
		g2d.addRenderingHints(renderingHints);
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
	
	@SuppressWarnings("unused")
	public void clear() {
		gameObjectList.clear();
		repaint();
	}
	
	

	}

}


