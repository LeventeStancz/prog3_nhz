package helpers;

import java.util.LinkedList;
import java.util.List;
import enums.Directions;
import enums.MapTiles;

/**
 * This class contains the algorithms used in the game.
 * 
 */
public class Algorithms {
	private Algorithms() {}
    
	/**
	 * Checks if in the given 2D array has a ROAD type of tile on each side.
	 * Collects the ROAD type of tiles coordinates into a list.
	 * 
	 * @param map The 2D array the algorithm runs on.
	 * @param points The list where the algorithms collects ROAD type of tile coordinates.
	 * @return Given 2D array has ROAD type of tile on any of the edge.
	 */
    public static boolean hasOutsideConnection(MapTile[][] map, List<Point> points) {
		int rows = map.length;
	    int cols = map[0].length;

	    boolean roadOnLeft = false;
	    for (int col = 0; col < cols; col++) {
	        if (map[col][0].getType().equals(MapTiles.ROAD)) {
	        	roadOnLeft = true;
	            points.add(new Point(0, col));
	        }
	    }

	    boolean roadOnBottom = false;
	    for (int row = 1; row < rows; row++) {
	        if (map[cols - 1][row].getType().equals(MapTiles.ROAD)) {
	        	roadOnBottom = true;
	            points.add(new Point(row, cols - 1));
	        }
	    }

	    boolean roadOnRight = false;
	    for (int col = cols - 2; col >= 0; col--) {
	        if (map[col][rows - 1].getType().equals(MapTiles.ROAD)) {
	        	roadOnRight = true;
	            points.add(new Point(rows - 1, col));
	        }
	    }

	    boolean roadOnTop = false;
	    for (int row = rows - 2; row > 0; row--) {
	        if (map[0][row].getType().equals(MapTiles.ROAD)) {
	        	roadOnTop = true;
	            points.add(new Point(row, 0));
	        }
	    }

	    return roadOnTop || roadOnRight || roadOnBottom || roadOnLeft;
	}
    
    /**
	 * If there is a road on the edge of the passed 2D array this algorithm finds the shortest path
	 * from that tile to the CAMP tile.
	 * 
	 * @param map The 2D array the algorithm runs on.
	 * @return If there is a ROAD on the edge there is a shortest path to it.
	 */
    public static boolean checkPathsFromEdges(MapTile[][] map, 	int[] dest) {
		int cols = map.length;
        int rows = map[0].length;
        
        // Felső
        for (int col = 0; col < cols; col++) {
        	int[] start = {col, 0};
        	if (map[col][0].getType().equals(MapTiles.ROAD) && shortestPath(map, start, dest).isEmpty()) {
                return false;
            }
        }
        // Jobb
        for (int row = 1; row < rows; row++) {
        	int[] start = {cols-1, row};
        	if (map[cols-1][row].getType().equals(MapTiles.ROAD) && shortestPath(map, start, dest).isEmpty()) {
                return false;
            }
        }
        // Alsó
        for (int col = cols - 2; col >= 0; col--) {
        	int[] start = {col, rows-1};
        	if (map[col][rows-1].getType().equals(MapTiles.ROAD) && shortestPath(map, start, dest).isEmpty()) {
                return false;
            }
        }
        // Bal
        for (int row = rows - 2; row > 0; row--) {
        	int[] start = {0, row};
        	if (map[0][row].getType().equals(MapTiles.ROAD) && shortestPath(map, start, dest).isEmpty()) {
                return false;
            }
        }
        
        return true;
    }

    /**
	 * Helper class for the BFS algorithm.
	 * 
	 */
    public static class Cell {
        int rowNum;
        int colNum;
        int dist;
        Cell prev;
        Directions direction;

        Cell(int x, int y, int dist, Cell prev, Directions direction) {
            this.rowNum = x;
            this.colNum = y;
            this.dist = dist;
            this.prev = prev;
            this.direction = direction;
        }
        
        public Directions getDir() {
        	return direction;
        }
        
        public int getRowNum() {
        	return rowNum;
        }
        
        public int getColNum() {
        	return colNum;
        }
    }

    /**
	 * BFS algorithm that finds the shortest path in the given 2D array from the passed
	 * start and end coordinates.
	 * 
	 * @param matrix The 2D array the algorithm runs on.
	 * @param start The start coordinate.
	 * @param end The destination coordinate.
	 * @return List of Cell-s which is the shortest path to the destination coordinate from the start coordinate.
	 */
    public static List<Cell> shortestPath(MapTile[][] matrix, int[] start, int[] end) {
        int sx = start[0];
        int sy = start[1];
        int dx = end[0];
        int dy = end[1];

        if (!matrix[sx][sy].getType().equals(MapTiles.ROAD)) {
            return new LinkedList<>();
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        Cell[][] cells = new Cell[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j].getType().equals(MapTiles.ROAD)) {
                    cells[i][j] = new Cell(i, j, Integer.MAX_VALUE, null, null);
                }
            }
        }
        
        LinkedList<Cell> queue = new LinkedList<>();
        Cell src = cells[sx][sy];
        src.dist = 0;
        queue.add(src);
        Cell dest = null;
        Cell p;
        while ((p = queue.poll()) != null) {
            if (p.rowNum == dx && p.colNum == dy) {
                dest = p;
                break;
            }
            
            visit(cells, queue, p.rowNum - 1, p.colNum, p, Directions.UP);
            visit(cells, queue, p.rowNum + 1, p.colNum, p, Directions.DOWN);
            visit(cells, queue, p.rowNum, p.colNum - 1, p, Directions.LEFT);
            visit(cells, queue, p.rowNum, p.colNum + 1, p, Directions.RIGHT);
        }

        if (dest == null) {
            return new LinkedList<>();
        } else {
            LinkedList<Cell> path = new LinkedList<>();
            while (p != null) {
                path.addFirst(p);
                p = p.prev;
            }
            //remove starting coordinate
            path.removeFirst();
            
            return path;
        }
    }

    /**
	 * Explores the neighboring cells of a given coordinate during BFS traversal.
	 * Updates the distance and path information for each reachable cell if a shorter path is found.
	 * 
	 * @param cells The 2D array representing the grid of cells.
	 * @param queue The queue used in BFS traversal.
	 * @param x The x-coordinate of the current cell.
	 * @param y The y-coordinate of the current cell.
	 * @param parent The parent cell from which the traversal is performed.
	 * @param direction The direction of the traversal from the parent cell.
	 */
    private static void visit(Cell[][] cells, LinkedList<Cell> queue, int x, int y, Cell parent, Directions direction) {
        if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length || cells[x][y] == null) {
            return;
        }

        int dist = parent.dist + 1;
        Cell p = cells[x][y];
        if (dist < p.dist) {
            p.dist = dist;
            p.prev = parent;
            p.direction = direction;
            queue.add(p);
        }
    }
}

