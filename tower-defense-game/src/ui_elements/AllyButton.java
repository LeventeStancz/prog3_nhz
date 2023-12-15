package ui_elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import enums.Allies;
import managers.ResourceManager;
import scenes.Play;

/**
 * This class represents a custom JButton for showing an Ally.
 * 
 */
public class AllyButton extends JButton implements ActionListener {
	private BufferedImage tileImage;
	private Allies allyType;
	private Play playScene;

	public AllyButton(Play playScene, Allies tileType) {
		super();
		this.playScene = playScene;
		this.allyType = tileType;
		this.tileImage = ResourceManager.getAllyIcon(tileType);
		
        ImageIcon originalIcon = new ImageIcon(tileImage);
        ImageIcon scaledIcon = scaleIcon(originalIcon, 50, 50);
        
        this.setIcon(scaledIcon);
		setPreferredSize(new Dimension(110, 60));
		initButtonProperties();
		addActionListener(this);
	}
	
	private static ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

	private void initButtonProperties() {
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setBorder(null);
		setFocusPainted(false);
		setContentAreaFilled(true);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setBorder(new CustomBorder());
		setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalTextPosition(SwingConstants.CENTER);
        Font newFont = getFont().deriveFont(14f);
        setFont(newFont);
        setForeground(Color.RED);
		setText("<html>Cost:<br>&nbsp;&nbsp;" + playScene.getAllyManager().getDummyAlly(allyType).getCost() + "</html>");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playScene.setSelectedAlly(allyType);
	}

	private static class CustomBorder extends AbstractBorder {
		private static final int BORDER_WIDTH = 3;

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			if (c instanceof AbstractButton) {
				AbstractButton button = (AbstractButton) c;
				if (button.isFocusOwner()) {
					g.setColor(Color.CYAN);
				} else {
					g.setColor(Color.BLACK);
				}

				for (int i = 0; i < BORDER_WIDTH; i++) {
					g.drawRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1);
				}
			}
		}
	}
}
