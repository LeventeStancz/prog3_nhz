package scenes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import enums.GameStates;
import main.GameSettings;
import managers.SceneManager;
import ui_elements.MenuButton;

/**
 * This class represents the game over scene.
 * 
 */
public class GameOver extends JPanel {
	private SceneManager sceneManager;
	private JLabel label2;
	
	public GameOver(SceneManager sceneManager){
		this.sceneManager = sceneManager;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		Color c = sceneManager.getPlayScene().getHeroLife() == 0 ? Color.RED.darker() : Color.GREEN.darker();
		this.setBackground(c);
		this.setPreferredSize(new Dimension(640, 640));
		createAndAddGameLabel();
        addButtons();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateHighScoreLabel();
	}
	
	private void updateHighScoreLabel(){
		label2.setText("<html>Your current score: " + sceneManager.getHighScore() + 
        		"<br>Your highest score: " + GameSettings.loadHighScore() + "</html>");
	}
	
	
	private void createAndAddGameLabel() {
		this.add(Box.createRigidArea(new Dimension(0,50)));
        JLabel label = new JLabel("Game over!");
        Font newFont = label.getFont().deriveFont(30f);
        label.setFont(newFont);
        label.setForeground(Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(label);
        
		this.add(Box.createRigidArea(new Dimension(0,50)));
        label2 = new JLabel("<html>Your current score: " + sceneManager.getHighScore() + 
        		"<br>Your highest score: " + GameSettings.loadHighScore() + "</html>");
        Font newFont2 = label.getFont().deriveFont(30f);
        label2.setFont(newFont2);
        label2.setForeground(Color.BLACK);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(label2);
	}

	private void addButtons() {
		this.add(Box.createRigidArea(new Dimension(0,50)));
		MenuButton replayBtn = new MenuButton(sceneManager, "Replay!", GameStates.PLAY, null);
		this.add(replayBtn);
       
		this.add(Box.createRigidArea(new Dimension(0,30)));
		
		 MenuButton menuBtn = new MenuButton(sceneManager, "Back to menu!", GameStates.MENU, null);
	     this.add(menuBtn);
	}

}



