package enemies;

import enums.EnemyTypes;
import enums.GameDifficulties;
import helpers.MapTile;
import managers.EnemyManager;

/**
 * This class represents a Bee (enemy) in the game.
 * The class inherits from the Enemy class.
 * 
 */
public class Bee extends Enemy {
	public Bee(MapTile[][] map, int x, int y, int[] dest, EnemyTypes type, EnemyManager enemyManager) {
		super(map, x, y, dest, type, enemyManager);
		// change max health based on game difficulty
		int maxHealth = GameDifficulties.gameDifficulty == GameDifficulties.HARD ? 25 : 20;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		this.speed = 0.8f;
		this.reward = 5;
	}
}
