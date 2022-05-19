package gametest;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;



@SuppressWarnings("serial")
public class RendererPanel extends JFrame {
	final int SCREEN_W = 800;
	final int SCREEN_H = 800;
	
	private RendererCanvas screenCanvas = null;
	
	public RendererPanel() {
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setSize(SCREEN_W, SCREEN_H);
		super.setVisible(true);
		screenCanvas = new RendererCanvas();
		this.add(screenCanvas);
		
		System.out.println("Renderer Window Initialized");	
	}

	
	public void DrawGameObjects(GameObject[] gm) {
		
		screenCanvas.parseGameObjects(gm);
	}
	
	public void display() {
		screenCanvas.repaint();
	}
	
	public void clearScreen() {
		screenCanvas.clear();
		
	}

	
//Classe Interna para lidar com o Painel e fazer overrides de funções, como
//nunca sera utilizada fora de frame, fica como privada e subjulgada a classe do frame

private class RendererCanvas extends JPanel{
	
	private GameObject[] gmBuffer = new GameObject[0];
	public RendererCanvas() {
		this.setSize(SCREEN_W, SCREEN_H);
		this.setBackground(new Color(255,0,0));
		System.out.println("Canvas Initialized");
		}

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("Screen has been painted");
		super.paintComponent(g);
		
		for(int i = 0;i<gmBuffer.length;i++) {
			gmBuffer[i].draw(g);
			}
		Toolkit.getDefaultToolkit().sync();
			
		
		}
	public void parseGameObjects(GameObject[] buffer) {
		gmBuffer = buffer;
	}
	
	public void clear() {
		//Deleta todo o buffer trocando a referencia, dps o GC cuida da memoria < MEXER O GC N DA CONTA, TROCAR POR LISTA
		gmBuffer = new GameObject[0];
		repaint();
	}
	
	



}
}


