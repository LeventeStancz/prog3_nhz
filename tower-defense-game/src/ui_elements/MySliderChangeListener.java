package ui_elements;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.GameSettings;

/**
 * This class represents a custom JSlider for the settings scene.
 * 
 */
public class MySliderChangeListener implements ChangeListener  {
	public void stateChanged(ChangeEvent e)
    {
		try {
			GameSettings.setAudioVolume(((JSlider)e.getSource()).getValue());
		}catch(Exception ex) {
			ex.printStackTrace();
		}
    }
}
