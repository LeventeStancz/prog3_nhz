package ui_elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import allies.Ally;
import enums.Allies;
import enums.GameStates;
import managers.SceneManager;
import scenes.Play;

/**
 * This class represents a custom bar for the play scene.
 * 
 */
public class PlayBar extends JPanel {
	private SceneManager sceneManager;
	private Play playScene;
	private JLabel goldWaveText;
	private JLabel enemyAndWaveText;
	private JButton upgradeBtn;
	JButton pauseBtn;
	private int gold = 100;

	public PlayBar(SceneManager sceneManager, Play playScene) {
		this.sceneManager = sceneManager;
		this.playScene = playScene;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(Color.GRAY);
		setPanelSize();
		addButtons();
	}

	private void setPanelSize() {
		Dimension size = new Dimension(640, 80);
		this.setMinimumSize(size);
		this.setSize(size);
		this.setMaximumSize(size);
	}

	private void addButtons() {
		addMenuButtons();
		addGoldAndTimerText();
		addEnemyAndWaveText();
		addAllyBtn();
		addUpgradeBtn();
	}

	private void addUpgradeBtn() {
		upgradeBtn = new JButton();
		upgradeBtn.setPreferredSize(new Dimension(120, 60));
		upgradeBtn.setBackground(Color.DARK_GRAY);
		upgradeBtn.setForeground(Color.GREEN);
		upgradeBtn.setText("<html>Upgrade "+ " " +"!<br>Cost: 100</html>");
		upgradeBtn.addActionListener(e -> playScene.upgradeAlly());
		this.add(upgradeBtn);
		upgradeBtn.setVisible(false);
	}

	private void addMenuButtons() {
		MenuButton backBtn = new MenuButton(this.sceneManager, "Menu", GameStates.MENU, new Dimension(72, 50));
		this.add(backBtn);
		addPauseBtn();
	}

	private void addPauseBtn() {
		pauseBtn = new JButton(playScene.getPauseGame() ? "Resume" : "Pause");
		pauseBtn.setPreferredSize(new Dimension(88, 50));
		pauseBtn.setBackground(Color.BLACK);
		pauseBtn.setForeground(Color.WHITE);
		pauseBtn.setBorder(null);
		pauseBtn.setFocusPainted(false);
		pauseBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		pauseBtn.setBorder(new EmptyBorder(10, 20, 10, 20));
		pauseBtn.addActionListener(e -> playScene.pauseGame());
		this.add(pauseBtn);
	}

	private void addGoldAndTimerText() {
		goldWaveText = new JLabel("Gold: " + gold);
		Font newFont = goldWaveText.getFont().deriveFont(15f);
		goldWaveText.setFont(newFont);
		goldWaveText.setForeground(Color.YELLOW);
		goldWaveText.setHorizontalAlignment(SwingConstants.LEFT);
		goldWaveText.setPreferredSize(new Dimension(114, 40));
		this.add(goldWaveText);
		this.add(Box.createRigidArea(new Dimension(4, 0)));
	}
	
	private void addEnemyAndWaveText() {
		enemyAndWaveText = new JLabel("<html>Wave: 0/0<br>Enemies: 0</html>");
		Font newFont = enemyAndWaveText.getFont().deriveFont(15f);
		enemyAndWaveText.setFont(newFont);
		enemyAndWaveText.setForeground(Color.YELLOW);
		enemyAndWaveText.setHorizontalAlignment(SwingConstants.LEFT);
		enemyAndWaveText.setPreferredSize(new Dimension(108, 40));
		this.add(enemyAndWaveText);
		this.add(Box.createRigidArea(new Dimension(2, 0)));
	}
	
	private void addAllyBtn() {
		AllyButton allyBtn = new AllyButton(playScene, Allies.ALLY);
		this.add(allyBtn);
	}

	public void updateGoldAndWaveTimerText(boolean includeTimeLeft) {
		goldWaveText.setText("<html>Gold: " + gold + "<br>" + 
				(includeTimeLeft ? "Till next wave: " + playScene.getWaveManager().getTimeLeft() : "<br>") + "</html>");
	}
	
	public void updateEnemyAndWaveText() {
		int index = playScene.getWaveManager().getWaveIndex()+1;
		enemyAndWaveText.setText("<html>Wave: " + (index) + "/" 
				+ playScene.getWaveManager().getWaves().size() + 
				"<br>Enemies left: "+ playScene.getEnemyManager().getAliveEnemies() + 
				"</html>");
	}

	public int getGold() {
		return gold;
	}
	
	public void showUpgradeBtn(Ally ally) {
		if(ally.getTier() == 4) {
			upgradeBtn.setText("Max tier!");
		}else {
			if(gold < ally.getUpgradeCost()) {
				upgradeBtn.setText("Can't afford!");
			}else {
				upgradeBtn.setText("<html>Upgrade "+ ally.getType().name().toLowerCase() +"!<br>Cost: "+ ally.getUpgradeCost() +"</html>");
			}
		}	
		upgradeBtn.setVisible(true);
	}
	
	public void updateUpgradeBtn(Ally ally) {
		if(ally.getTier() == 4) {
			upgradeBtn.setText("Max tier!");
		}else {
			if(gold < ally.getUpgradeCost()) {
				upgradeBtn.setText("Can't afford!");
			}else {
				upgradeBtn.setText("<html>Upgrade "+ ally.getType().name().toLowerCase() +"!<br>Cost: "+ ally.getUpgradeCost() +"</html>");
			}
		}	
	}
	
	public void hideUpgradeBtn() {
		upgradeBtn.setVisible(false);
	}

	public void removeAllyCost(Allies selectedAlly) {
		gold -= playScene.getAllyManager().getDummyAlly(selectedAlly).getCost();
	}

	public boolean isGoldEnough(Allies selectedAlly) {
		return gold - playScene.getAllyManager().getDummyAlly(selectedAlly).getCost() >= 0;
	}

	public void addGold(int reward) {
		gold += reward;
	}

	public void removeUpgradeCost(int upgradeCost) {
		gold -= upgradeCost;
	}

	public void updatePauseText() {
		pauseBtn.setText(playScene.getPauseGame() ? "Resume" : "Pause");
	}

}
