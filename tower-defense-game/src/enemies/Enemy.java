package enemies;

import java.awt.Rectangle;
import java.util.List;

import enums.Directions;
import enums.EnemyTypes;
import helpers.Algorithms;
import helpers.Algorithms.Cell;
import helpers.MapTile;
import managers.EnemyManager;

/**
 * This abstract class represents an enemy in the game.
 */
public abstract class Enemy {
	protected float speed;
	private int lastX;
	private int lastY;
	private int xOffset;
	private int yOffset;
	private float x;
	private float y;
	private Rectangle hitbox;
	protected int health;
	protected int maxHealth;
	public int iterator;
	private List<Cell> roadToCamp;
	private EnemyTypes type;
	private Directions lastDir;
	private boolean isAlive;
	protected int reward;
	private EnemyManager enemyManager;

	protected Enemy(MapTile[][] map, float x, float y, int[] dest, EnemyTypes type, EnemyManager enemyManager) {
		this.enemyManager = enemyManager;
		this.type = type;
		this.x = x;
		this.y = y;
		this.hitbox = new Rectangle((int) x, (int) y, 36, 36);
		this.iterator = 0;
		int[] start = { (int) this.y / 32, (int) this.x / 32 };
		this.roadToCamp = Algorithms.shortestPath(map, start, dest);

		this.lastDir = roadToCamp.get(0).getDir();

		switch (lastDir) {
		case LEFT:
			this.xOffset = -16;
			break;
		case UP:
			this.yOffset = -16;
			break;
		default:
			this.xOffset = 0;
			this.yOffset = 0;
			break;
		}

		this.lastX = (int) this.x / 32;
		this.lastY = (int) this.y / 32;

		this.isAlive = true;
	}

	/**
	 * Moves the enemy into the given direction with the given speed.
	 * 
	 * @param speed The speed of the enemy.
	 * @param direction The direction the enemy should move.
	 */
	public void move(float speed, Directions direction) {
		Directions dir = direction == null ? lastDir : direction;
		switch (dir) {
		case DOWN:
			this.y += speed;
			break;
		case LEFT:
			this.x -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case UP:
			this.y -= speed;
			break;
		default:
			break;
		}

		updateHitBox();
	}

	/**
	 * Updates the enemys hitbox.
	 * 
	 */
	public void updateHitBox() {
		hitbox.x = (int) (x+xOffset);
		hitbox.y = (int) (y+yOffset);
	}

	/**
	 * Reduces the health of the enemy with a given amount.
	 * 
	 * @param damage Amount to reduce from health.
	 */
	public void hurt(int dmg) {
		health -= dmg;
		if (health <= 0) {
			isAlive = false;
			enemyManager.rewardPlayer(this.reward);
		}
	}

	/**
	 * Kills the enemy.
	 * 
	 */
	public void kill() {
		isAlive = false;
		health = 0;
	}

	public int getMissingHealth() {
		return maxHealth - health;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}

	public int getHealth() {
		return health;
	}

	public List<Cell> getRoadToCamp() {
		return roadToCamp;
	}

	public void setRoadToCamp(List<Cell> roadToCamp) {
		this.roadToCamp = roadToCamp;
	}

	public int getLastX() {
		return lastX;
	}

	public void setLastX(int lastX) {
		this.lastX = lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public void setLastY(int lastY) {
		this.lastY = lastY;
	}

	public EnemyTypes getType() {
		return type;
	}

	public Directions getLastDir() {
		return lastDir;
	}

	public void setLastDir(Directions lastDir) {
		this.lastDir = lastDir;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public float getSpeed() {
		return speed;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
}
