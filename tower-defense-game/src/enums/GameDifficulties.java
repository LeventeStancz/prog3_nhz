package enums;

import main.GameSettings;

/**
 * This enum represents the type of difficulties in the game.
 * 
 */
public enum GameDifficulties {
	EASY, NORMAL, HARD;

	public static GameDifficulties gameDifficulty = NORMAL;
	
	public static void setGameDifficulty(GameDifficulties state) {
		gameDifficulty = state;
		GameSettings.saveSettings();
	}
}