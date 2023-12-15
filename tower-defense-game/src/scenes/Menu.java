package scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import enums.GameDifficulties;
import enums.GameLevels;
import enums.GameStates;
import managers.SceneManager;
import ui_elements.MenuButton;
import ui_elements.MyComboBox;

/**
 * This class represents the menu scene.
 * 
 */
public class Menu extends JPanel {
	private SceneManager sceneManager;
	
	public Menu(SceneManager sceneManager){
		this.sceneManager = sceneManager;
		this.setBackground(Color.DARK_GRAY);
		this.setPreferredSize(new Dimension(640, 640));
		createAndAddGameLabel();
        JPanel btnPanel = createButtonPanel();
		this.add(btnPanel);
	}
	
	
	private void createAndAddGameLabel() {
        JLabel label = new JLabel("Tower Defense!");
        Font newFont = label.getFont().deriveFont(34f);
        label.setFont(newFont);
        label.setForeground(Color.YELLOW);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
	}

	private JPanel createButtonPanel() {
		//create panel for buttons
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setBackground(Color.DARK_GRAY);
        
		
		 //add selectors
        btnPanel.add(Box.createRigidArea(new Dimension(0,100)));
       	JPanel selectorPanel = new JPanel();
       	selectorPanel.setLayout(new BoxLayout(selectorPanel, BoxLayout.X_AXIS));
       	selectorPanel.setBackground(Color.DARK_GRAY);
		addLevelSelector(selectorPanel);
		btnPanel.add(Box.createRigidArea(new Dimension(500,0)));
		addDiffSelector(selectorPanel);
		btnPanel.add(selectorPanel);
        
        //add buttons to new panel
        MenuButton btn = new MenuButton(sceneManager, "Play the game!", GameStates.PLAY, null);
        btnPanel.add(Box.createRigidArea(new Dimension(0,50)));
		btnPanel.add(btn);
		
		MenuButton btn1 = new MenuButton(sceneManager, "Level editor", GameStates.EDITING, null);
		btnPanel.add(Box.createRigidArea(new Dimension(0,50)));
		btnPanel.add(btn1);
		
		MenuButton btn2 = new MenuButton(sceneManager, "Settings", GameStates.SETTINGS, null);
		btnPanel.add(Box.createRigidArea(new Dimension(0,50)));
		btnPanel.add(btn2);
		
		MenuButton btn3 = new MenuButton(sceneManager, "Exit", GameStates.EXIT, null);
		btnPanel.add(Box.createRigidArea(new Dimension(0,50)));
		btnPanel.add(btn3);
		
		return btnPanel;
	}

	private void addLevelSelector(JPanel selectorPanel) {
		JPanel levelPanel = new JPanel(new FlowLayout());
		levelPanel.setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Level:");
        label.setFont(label.getFont().deriveFont(20f));
        label.setForeground(Color.CYAN);
        levelPanel.add(label);
		MyComboBox<GameLevels> levelSelector = new MyComboBox<>(GameLevels.class, Color.CYAN);
		levelSelector.setSelectedItem(GameLevels.gameLevel);
		levelPanel.add(Box.createRigidArea(new Dimension(10,0)));
		levelPanel.add(levelSelector);
		selectorPanel.add(levelPanel);
	}
	
	private void addDiffSelector(JPanel selectorPanel) {
		JPanel diffPanel = new JPanel(new FlowLayout());
		diffPanel.setBackground(Color.DARK_GRAY);
		JLabel label = new JLabel("Difficulty:");
        label.setFont(label.getFont().deriveFont(20f));
        label.setForeground(Color.GREEN);
        diffPanel.add(label);
        MyComboBox<GameDifficulties> difficultySelector = new MyComboBox<>(GameDifficulties.class, Color.GREEN);
        difficultySelector.setSelectedItem(GameDifficulties.gameDifficulty);
        diffPanel.add(Box.createRigidArea(new Dimension(10,0)));
		diffPanel.add(difficultySelector);
		selectorPanel.add(diffPanel);
	}
	
}


