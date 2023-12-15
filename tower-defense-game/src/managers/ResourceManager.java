package managers;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import enums.Allies;
import enums.GameLevels;
import enums.MapTiles;
import helpers.Algorithms;
import helpers.MapTile;

/**
 * This class manages the games resources.
 * 
 */
public class ResourceManager {
	ResourceManager(){}
	
	public static MapTile getTile(MapTiles tileType) {
		return new MapTile(tileType);
	}
	
	/**
	 * Loads a specified type map.
	 * 
	 * @param levelName The name of the level.
	 * @return 2D array.
	 */
	public static MapTile[][] getMap(GameLevels levelName){
		MapTile[][] map = null;
		
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(
        		Objects.requireNonNull(ResourceManager.class.getResourceAsStream(
                "/" + levelName.name().toLowerCase() + ".txt"))))) {
			String line;
			int row = 0;

			map = new MapTile[21][21];
			while ((line = buffer.readLine()) != null) {
			    String[] vals = line.trim().split(";");

			    for (int col = 0; col < 21; col++) {
			        map[row][col] = new MapTile(MapTiles.valueOf(vals[col]));
			    }

			    row++;
			}
		}catch(Exception e) {
			System.out.println("Error: Couldn't load " + levelName.name() + " level!");
			e.printStackTrace();
		}
        
        return map;
	}
	
	/**
	 * Loads and returns the custom map.
	 * 
	 * @return The custom map.
	 */
	public static MapTile[][] getCustomMap(){
		MapTile[][] map = null;
		
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(
        		Objects.requireNonNull(ResourceManager.class.getResourceAsStream(
                        "/custom_level.txt"))))) {
			String line;
			int row = 0;

			map = new MapTile[21][21];
			while ((line = buffer.readLine()) != null) {
			    String[] vals = line.trim().split(";");

			    for (int col = 0; col < 21; col++) {
			        map[row][col] = new MapTile(MapTiles.valueOf(vals[col]));
			    }

			    row++;
			}
		}catch(Exception e) {
			System.out.println("Error: Couldn't load custom level!");
			e.printStackTrace();
		}
        
        return map;
	}
	
	/**
	 * Saves the given map.
	 * 
	 * @param map The map to save.
	 */
	public static void saveCustomMap(MapTile[][] map) {
		int[] camp = {11,10};
		if(map==null || !map[10][10].getType().equals(MapTiles.CAMP) || !map[10][11].getType().equals(MapTiles.CAMP)
				 || !map[9][11].getType().equals(MapTiles.CAMP) || !map[9][10].getType().equals(MapTiles.CAMP)) {
			System.out.println("Error: Can't save custom map! The map do not have the CAMP at the specified location.");
			return;
		}
		
		if(!Algorithms.hasOutsideConnection(map, new ArrayList<>())) {
			System.out.println("Error: Can't save custom map! The map do not have any outside connection.");
			return;
		}

		//check if all the roads connected at the outside has a path to the camp
		if(!Algorithms.checkPathsFromEdges(map, camp)) {
			System.out.println("Error: Can't save custom map! The map do not have any path to the CAMP.");
			return;
		}
		
		//save map
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ResourceManager.class.getResource("/").getPath() + "/custom_level.txt"))) {
	        for (int row = 0; row < 21; row++) {
	            StringBuilder line = new StringBuilder();

	            for (int col = 0; col < 21; col++) {
	                line.append(map[row][col].getType().name());

	                if (col < 20) {
	                    line.append(";");
	                }
	            }

	            writer.write(line.toString());
	            writer.newLine();
	        }
	        System.out.println("Info: Successfully saved custom map!");
	    } catch (Exception e) {
	        System.out.println("Error: Couldn't save custom level!");
	        e.printStackTrace();
	    }
	}

	public static BufferedImage getEnemyImg(String enemyName, String action) {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/enemies/"+enemyName+"/"+action+".png"));
		} catch (Exception e) {
			System.out.println("Error: Couldn't load enemy image!");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getHeroImage() {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/allies/hero/D_Idle.png")).getSubimage(0, 0, 48, 48);
		} catch (Exception e) {
			System.out.println("Error: Couldn't load image! (hero)");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getCampImage() {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/map_resources/camp.png"));
		} catch (Exception e) {
			System.out.println("Error: Couldn't load camp image!");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getAlly(Allies ally) {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/allies/" + ally.name().toLowerCase() + "/D_Idle.png")).getSubimage(0, 0, 48, 48);
		} catch (Exception e) {
			System.out.println("Error: Couldn't load ally image!");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getArrowImg() {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/arrow.png"));
		} catch (Exception e) {
			System.out.println("Error: Couldn't load arrow image!");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getAllyIcon(Allies allyType) {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/icons/"+allyType.name().toLowerCase()+"_icon.png"));
		} catch (Exception e) {
			System.out.println("Error: Couldn't load ally icon!");
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage getHeartImage() {
		try {
			return ImageIO.read(ResourceManager.class.getResource("/heart.png"));
		} catch (Exception e) {
			System.out.println("Error: Couldn't load heart image!");
			e.printStackTrace();
		}
		return null;
	}
	
}
