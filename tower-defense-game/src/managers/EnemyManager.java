package managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import enemies.Bee;
import enemies.Enemy;
import enemies.Ogre;
import enemies.Wolf;
import enums.Directions;
import enums.EnemyTypes;
import enums.MapTiles;
import helpers.Algorithms;
import helpers.Point;
import scenes.Play;

/**
 * This class manages Enemies in a List.
 * 
 */
public class EnemyManager {
	private Play playScene;
	private BufferedImage[] enemyImgs;
	private ArrayList<Enemy> enemies = new ArrayList<>();

	public EnemyManager(Play playScene) {
		this.playScene = playScene;
		this.enemyImgs = new BufferedImage[3];
		loadEnemyImgs();
	}
	
	public EnemyManager() {
	}

	/**
	 * Loads all enemy images.
	 * 
	 */
	private void loadEnemyImgs() {
		enemyImgs[0] = ResourceManager.getEnemyImg("wolf", "D_Walk").getSubimage(10, 14, 32, 32);
		enemyImgs[1] = ResourceManager.getEnemyImg("ogre", "D_Walk").getSubimage(10, 14, 32, 32);
		enemyImgs[2] = ResourceManager.getEnemyImg("bee", "D_Walk").getSubimage(10, 14, 32, 32);
	}

