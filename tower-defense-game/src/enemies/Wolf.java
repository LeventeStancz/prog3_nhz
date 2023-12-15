package enemies;

import enums.EnemyTypes;
import enums.GameDifficulties;
import helpers.MapTile;
import managers.EnemyManager;

/**
 * This class represents a Wolf (enemy) in the game.
 * The class inherits from the Enemy class.
 * 
 */
public class Wolf extends Enemy {
	public Wolf(MapTile[][] map, int x, int y, int[] dest, EnemyTypes type, EnemyManager enemyManager) {
		super(map, x, y, dest, type, enemyManager);
		// change max health based on the game difficulty
		int maxHealth = GameDifficulties.gameDifficulty == GameDifficulties.HARD ? 50 : 40;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.speed =  0.5f;
		this.reward = 10;
	}

}
