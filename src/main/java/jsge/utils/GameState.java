package jsge.utils;

public class GameState {
	public static enum GameStates{
		Running, //Everything updates
		Halted, //Only input is received
		Paused, //Collisions are not checked, animations will depend on the tag you've put, update calls normally for GO's
		Exit,
	}

}
