package ui_elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.AbstractBorder;

import enums.MapTiles;
import managers.ResourceManager;
import scenes.LevelEdit;

/**
 * This class represents a custom JButton for the level editing scene.
 * 
 */
public class TileButton extends JButton implements ActionListener {
	private BufferedImage tileImage;
	private MapTiles tileType;
	private LevelEdit levelEdit;

	public TileButton(LevelEdit levelEdit, MapTiles tileType) {
		super();
		this.levelEdit = levelEdit;
		this.tileType = tileType;
		this.tileImage = ResourceManager.getTile(tileType).getImage();
		setPreferredSize(new Dimension(60, 60));
		initButtonProperties();
		addActionListener(this);
	}

	private void initButtonProperties() {
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(null);
		setFocusPainted(false);
		setContentAreaFilled(true);
		setIcon(new ImageIcon(tileImage));
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setBorder(new CustomBorder());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		levelEdit.setSelectedTile(ResourceManager.getTile(tileType));
	}

	private static class CustomBorder extends AbstractBorder {
		private static final int BORDER_WIDTH = 3;

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			if (c instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) c;
				if (button.isFocusOwner()) {
					g.setColor(Color.CYAN);
					for (int i = 0; i < BORDER_WIDTH; i++) {
						g.drawRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1);
					}
				}
			}
		}
	}
}
