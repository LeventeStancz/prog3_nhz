package helpers;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import enums.MapTiles;

/**
 * This class represents a tile on the map in the game.
 * 
 */
public class MapTile {
	private MapTiles type;
	private BufferedImage  image;
	
	public MapTile(MapTiles type) {
		this.type = type;
		loadImage();
	}

	/**
	 * Loads the image of the current tile.
	 * 
	 */
	private void loadImage() {
		try {
			String fileName = type.equals(MapTiles.CAMP) ? "grass.png" : type.name().toLowerCase()+".png";
			image = ImageIO.read(getClass().getResource("/map_resources/"+fileName));
		} catch (Exception e) {
			System.out.println("Error: Couldn't load image for map tile!");
			e.printStackTrace();
		}
	}
	
	public MapTiles getType() {
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
}
