package enemies;

import enums.EnemyTypes;
import enums.GameDifficulties;
import helpers.MapTile;
import managers.EnemyManager;

/**
 * This class represents an Ogre (enemy) in the game.
 * The class inherits from the Enemy class.
 * 
 */
public class Ogre extends Enemy {
	public Ogre(MapTile[][] map, int x, int y, int[] dest, EnemyTypes type, EnemyManager enemyManager) {
		super(map, x, y, dest, type, enemyManager);
		// change max health based on the game difficulty
		int maxHealth = GameDifficulties.gameDifficulty == GameDifficulties.HARD ? 100 : 60;
		this.maxHealth = maxHealth;
		this.health = maxHealth;
		// change speed based on the game difficulty
		float speed = GameDifficulties.gameDifficulty == GameDifficulties.HARD ? 0.4f : 0.3f;
		this.speed = speed;
		this.reward = 25;
	}

}
