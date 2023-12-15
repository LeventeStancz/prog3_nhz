package enums;

/**
 * This enum represents the type of game states in the game.
 * 
 */
public enum GameStates {
	MENU, PLAY, EDITING, SETTINGS, EXIT, GAMEOVER;

	public static GameStates gameState = MENU;
	public static GameStates lastGameState = MENU;
	
	public static void setGameState(GameStates newState) {
		lastGameState = gameState;
		gameState = newState;
	}
}
