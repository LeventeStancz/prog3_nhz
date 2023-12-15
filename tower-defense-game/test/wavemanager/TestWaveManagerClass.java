package wavemanager;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import managers.WaveManager;

public class TestWaveManagerClass {

	private WaveManager waveManager;

    @Before
    public void setUp() {
        waveManager = new WaveManager();
    }

    @Test
    public void testCreateWaves() {
        assertNotNull(waveManager.getWaves());
        assertFalse(waveManager.getWaves().isEmpty());
    }

    @Test
    public void testUpdateEnemySpawnTick() {
        waveManager.update();
        assertTrue(waveManager.isTimeForNewEnemy());
    }

    @Test
    public void testIncreaseWaveIndex() {
        int initialWaveIndex = waveManager.getWaveIndex();
        waveManager.increaseWaveIndex();
        assertEquals((initialWaveIndex + 1), waveManager.getWaveIndex());
        assertFalse(waveManager.isWaveTimerOver());
        assertFalse(waveManager.isWaveTimerStarted());
    }

    @Test
    public void testGetNextEnemy() {
        waveManager.increaseWaveIndex();
        assertNotNull(waveManager.getNextEnemy());
    }

    @Test
    public void testIsThereMoreEnemiesInWave() {
    	for(int i = 0; i < waveManager.getWaves().get(0).getEnemyList().size(); i++) {
    		waveManager.getNextEnemy();
    	}
        assertFalse(waveManager.isThereMoreEnemiesInWave());
    }

    @Test
    public void testIsThereMoreWaves() {
        assertTrue(waveManager.isThereMoreWaves());
        while (waveManager.isThereMoreWaves()) {
            waveManager.increaseWaveIndex();
        }
        assertFalse(waveManager.isThereMoreWaves());
    }

    @Test
    public void testStartWaveTimer() {
        waveManager.startWaveTimer();
        assertTrue(waveManager.isWaveTimerStarted());
    }

    @Test
    public void testUpdateWaveTimer() {
        waveManager.startWaveTimer();
        for (int i = 0; i < waveManager.getTimeLeft() * 60 * 10; i++) {
            waveManager.update();
        }
        assertTrue(waveManager.isWaveTimerOver());
    }

    @Test
    public void testResetEnemyIndex() {
        waveManager.resetEnemyIndex();
        assertEquals(0, waveManager.getEnemyIndex());
    }
}
