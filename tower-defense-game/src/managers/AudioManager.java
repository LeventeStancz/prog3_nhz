package managers;

import java.net.URI;
import java.util.Objects;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import main.GameSettings;
import java.io.File;
import java.io.IOException;

/**
 * This class manages the audio played in the game.
 * 
 */
public class AudioManager extends Thread {
	
	private static final String[] songPaths = {
				"/music/assassin.wav",
	            "/music/adventure.wav",
	            "/music/beyond.wav",
	            "/music/enemy_lines.wav",
	            "/music/flying_jack.wav"
	};
	private static FloatControl gainControl;
	
	
	@Override
	public void run() {
        AudioManager.loadAudioLibrary();
    }
	 
	/**
	 * Loads all the audio and plays each.
	 * 
	 */
	public static void loadAudioLibrary() {
		try {
			while (true) {
				for (String songPath : songPaths) {
	                URI uri = Objects.requireNonNull(AudioManager.class.getResource(songPath)).toURI();
	                File audioFile = new File(uri);
	
	                playAudio(audioFile);
		        }
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Plays an audio.
	 * 
	 * @param audioFile The audio file to play.
	 */
	private static void playAudio(File audioFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
            line.open(format);
            line.start();
            
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            }
            
            if(gainControl != null) {
            	updateGainControl();
            }

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = audioStream.read(buffer, 0, buffer.length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.stop();
        }
    }
	
	/**
	 * Updates the gain control.
	 * 
	 */
	public static void updateGainControl() {
		if (gainControl != null) {
            float minGain = GameSettings.getAudioVolume() == 0.0f ? gainControl.getMinimum() : -40.0f;
            float maxGain = gainControl.getMaximum();
            float volumeRange = maxGain - minGain;
            float adjustedVolume = minGain + GameSettings.getAudioVolume() * volumeRange;

            gainControl.setValue(adjustedVolume);
        }
	}
}
