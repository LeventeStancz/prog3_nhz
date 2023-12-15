package scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import allies.Ally;
import enemies.Enemy;
import enums.Allies;
import enums.GameLevels;
import enums.GameStates;
import enums.MapTiles;
import helpers.MapConstants;
import helpers.MapTile;
import managers.AllyManager;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.ResourceManager;
import managers.SceneManager;
import managers.WaveManager;
import ui_elements.PlayBar;

/**
 * This class represents the play scene.
 * 
 */
public class Play extends JPanel implements MouseListener, MouseMotionListener {
	private MapTile[][] map;
	private SceneManager sceneManager;
	private EnemyManager enemyManager;
	private AllyManager allyManager;
	private WaveManager waveManager;
	private ProjectileManager projectileManager;
	private BufferedImage heroImg;
	private BufferedImage campImg;
	private BufferedImage heartImg;
	private PlayBar playBar;
	private Allies selectedAlly;
	private boolean drawSelected;
	private boolean drawRange;
	private int mouseX;
	private int mouseY;
	private int lastClickedMouseX;
	private int lastClickedMouseY;
	private int goldTick = 0;
	private boolean pauseGame = false;
	private int heroLife = 3;

	public Play(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());

		this.heartImg = ResourceManager.getHeartImage();
		this.heroImg = ResourceManager.getHeroImage();
		this.campImg = ResourceManager.getCampImage();
		this.map = ResourceManager.getMap(GameLevels.gameLevel);

		this.enemyManager = new EnemyManager(this);
		this.allyManager = new AllyManager(this);
		this.projectileManager = new ProjectileManager(this);
		this.waveManager = new WaveManager();

