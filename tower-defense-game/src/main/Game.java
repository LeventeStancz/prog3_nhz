package main;

import enums.GameStates;
import managers.AudioManager;
import managers.SceneManager;

/**
 * This path containts the games base logic.
 * 
 */
public class Game implements Runnable {
	private static final double FPS_LIMIT = 120.0;
	private static final double UPDATE_LIMIT = 60.0;

	SceneManager sceneManager;

	public Game() {
		sceneManager = new SceneManager();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	/**
	 * Starts the game and audio threads.
	 * 
	 */
	private void start() {
		Thread gameThread;
		gameThread = new Thread(this);
		gameThread.start();
		
		AudioManager audioThread = new AudioManager();
        audioThread.start();
	}

	private void updateGame() {
		// called every UpdatePerSecond
		if (GameStates.gameState == GameStates.PLAY) {
			sceneManager.getPlayScene().update();
		}
	}

	private void updateRender() {
		if (GameStates.gameState == GameStates.PLAY) {
			sceneManager.getPlayScene().repaint();
		}
	}

	/**
	 * Overrides the Runnable interface run method because the game runs on a separate thread.
	 * 
	 */
	@Override
	public void run() {		
		double timePerFrame = 1000000000.0 / FPS_LIMIT;
		double timePerUpdate = 1000000000.0 / UPDATE_LIMIT;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			// Render
			if (now - lastFrame >= timePerFrame) {
				updateRender();
				lastFrame = now;
				frames++;
			}

			// Update
			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}

		}
	}

}
