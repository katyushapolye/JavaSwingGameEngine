//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END

package jsge.demo.stage_1;

public class PlayerData {
	private static int currentScore = 0;
	private static int playerLives = 0;
	
	
	public static void addScore(int scoreToAdd) {
		
		//marshall to UI;
		Stage_1_Scene.updatePlayerScoreUI();
		currentScore += scoreToAdd;
	}
	
	public static int getScore() {
		return currentScore;
	}
	
	public static int getLifes()
	{
		return playerLives;
	}
	
	public static void decreaseLife() {
		playerLives--;
	}
	
	public static void savePlayerScore() {
		//dump to file <- criptografy please . . .
		
	}
	
	public static void resetPlayerData() {
		currentScore = 0;
		playerLives = 0;
	}

}