		this.playBar = new PlayBar(this.sceneManager, this);
		this.add(playBar, BorderLayout.PAGE_END);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		this.goldTick = 0;
		this.heroLife = 3;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}

	public void render(Graphics g) {
		paintMap(g);
		drawHero(g);
		drawCamp(g);
		allyManager.draw(g);
		enemyManager.draw(g);
		projectileManager.draw(g);
		drawSelectedAlly(g);
		drawSelectedRange(g);
		drawInfos(g);
	}

	private void drawInfos(Graphics g) {
			playBar.updateGoldAndWaveTimerText(waveManager.isWaveTimerStarted());
			playBar.updateEnemyAndWaveText();
			playBar.updatePauseText();
			if(pauseGame) {
				drawPauseGame(g);
			}
			drawHeroLife(g);
	}

	private void drawHeroLife(Graphics g) {
	    int gap = 12;
	    int x = 347;
	    int y = 334;

	    for (int i = 0; i < heroLife; i++) {
	        g.drawImage(heartImg, x, y, 11, 11, null);
	        x += gap;
	    }
	}


	private void drawPauseGame(Graphics g) {
		Font styledFont = new Font("Arial", Font.BOLD, 40);
        g.setFont(styledFont);
        FontMetrics metrics = g.getFontMetrics(styledFont);
        String text = "Game is paused!";
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + metrics.getAscent();
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x - 5, y - metrics.getAscent() - 5, textWidth + 10, textHeight + 10);
        
        g.setColor(Color.BLACK);
		g.drawString(text, x, y);
	}

	private void drawSelectedRange(Graphics g) {
		if (drawRange) {
			Ally ally = allyManager.getAllyAt(lastClickedMouseY / 32, lastClickedMouseX / 32);
			if (ally != null) {
				drawRangeCircle(g, ally);
			}else {
				playBar.hideUpgradeBtn();
				this.grabFocus();
			}
		}
	}

	public void drawRangeCircle(Graphics g, Ally ally) {
		g.setColor(Color.YELLOW);
		g.drawOval((int) (ally.getX() + 16 - ally.getRange()), (int) (ally.getY() + 16 - ally.getRange()),
				(int) ally.getRange() * 2, (int) ally.getRange() * 2);
	}

	private void drawSelectedAlly(Graphics g) {
		if (drawSelected) {
			g.drawImage(allyManager.getAllyImg(), mouseX, mouseY, null);
		}
	}

	public void update() {
		
		if(heroLife == 0) {
			heroLife = 3;
			sceneManager.setScene(GameStates.GAMEOVER);
		}
		
		if(!waveManager.isThereMoreWaves()) {
			heroLife = 3;
			sceneManager.setScene(GameStates.GAMEOVER);
		}
		
		if(!pauseGame) {
			waveManager.update();
			goldTick++;
			if(goldTick % (60*3) == 0) {
				playBar.addGold(1);
				Ally ally = allyManager.getAllyAt(lastClickedMouseY / 32, lastClickedMouseX / 32);
				if(ally != null) {
					playBar.updateUpgradeBtn(ally);
				}
			}
			
			//reuse goldTick for highscore
			if(goldTick % (60*1) == 0) {
				int newScore = (sceneManager.getHighScore()+1);
				sceneManager.setHighScore(newScore);
			}
	
			if (isAllEnemiesDead() && waveManager.isThereMoreWaves()) {
				waveManager.startWaveTimer();
				if (waveManager.isWaveTimerOver()) {
					waveManager.increaseWaveIndex();
					enemyManager.getEnemies().clear();
					waveManager.resetEnemyIndex();
				}
			}
	
			if (isTimeForNewEnemy()) {
				spawnEnemy();
			}
	
			allyManager.update();
			enemyManager.update();
			projectileManager.update();
		}
	}

	private boolean isAllEnemiesDead() {
		if (waveManager.isThereMoreEnemiesInWave()) {
			return false;
		}

		for (Enemy e : enemyManager.getEnemies()) {
			if (e.isAlive()) {
				return false;
			}
		}
		return true;
	}

	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	private boolean isTimeForNewEnemy() {
		return waveManager.isTimeForNewEnemy() && (waveManager.isThereMoreEnemiesInWave());
	}

	private void drawHero(Graphics g) {
		g.drawImage(heroImg, MapConstants.HERO_POS_X, MapConstants.HERO_POS_Y, null);
	}

	private void drawCamp(Graphics g) {
		g.drawImage(campImg, MapConstants.CAMP_POS_X, MapConstants.CAMP_POS_Y, null);
	}

	private void paintMap(Graphics g) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				g.drawImage(map[y][x].getImage(), x * 32, y * 32, null);
			}
		}
	}
	
	public void reduceHeroLife() {
		heroLife -= 1;
	}
	
	public void upgradeAlly() {
		Ally ally = allyManager.getAllyAt(lastClickedMouseY / 32, lastClickedMouseX / 32);
		if(ally != null && ally.canUpgradeTier() && playBar.getGold() >= ally.getUpgradeCost()) {
			playBar.removeUpgradeCost(ally.getUpgradeCost());
			ally.upgradeTier();
			playBar.updateUpgradeBtn(ally);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		lastClickedMouseX = e.getX();
		lastClickedMouseY = e.getY();
		if (e.getButton() == MouseEvent.BUTTON3) {
			selectedAlly = null;
			drawSelected = false;
			drawRange = false;
			playBar.hideUpgradeBtn();
			this.grabFocus();
		} else {
			if(!pauseGame) {
				if (selectedAlly != null && allyManager.getAllyAt(mouseY / 32, mouseX / 32) == null && isTileGrass()
						&& isTileNextToRoad() && isThereNoAlly(mouseY, mouseX)) {
					if (playBar.isGoldEnough(selectedAlly)) {
						allyManager.addAlly(selectedAlly, mouseX, mouseY);
						playBar.removeAllyCost(selectedAlly);
					}
					selectedAlly = null;
					drawSelected = false;
					drawRange = false;
					playBar.hideUpgradeBtn();
					this.grabFocus();
				} else {
					Ally ally = allyManager.getAllyAt(mouseY / 32, mouseX / 32);
					if (ally != null) {
						playBar.showUpgradeBtn(ally);
						drawRange = true;
					}
				}
			}
		}
	}

	private boolean isThereNoAlly(int mouseY, int mouseX) {
	    int row = mouseX / 32;
	    int col = mouseY / 32;

	    boolean topNoAlly = row - 1 >= 0 && allyManager.getAllyAt(col, row - 1) == null;
	    boolean leftNoAlly = col - 1 >= 0 && allyManager.getAllyAt(col - 1, row) == null;
	    boolean downNoAlly = row + 1 < map[col].length && allyManager.getAllyAt(col, row + 1) == null;
	    boolean rightNoAlly = col + 1 < map.length && allyManager.getAllyAt(col + 1, row) == null;

	    return topNoAlly && leftNoAlly && downNoAlly && rightNoAlly;
	}


	private boolean isTileGrass() {
		return map[mouseY / 32][mouseX / 32].getType().equals(MapTiles.GRASS);
	}

	private boolean isTileNextToRoad() {
		int row = mouseX / 32;
		int col = mouseY / 32;
		boolean topIsRoad = false;
		boolean leftIsRoad = false;
		boolean downIsRoad = false;
		boolean rightIsRoad = false;

		if (row - 1 >= 0) {
			topIsRoad = map[col][row - 1].getType().equals(MapTiles.ROAD);
		}
		if (col - 1 >= 0) {
			leftIsRoad = map[col - 1][row].getType().equals(MapTiles.ROAD);
		}
		if (row + 1 < map[col].length) {
			downIsRoad = map[col][row + 1].getType().equals(MapTiles.ROAD);
		}
		if (col + 1 < map.length) {
			rightIsRoad = map[col + 1][row].getType().equals(MapTiles.ROAD);
		}

		return topIsRoad || leftIsRoad || downIsRoad || rightIsRoad;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Mouse pressed
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Mouse released
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Mouse entered
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Mouse exited
	}

	public MapTile[][] getMap() {
		return map;
	}

	public void setMap(GameLevels gameLevel) {
		this.map = ResourceManager.getMap(gameLevel);
	}

	public void setSelectedAlly(Allies allyType) {
		selectedAlly = allyType;
	}

	public Allies getSelectedAlly() {
		return selectedAlly;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// Mouse dragged
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int trueValueX = (e.getPoint().x / 32) * 32;
		int trueValueY = (e.getPoint().y / 32) * 32;
		if ((trueValueY <= 640 && trueValueX <= 640) && (trueValueX >= 0 && trueValueY >= 0)) {
			mouseX = trueValueX;
			mouseY = trueValueY;
		}
		drawSelected = selectedAlly != null;
	}
	
	public void shootEnemy(Ally ally, Enemy enemy) {
		projectileManager.newProjectile(ally, enemy);
	}

	public void pauseGame() {
		boolean temp = pauseGame;
		pauseGame = !temp;
	}

	public boolean getPauseGame() {
		return pauseGame;
	}
	
	public AllyManager getAllyManager() {
		return allyManager;
	}

	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public WaveManager getWaveManager() {
		return waveManager;
	}
	
	public PlayBar getPlayBar() {
		return playBar;
	}

	public int getHeroLife() {
		return heroLife;
	}
	
}