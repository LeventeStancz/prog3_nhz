package ui_elements;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import enums.GameStates;
import managers.SceneManager;

/**
 * This class represents a custom JButton for the menu scene.
 * 
 */
public class MenuButton extends JButton implements ActionListener{
	private GameStates state;
	private SceneManager sceneManager;
	private Dimension size;
	
    public MenuButton(SceneManager sceneManager, String label, GameStates state, Dimension dimension) {
        super(label);
        this.sceneManager = sceneManager;
        this.state = state;
        this.size = dimension == null ? new Dimension(100, 50) : dimension;
        addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setBorder(null);
        setFocusPainted(false);
        setPreferredSize(size);
        setAlignmentX(Component.CENTER_ALIGNMENT);

        int paddingX = 20;
        int paddingY = 10;
        setBorder(new EmptyBorder(paddingY, paddingX, paddingY, paddingX));
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		sceneManager.setScene(state);
	}

}
