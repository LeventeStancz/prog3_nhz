package helpers;

import java.awt.geom.Point2D;

/**
 * This class represents a Projectile (arrow) in the game.
 * 
 */
public class Projectile {
	private Point2D.Float pos;
	private boolean active = true;
	private float xSpeed;
	private float ySpeed;
	private int damage;
	private float rotation;
	
	public Projectile(float x, float y, float xSpeed, float ySpeed, int damage, float rotation) {
		pos = new Point2D.Float(x,y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.damage = damage;
		this.rotation = rotation;
	}
	
	/**
	 * Moves the projectile based on the projectile speed.
	 * 
	 */
	public void move() {
		pos.x += xSpeed;
		pos.y += ySpeed;
	}

	public Point2D.Float getPos() {
		return pos;
	}

	public void setPos(Point2D.Float pos) {
		this.pos = pos;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
