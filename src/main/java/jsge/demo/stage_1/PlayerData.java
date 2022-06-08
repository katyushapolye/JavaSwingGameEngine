//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END

package jsge.demo.stage_1;

public class PlayerData {
	private static int currentScore = 0;
	//private static int playerLives = 3;
	
	
	public static void addScore(int scoreToAdd) {
		
		//marshall to UI;
		Stage_1_Scene.updatePlayerScoreUI();
		currentScore += scoreToAdd;
	}
	
	public static int getScore() {
		return currentScore;
	}
	
	
	public static void savePlayerScore() {
		//dump to file <- criptografy please . . .
		
	}

}
