package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import enums.GameDifficulties;
import enums.GameLevels;
import managers.AudioManager;

/**
 * This class handles the settings related tp the game.
 * 
 */
public class GameSettings{
	GameSettings(){}
	private static float audioVolume = 0.5f;
	
	/**
	 * Saves the game settings to a file.
	 * 
	 */
	public static void saveSettings() {
		//data structure:
		//difficulty;level;audioVolume
		String dataString = GameDifficulties.gameDifficulty.name() + ";" + GameLevels.gameLevel.name() + ";" + audioVolume;
		try (FileWriter fw = new FileWriter(GameSettings.class.getResource("/").getPath() + "/settings.txt")){
			fw.write(dataString);
			System.out.println("Info: Successfully saved settings.");
		}catch(IOException e) {
			System.out.println("Error: An error occurred while saving settings.");
		      e.printStackTrace();
		}
	}

	/**
	 * Loads the settings from the settings file.
	 * 
	 */
	public static void loadSettings() {
		try {
		      File file = new File(GameSettings.class.getResource("/").getPath() + "/settings.txt");
		      Scanner sc = new Scanner(file);
		      while (sc.hasNextLine()) {
		        String data = sc.nextLine();
		        String[] dataString = data.split(";");
		        GameDifficulties.gameDifficulty = GameDifficulties.valueOf(dataString[0]);
		        GameLevels.gameLevel = GameLevels.valueOf(dataString[1]);
		        int vol = Math.round((Float.parseFloat(dataString[2])*10.0f));
		        setAudioVolume(vol);
		      }
		      sc.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("Error: Could not find settings file.");
	      saveSettings(); //create file
	    }
	}
	
	public static float getAudioVolume() {
		return audioVolume;
	}
	
	public static void setAudioVolume(int newVolume) {
		if (newVolume >= 0 && newVolume <= 10) {
            audioVolume = newVolume / 10.0f;
        }
		AudioManager.updateGainControl();
	}

	/**
	 * Saves the passed score to a file.
	 * 
	 * @param highScore The score to save.
	 */
	public static void saveHighScore(int highScore) {
		try (FileWriter fw = new FileWriter(GameSettings.class.getResource("/").getPath() + "/highscore.txt")){
			fw.write(String.valueOf(highScore));
			System.out.println("Info: Successfully saved highest score.");
		}catch(IOException e) {
			System.out.println("Error: An error occurred while saving highest score.");
		      e.printStackTrace();
		}
	}

	/**
	 * Loads the highscore from a file.
	 * 
	 * @return The score from the file.
	 */
	public static int loadHighScore() {
		 int highScore = 0;
		try {
		      File file = new File(GameSettings.class.getResource("/").getPath() + "/highscore.txt");
		      Scanner sc = new Scanner(file);
		     
		      while (sc.hasNextInt()) {
		        highScore = sc.nextInt();
		      }
		      sc.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("Error: Could not find highest score file.");
	      saveHighScore(0); //create file
	    }
		return highScore;
	}
}
