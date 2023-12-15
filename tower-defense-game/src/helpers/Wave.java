package helpers;

import java.util.ArrayList;

import enums.EnemyTypes;

/**
 * This class represents a wave in the game.
 * 
 */
public class Wave {
	private ArrayList<EnemyTypes> enemyList;
	
	public Wave(ArrayList<EnemyTypes> enemyList) {
		this.enemyList = enemyList;
	}
	
	public ArrayList<EnemyTypes> getEnemyList(){
		return enemyList;
	}
}
