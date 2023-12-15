package allies;

import enums.Allies;
import enums.GameDifficulties;

/**
 * This class represents an ally (archer) in the game.
 */
public class Ally {
	private float x;
	private float y;
	private Allies type;
	private float cooldown;
	private float range;
	private int damage;
	private int cooldownTick;
	private int cost = 30;
	private int tier = 1;
	private int upgradeCost = 100;
	private int id;

	public Ally(float x, float y, Allies type, int id) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.id = id;

		this.cooldown = 60;
		this.range = GameDifficulties.gameDifficulty == GameDifficulties.HARD ? 90 : 100;
		this.damage =  5;
	}
	
	/**
	 * Checks if this ally can be upgraded.
	 *
	 * @return ally can be upgraded.
	 */
	public boolean canUpgradeTier() {
		return tier < 4;
	}
	
	/**
	 * Upgrades ally to the next tier. Upgrading an ally increases the upgrade cost,
	 * and the range of the ally based on the tier.
	 * 
	 */
	public void upgradeTier() {
		if(tier < 4) {
			tier++;
			upgradeCost = upgradeCost + 50;
			range = range + tier * 2;
		}
	}
	
	/**
	 * Method for updating the cooldownTick property.
	 * 
	 */
	public void update() {
		cooldownTick++;
	}
	
	/**
	 * Checks if the cooldown has ended for this ally.
	 * 
	 * @return cooldown has ended.
	 */
	public boolean isCooldownOver() {
		return cooldownTick >= cooldown;
	}
	
	/**
	 * Resets the allys cooldown.
	 * 
	 */
	public void resetCooldown() {
		cooldownTick = 0;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Allies getType() {
		return type;
	}

	public void setType(Allies type) {
		this.type = type;
	}

	public float getCooldown() {
		return cooldown;
	}

	public void setCooldown(float cooldown) {
		this.cooldown = cooldown;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCost() {
		return cost;
	}

	public int getUpgradeCost() {
		return upgradeCost;
	}
	
	public int getId() {
		return id;
	}

	public int getTier() {
		return tier;
	}

}
