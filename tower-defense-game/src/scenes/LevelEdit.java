package scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import enums.MapTiles;
import helpers.Algorithms;
import helpers.MapConstants;
import helpers.MapTile;
import managers.ResourceManager;
import managers.SceneManager;
import ui_elements.EditorBar;

/**
 * This class represents the level editing scene.
 * 
 */
public class LevelEdit  extends JPanel implements MouseMotionListener{
	private SceneManager sceneManager;
	private MapTile selectedTile;
	private EditorBar editorBar;
	private boolean drawSelected = false;
	private MapTile[][] originalMap;
	private MapTile[][] editedMap;
	
	private MapTile grassTile;
	private MapTile roadTile;
	private MapTile waterTile;

	private int mouseX;
	private int mouseY;
	
	private int lastTileY;
	private int lastTileX;
	
	public LevelEdit(SceneManager sceneManager){
		originalMap = ResourceManager.getCustomMap();
		editedMap = originalMap;
		grassTile = new MapTile(MapTiles.GRASS);
		roadTile = new MapTile(MapTiles.ROAD);
		waterTile = new MapTile(MapTiles.WATER);
		this.sceneManager = sceneManager;
		
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout());
		editorBar = new EditorBar(this.sceneManager, this);
		this.add(editorBar, BorderLayout.PAGE_END);
		this.addMouseMotionListener(this);
	}
	
	private void drawSelectedTile(Graphics g) {
		if(selectedTile != null && drawSelected) {
			g.drawImage(selectedTile.getImage(),mouseX,mouseY, null);
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintCustomMap(g);
    }
	
	private void paintCustomMap(Graphics g) {
		originalMap = ResourceManager.getCustomMap();
		for(int y = 0; y < originalMap.length; y++) {
        	for(int x = 0; x < originalMap[y].length; x++) {
        		g.drawImage(originalMap[y][x].getImage(), x*32, y*32, null);
        	}
        }
		editedMap = originalMap;
		if(!Algorithms.hasOutsideConnection(editedMap, new ArrayList<>())) {
			editorBar.setHelpTextValue("No outside connections!");
		}else if(!Algorithms.checkPathsFromEdges(editedMap, new int[] {11,10})){
			editorBar.setHelpTextValue("No path to camp!");
		}else {
			editorBar.setHelpTextValue("Click and hold to draw!");
		}
		drawCamp(g);
		drawHero(g);
	}

	private void drawHero(Graphics g) {
		BufferedImage heroImg = ResourceManager.getHeroImage();
		g.drawImage(heroImg, MapConstants.HERO_POS_X, MapConstants.HERO_POS_Y, null);
	}

	private void drawCamp(Graphics g) {
		BufferedImage campImg = ResourceManager.getCampImage();
		g.drawImage(campImg, MapConstants.CAMP_POS_X, MapConstants.CAMP_POS_Y, null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(selectedTile != null) {
			int trueValueX = (e.getPoint().x/32) * 32;
			int trueValueY = (e.getPoint().y/32) * 32;
			if((trueValueY <= 640 && trueValueX <= 640) && (trueValueX >= 0 && trueValueY >= 0)) {
				mouseX = trueValueX;
				mouseY = trueValueY;
				drawSelected = true;
			}else {
				drawSelected = false;
			}
			changeTile(mouseX, mouseY);
		}
	}

	private void changeTile(int x, int y) {
		int tileX = x/32;
		int tileY = y/32;
		
		
		if(lastTileX == tileX && lastTileY == tileY) {
			return;
		}
		
		lastTileX = tileX;
		lastTileY = tileY;
		
		if(editedMap[tileY][tileX].getType().equals(MapTiles.CAMP)) {
			return;
		}
		
		drawSelectedTile(this.getGraphics());
		drawCamp(this.getGraphics());
		drawHero(this.getGraphics());
		
		if(selectedTile.getType() == MapTiles.GRASS) {
			editedMap[tileY][tileX] = grassTile;
		}else {
			editedMap[tileY][tileX] = selectedTile.getType() == MapTiles.ROAD ? roadTile : waterTile;
		}
		
		if(!Algorithms.hasOutsideConnection(editedMap, new ArrayList<>())) {
			editorBar.setHelpTextValue("No outside connections!");
		}else if(!Algorithms.checkPathsFromEdges(editedMap, new int[] {11,10})){
			editorBar.setHelpTextValue("No path to camp!");
		}else {
			editorBar.setHelpTextValue("Click and hold to draw!");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//Called when mouse moves
	}
	
	public void update() {
		drawSelectedTile(this.getGraphics());
	}
	
	public MapTile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(MapTile selectedTile) {
		this.selectedTile = selectedTile;
		drawSelected = true;
	}
	
	public MapTile[][] getEditedMap() {
		return editedMap;
	}
}
