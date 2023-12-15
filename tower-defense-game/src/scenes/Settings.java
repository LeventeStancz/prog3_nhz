package scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import enums.GameStates;
import main.GameSettings;
import managers.SceneManager;
import ui_elements.MenuButton;
import ui_elements.MySliderChangeListener;

/**
 * This class represents the settings scene.
 * 
 */
public class Settings extends JPanel {
	private SceneManager sceneManager;
	
	
	public Settings(SceneManager sceneManager){
		this.sceneManager = sceneManager;
		this.setBackground(Color.DARK_GRAY);
		setPanelSize();
		this.setLayout(new BorderLayout());
		createAndAddGameLabel();
        JPanel settingsPanel = createPanel();
		this.add(settingsPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	private void setPanelSize() {
		this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		Dimension panelSize = new Dimension(640, 640);
		this.setMinimumSize(panelSize);
		this.setPreferredSize(panelSize);
		this.setMaximumSize(panelSize);
	}
	
	
	private void createAndAddGameLabel() {
        JLabel label = new JLabel("Settings");
        Font newFont = label.getFont().deriveFont(30f);
        label.setFont(newFont);
        label.setForeground(Color.YELLOW);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label, BorderLayout.NORTH);
        //add back button
        MenuButton btn = new MenuButton(sceneManager, "Back", GameStates.MENU, null);
        this.add(btn, BorderLayout.SOUTH);
	}

	private JPanel createPanel() {
		//create panel for buttons
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.LINE_AXIS));
        settingsPanel.setBackground(Color.DARK_GRAY);
        
        //Add text
        JLabel label = new JLabel("Audio:");
        Font newFont = label.getFont().deriveFont(20f);
        label.setFont(newFont);
        label.setForeground(Color.CYAN);
        settingsPanel.add(label);
        
        //add slider
        JSlider audioSlider = createAudioSlider();
        settingsPanel.add(Box.createRigidArea(new Dimension(60,0)));
        settingsPanel.add(audioSlider);
		
		return settingsPanel;
	}

	private JSlider createAudioSlider() {
		int vol = Math.round(GameSettings.getAudioVolume() * 10);
		System.out.println(vol);
		JSlider audioSlider = new JSlider(SwingConstants.HORIZONTAL,
                0, 10, vol);
        audioSlider.setMaximumSize(new Dimension(400,60));
        audioSlider.setPaintTrack(true);
        audioSlider.setPaintTicks(true);
        audioSlider.setPaintLabels(true);
        audioSlider.setMajorTickSpacing(1);
        audioSlider.setMinorTickSpacing(1);
        audioSlider.setBackground(Color.DARK_GRAY);
        audioSlider.setForeground(Color.CYAN);
        audioSlider.addChangeListener(new MySliderChangeListener());
		return audioSlider;
	}
	
}



