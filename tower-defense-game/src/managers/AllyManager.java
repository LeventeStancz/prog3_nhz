package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import allies.Ally;
import enemies.Enemy;
import enums.Allies;
import scenes.Play;

/**
 * This class manages a List of Ally.
 * 
 */
public class AllyManager {
	private Play playScene;
	private BufferedImage allyImg;
	private ArrayList<Ally> allies = new ArrayList<>();
	private int allyId = 0;

	public AllyManager(Play playScene) {
		this.playScene = playScene;

		loadAllyImg();
	}

	/**
	 * Loads images for the Ally-s.
	 * 
	 */
	private void loadAllyImg() {
		allyImg = ResourceManager.getAlly(Allies.ALLY).getSubimage(8, 0, 32, 32);
	}

	/**
	 * Draws all Allies in the list to the screen.
	 * 
	 */
	public void draw(Graphics g) {
		for (Ally ally : allies) {
			g.drawImage(allyImg, (int) ally.getX(), (int) ally.getY(), null);
		}
	}

	/**
	 * Adds a new Ally to a specified locatin.
	 * 
	 * @param selectedAlly The Ally type.
	 * @param xPos Position of the x.
	 * @param yPos Position of the y.
	 */
	public void addAlly(Allies selectedAlly, int xPos, int yPos) {
		allies.add(new Ally(xPos, yPos, selectedAlly, allyId++));
	}

	/**
	 * Updates Allies in the list.
	 * 
	 */
	public void update() {
		for (Ally ally : allies) {
			ally.update();
			attackEnemyInRange(ally);
		}
	}

	/**
	 * Attacks an enemy in range.
	 * 
	 * @param ally The Ally that attacks.
	 */
	private void attackEnemyInRange(Ally ally) {
		for (Enemy enemy : playScene.getEnemyManager().getEnemies()) {
			if (isEnemyInRange(ally, enemy) && enemy.isAlive() && ally.isCooldownOver()) {
				playScene.shootEnemy(ally, enemy);
				ally.resetCooldown();
			}
		}
	}

	/**
	 * Checks is a certain enemy is in range of a certain ally.
	 * 
	 * @param ally The ally.
	 * @param enemy The enemy.
	 * @return enemy is in range of ally.
	 */
	private boolean isEnemyInRange(Ally ally, Enemy enemy) {
		int xDiff = Math.abs((int) ally.getX() - (int) enemy.getX());
		int yDiff = Math.abs((int) ally.getY() - (int) enemy.getY());

		int range = (int) Math.hypot(xDiff, yDiff);

		return range < ally.getRange();
	}

	public BufferedImage getAllyImg() {
		return allyImg;
	}

	public Ally getAllyAt(int colNum, int rowNum) {
		for (Ally ally : allies) {
			if (((int) (ally.getX() / 32)) == rowNum && ((int) (ally.getY() / 32)) == colNum) {
				return ally;
			}
		}

		return null;
	}

	public Ally getDummyAlly(Allies allyType) {
		return new Ally(0, 0, allyType, -1);
	}

	public Ally getAllyById(int id) {
		for(Ally a : allies) {
			if(a.getId() == id) {
				return a;
			}
		}
		return null;
	}

}
