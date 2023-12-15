package ui_elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import enums.GameStates;
import enums.MapTiles;
import managers.SceneManager;
import scenes.LevelEdit;

/**
 * This class represents a custom bar for the level editing scene.
 * 
 */
public class EditorBar extends JPanel {
	private SceneManager sceneManager;
	private LevelEdit levelEdit;
	private JLabel helpText;

	public EditorBar(SceneManager sceneManager, LevelEdit levelEdit) {
		this.sceneManager = sceneManager;
		this.levelEdit = levelEdit;
		this.setLayout(new FlowLayout());
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
		MenuButton backBtn = new MenuButton(this.sceneManager, "Save & Back", GameStates.MENU, new Dimension(114, 50));
		this.add(backBtn);
		this.add(Box.createRigidArea(new Dimension(6, 0)));

		JButton customButton = new JButton("Reset map");
		customButton.addActionListener(e -> levelEdit.repaint());
		customButton.setBackground(Color.PINK);
		customButton.setForeground(Color.BLACK);
		customButton.setPreferredSize(new Dimension(96, 50));
		this.add(customButton);
		this.add(Box.createRigidArea(new Dimension(10, 0)));

		// add help text
		helpText = new JLabel("Click and hold to draw!");
		Font newFont = helpText.getFont().deriveFont(16f);
		helpText.setFont(newFont);
		helpText.setForeground(Color.YELLOW);
		helpText.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(helpText);
		this.add(Box.createRigidArea(new Dimension(10, 0)));

		TileButton grassTile = new TileButton(levelEdit, MapTiles.GRASS);
		this.add(grassTile);
		this.add(Box.createRigidArea(new Dimension(6, 0)));
		TileButton waterTile = new TileButton(levelEdit, MapTiles.WATER);
		this.add(waterTile);
		this.add(Box.createRigidArea(new Dimension(6, 0)));
		TileButton roadTile = new TileButton(levelEdit, MapTiles.ROAD);
		this.add(roadTile);

	}

	public void setHelpTextValue(String value) {
		if (helpText != null) {
			helpText.setText(value);
		}
	}
}
