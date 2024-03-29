//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END
package jsge.utils;

public class GameState {
	public static enum GameStates{
		Running, //Everything updates
		Halted, //Only input is received
		Paused, //Collisions are not checked, animations will depend on the tag you've put, update calls normally for GO's
		Exit, //will terminate the game at the end of the tick it was set to
	}

}