	/**
	 * Updates all enemies inside the list.
	 * 
	 */
	public void update() {
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				updateMove(enemy);
			}
		}
	}

	/**
	 * Updates an enemys move.
	 * 
	 * @param enemy The enemy to update it's move for.
	 */
	private void updateMove(Enemy enemy) {
		if (enemy.iterator < enemy.getRoadToCamp().size()) {
			int newX = (int) enemy.getX() / 32;
			int newY = (int) enemy.getY() / 32;

			if (enemy.getRoadToCamp().get(enemy.iterator).getDir().equals(Directions.LEFT)) {
				newX = (int) (enemy.getX() + 16) / 32;
			}

			if (enemy.getRoadToCamp().get(enemy.iterator).getDir().equals(Directions.UP)) {
				newY = (int) (enemy.getY() + 16) / 32;
			}

			if (newX != enemy.getLastX() || newY != enemy.getLastY()) {
				enemy.iterator++;
			} else {
				enemy.move(enemy.getSpeed(), enemy.getRoadToCamp().get(enemy.iterator).getDir());
			}

			enemy.setLastX(newX);
			enemy.setLastY(newY);
			if (enemy.iterator < enemy.getRoadToCamp().size()) {
				enemy.setLastDir(enemy.getRoadToCamp().get(enemy.iterator).getDir());
			}
		} else {
			enemy.kill();
			playScene.reduceHeroLife();
		}
	}

	/**
	 * Spawns an enemy with the given type.
	 * 
	 * @param nextEnemy The enemy type.
	 */
	public void spawnEnemy(EnemyTypes nextEnemy) {
		addEnemy(nextEnemy);
	}

	/**
	 * Adds an enemy to the List.
	 * 
	 * @param enemyType The type of the enemy.
	 */
	public void addEnemy(EnemyTypes enemyType) {
		Point start = findRandomStartPoint();
		int x = start.x * 32;
		int y = start.y * 32;

		if ((x / 32 >= 0 || y / 32 >= 0) && (x / 32 < 21 && y / 32 < 21)
				&& playScene.getMap()[y / 32][x / 32].getType().equals(MapTiles.ROAD)) {
			switch (enemyType) {
			case WOLF:
				Wolf wolf = new Wolf(playScene.getMap(), x, y, new int[] {11, 10}, EnemyTypes.WOLF, this);
				if(playScene.getWaveManager().getWaveIndex() > (playScene.getWaveManager().getWaves().size()/2)) {
					float oldSpeed = wolf.getSpeed();
					wolf.setSpeed((oldSpeed + 0.2f));
					int oldMaxHealth = wolf.getMaxHealth();
					wolf.setMaxHealth((oldMaxHealth + 10));
					wolf.setHealth(wolf.getMaxHealth());
				}
				enemies.add(wolf);
				break;
			case OGRE:
				Ogre ogre = new Ogre(playScene.getMap(), x, y, new int[] {11, 10}, EnemyTypes.OGRE, this);
				if(playScene.getWaveManager().getWaveIndex() > (playScene.getWaveManager().getWaves().size()/2)) {
					float oldSpeed = ogre.getSpeed();
					ogre.setSpeed((oldSpeed + 0.35f));
					int oldMaxHealth = ogre.getMaxHealth();
					ogre.setMaxHealth((oldMaxHealth + 20));
					ogre.setHealth(ogre.getMaxHealth());
				}
				enemies.add(ogre);
				break;
			case BEE:
				Bee bee = new Bee(playScene.getMap(), x, y, new int[] {11, 10}, EnemyTypes.BEE, this);
				if(playScene.getWaveManager().getWaveIndex() > (playScene.getWaveManager().getWaves().size()/2)) {
					float oldSpeed = bee.getSpeed();
					bee.setSpeed((oldSpeed + 0.4f));
					int oldMaxHealth = bee.getMaxHealth();
					bee.setMaxHealth((oldMaxHealth + 15));
					bee.setHealth(bee.getMaxHealth());
				}
				enemies.add(bee);
				break;
			}
		}
	}

	/**
	 * Finds a random starting point for the enemy.
	 * 
	 * @return Random point in the 2D array.
	 */
	private Point findRandomStartPoint() {
		List<Point> points = new ArrayList<>();
		Algorithms.hasOutsideConnection(playScene.getMap(), points);
		Random random = new Random();
		int randomIndex = random.nextInt(points.size());
		return points.get(randomIndex);
	}

	/**
	 * Draws the enemies to the screen.
	 * 
	 */
	public void draw(Graphics g) {
		for (Enemy enemy : enemies) {
			if (enemy.isAlive()) {
				switch (enemy.getType()) {
				case BEE:
					drawEnemy(enemyImgs[2], enemy, g);
					break;
				case OGRE:
					drawEnemy(enemyImgs[1], enemy, g);
					break;
				case WOLF:
					drawEnemy(enemyImgs[0], enemy, g);
					break;
				}
			}
		}
	}

	/**
	 * Draws an enemy with the given image and object.
	 * 
	 * @param enemyImg The image of the enemy.
	 * @param enemy The enemy object.
	 */
	private void drawEnemy(BufferedImage enemyImg, Enemy enemy, Graphics g) {
		if (enemy.iterator < enemy.getRoadToCamp().size()) {
			switch (enemy.getRoadToCamp().get(enemy.iterator).getDir()) {
			case LEFT:
				enemy.setxOffset(-16);
				g.drawImage(enemyImg, ((int) enemy.getX() + enemy.getxOffset()),
						((int) enemy.getY() + enemy.getyOffset()), null);
				break;
			case UP:
				enemy.setyOffset(-16);
				g.drawImage(enemyImg, ((int) enemy.getX() + enemy.getxOffset()),
						((int) enemy.getY() + enemy.getyOffset()), null);
				break;
			case RIGHT:
				enemy.setxOffset(0);
				g.drawImage(enemyImg, ((int) enemy.getX() + enemy.getxOffset()),
						((int) enemy.getY() + enemy.getyOffset()), null);
				break;
			case DOWN:
				enemy.setyOffset(0);
				g.drawImage(enemyImg, ((int) enemy.getX() + enemy.getxOffset()),
						((int) enemy.getY() + enemy.getyOffset()), null);
				break;
			}
		}
		g.drawImage(enemyImg, ((int) enemy.getX() + enemy.getxOffset()), ((int) enemy.getY() + enemy.getyOffset()),
				null);
		g.setColor(Color.RED);
	
		int width = enemy.getHealth() > 40 ? enemy.getHealth()/2 : enemy.getHealth();
		int height =  enemy.getHealth() > 40 ? 6 : 3;
		g.fillRect(
			(int)enemy.getX() + enemy.getxOffset(),
		    (int)(enemy.getY() + enemy.getyOffset() - 5),
		    width,
		    height
		);


	}
	
	/**
	 * Draws an enemy with the given image and object.
	 * 
	 * @param reward The amount to add.
	 */
	public void rewardPlayer(int reward) {
		playScene.getPlayBar().addGold(reward);
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public int getAliveEnemies() {
		int size = 0;
		for(Enemy e : enemies) {
			if(e.isAlive()) {
				size++;
			}
		}
		return size;
	}

}
