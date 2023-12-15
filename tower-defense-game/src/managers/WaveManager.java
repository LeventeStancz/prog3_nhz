package managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import enums.EnemyTypes;
import enums.GameDifficulties;
import helpers.Wave;

/**
 * This class manages a List of Waves.
 * 
 */
public class WaveManager {
	private ArrayList<Wave> waves = new ArrayList<>();
	private int enemySpawnTickLimit = 60 * 5;
	private int enemySpawnTick = enemySpawnTickLimit;
	private int enemyIndex;
	private int waveIndex;
	private boolean waveStartTimer;
	private boolean waveTickTimerOver;
	private int waveTickLimit = 60 * 5;
	private int waveTick = 0;

	public WaveManager() {
		createWaves();
	}

	/**
	 * Updates waves and properties.
	 * 
	 */
	public void update() {
		if (enemySpawnTick < enemySpawnTickLimit) {
			enemySpawnTick++;
		}

		if (waveStartTimer) {
			waveTick++;
			if (waveTick >= waveTickLimit) {
				waveTickTimerOver = true;
			}
		}
	}

	/**
	 * Increases the wave index and resets the waveTickTimerOver, waveStartTimer, waveTick properties.
	 * 
	 */
	public void increaseWaveIndex() {
		waveIndex++;
		waveTickTimerOver = false;
		waveStartTimer = false;
		waveTick = 0;
	}

	public EnemyTypes getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}

	/**
	 * Creates waves based on the game difficulty.
	 * 
	 */
	private void createWaves() {
		switch(GameDifficulties.gameDifficulty) {
		case EASY:
			createEasyWaves();
			break;
		case NORMAL:
			createNormalWaves();
			break;
		case HARD:
			createHardWaves();
			break;
		default:
			createNormalWaves();
			break;
		}
	}

	/**
	 * Creates waves for the hard game mode.
	 * 
	 */
	private void createHardWaves() {
		waves.add(new Wave(
				new ArrayList<>(Arrays.asList(EnemyTypes.BEE, EnemyTypes.BEE, EnemyTypes.WOLF,
						EnemyTypes.WOLF, EnemyTypes.BEE, EnemyTypes.WOLF))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.BEE, EnemyTypes.WOLF,
				EnemyTypes.BEE, EnemyTypes.WOLF, EnemyTypes.WOLF, EnemyTypes.OGRE))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF, EnemyTypes.OGRE))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.OGRE, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF, EnemyTypes.OGRE))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE,EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.OGRE,EnemyTypes.BEE, EnemyTypes.OGRE))));
	}

	/**
	 * Creates waves for the normal game mode.
	 * 
	 */
	private void createNormalWaves() {
		waves.add(new Wave(
				new ArrayList<>(Arrays.asList(EnemyTypes.BEE, EnemyTypes.BEE, EnemyTypes.WOLF,
						EnemyTypes.WOLF, EnemyTypes.BEE, EnemyTypes.WOLF))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.BEE, EnemyTypes.WOLF,
				EnemyTypes.BEE, EnemyTypes.WOLF, EnemyTypes.WOLF, EnemyTypes.OGRE))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.WOLF, EnemyTypes.BEE, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF, EnemyTypes.OGRE))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.WOLF, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.WOLF, EnemyTypes.WOLF))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.WOLF, EnemyTypes.BEE, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE,EnemyTypes.WOLF, EnemyTypes.OGRE, EnemyTypes.OGRE,EnemyTypes.BEE, EnemyTypes.OGRE))));
	}

	/**
	 * Creates waves for the easy game mode.
	 * 
	 */
	private void createEasyWaves() {
		waves.add(new Wave(
				new ArrayList<>(Arrays.asList(EnemyTypes.BEE, EnemyTypes.BEE, EnemyTypes.WOLF,
						EnemyTypes.BEE, EnemyTypes.BEE, EnemyTypes.WOLF))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.BEE, EnemyTypes.WOLF,
				EnemyTypes.BEE, EnemyTypes.BEE, EnemyTypes.WOLF, EnemyTypes.OGRE))));
		
		waves.add(new Wave(new ArrayList<>(Arrays.asList(EnemyTypes.WOLF, EnemyTypes.BEE, EnemyTypes.WOLF,
				EnemyTypes.WOLF, EnemyTypes.OGRE,EnemyTypes.BEE, EnemyTypes.OGRE))));
	}

	/**
	 * Checks if it is time to spawn a new enemy.
	 * 
	 */
	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	public List<Wave> getWaves() {
		return waves;
	}

	/**
	 * Checks if is there more enemies to spawn in the wave.
	 * 
	 */
	public boolean isThereMoreEnemiesInWave() {
		if(waveIndex != waves.size()) {
			return enemyIndex < waves.get(waveIndex).getEnemyList().size();
		}
		return false;
	}

	/**
	 * Checks if there are more waves in the list.
	 * 
	 */
	public boolean isThereMoreWaves() {
		return waveIndex < waves.size();
	}

	public void startWaveTimer() {
		waveStartTimer = true;
	}

	public boolean isWaveTimerOver() {
		return waveTickTimerOver;
	}

	public void resetEnemyIndex() {
		enemyIndex = 0;
	}

	public int getWaveIndex() {
		return waveIndex;
	}
	
	public int getEnemyIndex() {
		return enemyIndex;
	}

	public int getTimeLeft() {
	    return ((waveTickLimit - waveTick)/60)+1;
	}

	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}
}
