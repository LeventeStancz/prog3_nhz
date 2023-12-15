package ui_elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import enums.GameDifficulties;
import enums.GameLevels;

/**
 * This class represents a custom JComboBox for the menu scene.
 * 
 */
public class MyComboBox<E extends Enum<E>> extends JComboBox<E> {
	
	public MyComboBox(Class<E> enumClass, Color foreground) {
		super(enumClass.getEnumConstants());
		this.setSize(new Dimension(200,30));
		this.setMaximumSize(new Dimension(200,30));
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(foreground);
		
		addComboBoxListener();
	}

	private void addComboBoxListener() {
		this.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unchecked")
				E selectedValue = (E) getSelectedItem();
                if(selectedValue.getClass() == GameLevels.class) {
                	GameLevels.setGameLevel(GameLevels.valueOf(selectedValue.name()));
                }
                if(selectedValue.getClass() == GameDifficulties.class) {
                	GameDifficulties.setGameDifficulty(GameDifficulties.valueOf(selectedValue.name()));
                }
            }
        });
	}
}
