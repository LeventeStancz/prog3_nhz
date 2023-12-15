package managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import allies.Ally;
import enemies.Enemy;
import helpers.Projectile;
import scenes.Play;

/**
 * This class manages a List of Projectiles.
 * 
 */
public class ProjectileManager {
	private Play playScene;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private BufferedImage arrowImg;
	
	public ProjectileManager(Play playScene) {
		this.playScene = playScene;
		loadArrowImg();
	}
	
	private void loadArrowImg() {
		arrowImg = ResourceManager.getArrowImg();
	}
	
	/**
	 * Calculates and adds a new projectile to the list.
	 * 
	 * @param ally The ally that shoots the projectile.
	 * @param enemy The enemy that is the target.
	 */
	public void newProjectile(Ally ally, Enemy enemy) {
		int xDist = (int) (ally.getX() - (enemy.getX()+enemy.getxOffset()));
		int yDist = (int) (ally.getY() - (enemy.getY()+enemy.getyOffset()));
		int totDist = Math.abs(xDist) + Math.abs(yDist);

		float xPer = (float) Math.abs(xDist) / totDist;

		float xSpeed = xPer * 8f;
		float ySpeed = 8f - xSpeed;

		if (ally.getX() > (enemy.getX()+enemy.getxOffset()))
			xSpeed *= -1;
		if (ally.getY() > (enemy.getY()+enemy.getyOffset()))
			ySpeed *= -1;

		float rotate = 0;
		
		float arcValue = (float) Math.atan(yDist / (float) xDist);
		rotate = (float) Math.toDegrees(arcValue);

		if (xDist < 0)
			rotate += 180;
		
		projectiles.add(new Projectile(ally.getX() + 16, ally.getY() + 16, xSpeed, ySpeed, ally.getDamage(), rotate));
	}

	/**
	 * Updates the projectiles in the list.
	 * 
	 */
	public void update() {
		List<Projectile> projToRemove = new ArrayList<>();
		for(Projectile proj : projectiles) {
			if(proj.isActive()) {
				proj.move();
				if(isProjectileHittingEnemy(proj) || isProjectileOutOfBounds(proj)) {
					proj.setActive(false);
					projToRemove.add(proj);
					break;
				}
			}
		}
		//remove inactive projectile
		if(!projToRemove.isEmpty()) {
			projectiles.removeAll(projToRemove);
		}
	}
	
	/**
	 * Checks if a projectile is out of bound from the screen.
	 * 
	 * @param proj The projectile to check.
	 * @return projectile is out of bounds.
	 */
	private boolean isProjectileOutOfBounds(Projectile proj) {
		return proj.getPos().x <= 0 || proj.getPos().x >= 640 || proj.getPos().y <= 0 || proj.getPos().y >= 640;
	}

	/**
	 * Checks if a certain projectile is hitting an enemy.
	 * 
	 * @param proj The projectile to check.
	 * @return Projectile hitting an enemy.
	 */
	private boolean isProjectileHittingEnemy(Projectile proj) {
		for(Enemy e : playScene.getEnemyManager().getEnemies()) {
			if(e.getHitbox().contains(proj.getPos()) && e.isAlive()) {
				e.hurt(proj.getDamage());
				return true;
			}
		}
		return false;
	}

	/**
	 * Draws the projectiles to the screen.
	 * 
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for(Projectile proj : projectiles) {
			if(proj.isActive() && !isProjectileOutOfBounds(proj)) {
				g2d.translate(proj.getPos().x, proj.getPos().y);
				g2d.rotate(Math.toRadians(proj.getRotation()));
				g2d.drawImage(arrowImg, -16, -16, null);
				g2d.rotate(-Math.toRadians(proj.getRotation()));
				g2d.translate(-proj.getPos().x, -proj.getPos().y);
			}
		}
	}
}
