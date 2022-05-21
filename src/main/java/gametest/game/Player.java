package gametest.game;
import gametest.GameKeyEvent;
import gametest.GameObject;
import gametest.Utils.Layer;

public class Player extends GameObject{
	
	public Player(String PathToImageFile,int X,int Y,int rotation) {
		super(PathToImageFile, X, Y,rotation,Layer.GAMEOBJECT);
		
		
	}
	
	
	public void sendInput(GameKeyEvent e) {
	
		
	}
		
	


}
