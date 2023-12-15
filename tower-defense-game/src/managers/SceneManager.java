package managers;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import enums.GameLevels;
import enums.GameStates;
import main.GameSettings;
import scenes.GameOver;
import scenes.LevelEdit;
import scenes.Menu;
import scenes.Play;
import scenes.Settings;

/**
 * This class manages switching between scenes.
 * 
 */
public class SceneManager extends JFrame {
	private Menu menuScene;
	private Play playScene;
	private GameOver gameOverScene;
	private LevelEdit levelEditScene;
	private Settings settingsScene;
	private int highScore = 0;
	
	public SceneManager() {
		this.setTitle("TowerDefense");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.DARK_GRAY);
		GameSettings.loadSettings();
		initScenes();
		this.add(menuScene);
		setPanelSize();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initScenes() {
		menuScene = new Menu(this);
		playScene = new Play(this);
		gameOverScene = new GameOver(this);
		levelEditScene = new LevelEdit(this);
		settingsScene = new Settings(this);
	}
	
	private void setPanelSize() {
		Dimension panelSize = new Dimension(692, 786);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
	}
	
	/**
	 * Sets the passed scene to the main scene.
	 * 
	 * @param newState Scene to switch to.
	 */
	public void setScene(GameStates newState) {
		GameStates.setGameState(newState);
		switch(newState) {
		case MENU:
			switch(GameStates.lastGameState) {
			case SETTINGS:
				GameSettings.saveSettings();
				break;
			case EDITING:
				ResourceManager.saveCustomMap(this.levelEditScene.getEditedMap());
				break;
			default:
				break;
			}
			this.setContentPane(menuScene);
			break;
		case PLAY:
			startPlayScene();
			break;
		case GAMEOVER:
			int highestScore = GameSettings.loadHighScore();
			if(highScore > highestScore) {
				GameSettings.saveHighScore(highScore);
			}
			this.setContentPane(gameOverScene);
			break;
		case EDITING:
			this.setContentPane(levelEditScene);
			break;
		case SETTINGS:
			this.setContentPane(settingsScene);
			break;
		case EXIT:
			ResourceManager.saveCustomMap(this.levelEditScene.getEditedMap());
			GameSettings.saveSettings();
			System.exit(0);
			break;
		default:
			ResourceManager.saveCustomMap(this.levelEditScene.getEditedMap());
			GameSettings.saveSettings();
			this.setContentPane(menuScene);
			break;
		}
		this.revalidate();
	}

	/**
	 * Resets and start the play scene.
	 * 
	 */
	private void startPlayScene() {
		highScore = 0;
		playScene = new Play(this);
		playScene.setMap(GameLevels.gameLevel);
		this.setContentPane(playScene);
	}

	public Menu getMenuScene() {
		return menuScene;
	}

	public Play getPlayScene() {
		return playScene;
	}

	public LevelEdit getLevelEditScene() {
		return levelEditScene;
	}

	public Settings getSettingsScene() {
		return settingsScene;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	
	
}
