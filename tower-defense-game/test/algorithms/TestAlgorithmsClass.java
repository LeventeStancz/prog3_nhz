package algorithms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import enums.MapTiles;
import helpers.Algorithms;
import helpers.MapTile;
import helpers.Point;

public class TestAlgorithmsClass {

	@Test
    public void testHasOutsideConnection() {
        MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS)}
        };

        List<Point> points = new ArrayList<>();
        assertTrue(Algorithms.hasOutsideConnection(map, points));
        assertEquals(2, points.size());
        assertTrue(points.get(0).x == 0);
        assertTrue(points.get(0).y == 0);
        assertTrue(points.get(1).x == 0);
        assertTrue(points.get(1).y == 2);
    }

    @Test
    public void testCheckPathsFromEdges() {
        MapTile[][] mapWithRoad = {
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS)}
        };

        int[] destToRoad = {1,1};
        
        assertTrue(Algorithms.checkPathsFromEdges(mapWithRoad, destToRoad));

        MapTile[][] mapWithoutRoad = {
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS),new MapTile(MapTiles.GRASS)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

        assertFalse(Algorithms.checkPathsFromEdges(mapWithoutRoad, destToRoad));
    }

    @Test
    public void testShortestPath() {
        MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.ROAD), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.GRASS)}
        };

        int[] start = {0, 0};
        int[] end = {2, 3};
        List<Algorithms.Cell> path = Algorithms.shortestPath(map, start, end);

        assertFalse(path.isEmpty());
        assertEquals(5, path.size());
        assertEquals(1, path.get(0).getColNum());
        assertEquals(0, path.get(0).getRowNum());
        assertEquals(2, path.get(1).getColNum());
        assertEquals(0, path.get(1).getRowNum());
        assertEquals(3, path.get(2).getColNum());
        assertEquals(0, path.get(2).getRowNum());
        assertEquals(3, path.get(3).getColNum());
        assertEquals(1, path.get(3).getRowNum());
        assertEquals(3, path.get(4).getColNum());
        assertEquals(2, path.get(4).getRowNum());
    }

}
