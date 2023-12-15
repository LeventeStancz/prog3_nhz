package enums;

import main.GameSettings;

/**
 * This enum represents the type of game levels in the game.
 * 
 */
public enum GameLevels {
	TWORIVERS, CUSTOM_LEVEL;

	public static GameLevels gameLevel = TWORIVERS;

	public static void setGameLevel(GameLevels level) {
		gameLevel = level;
		GameSettings.saveSettings();
	}
}
