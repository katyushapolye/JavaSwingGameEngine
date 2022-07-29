//Identificação START
//Raphael Nogueira Rezende Laroca Pinto - 202135014
//Antônio Marcos da Silva Júnior -  202135002
//Identificação END

package jsge.demo.stage_1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerData {
	private static int currentScore = 0;
	private static int playerLives = 3;
	
	public final static String dataFilePath = "playerData.dat";
	
	
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
	
	public static void savePlayerScore(String playerName) {
		
		//check for existing file
		
		try {
		FileWriter f = new FileWriter(dataFilePath,true);
		
		f.append(playerName);
		f.append("-");
		f.append( String.valueOf(PlayerData.getScore()));
		
		f.append(System.getProperty("line.separator"));
		f.close();
		}
		catch(Exception e) {
			
			
		}
		
		
		//dump to file <- criptografy please . . .
		
	}
	
	public static void resetPlayerData() {
		currentScore = 0;
		playerLives = 3;
	}

}
