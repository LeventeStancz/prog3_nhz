package enemy;

import static org.junit.Assert.*;
import java.awt.Rectangle;
import org.junit.Test;
import enemies.Bee;
import enemies.Enemy;
import enums.Directions;
import enums.EnemyTypes;
import enums.MapTiles;
import helpers.MapTile;
import managers.EnemyManager;

public class TestEnemyClass {

	@Test
    public void testMove() {
		MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

		Enemy enemy = new Bee(map, 0, 0, new int[] {2, 0}, EnemyTypes.BEE, new EnemyManager());

        enemy.move(1.0f, Directions.DOWN);
        assertEquals(1.0f, enemy.getY(), 0.001);

        enemy.move(1.0f, Directions.RIGHT);
        assertEquals(1.0f, enemy.getX(), 0.001);

        enemy.move(1.0f, Directions.UP);
        assertEquals(0.0f, enemy.getY(), 0.001);

        enemy.move(1.0f, Directions.LEFT);
        assertEquals(0.0f, enemy.getX(), 0.001);
    }

    @Test
    public void testHurt() {
    	MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

    	Enemy enemy = new Bee(map, 0, 0, new int[] {2, 0}, EnemyTypes.BEE, new EnemyManager());
        int initialHealth = enemy.getHealth();

        enemy.hurt(10);
        assertEquals((initialHealth - 10), enemy.getHealth());

        enemy.hurt(5);
        assertEquals((initialHealth - 15), enemy.getHealth());
    }

    @Test
    public void testKill() {
    	MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

    	Enemy enemy = new Bee(map, 0, 0, new int[] {2, 0}, EnemyTypes.BEE, new EnemyManager());
        assertTrue(enemy.isAlive());

        enemy.kill();
        assertFalse(enemy.isAlive());
        assertEquals(0, enemy.getHealth());
    }

    @Test
    public void testUpdateHitBox() {
    	MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

    	Enemy enemy = new Bee(map, 0, 0, new int[] {2, 0}, EnemyTypes.BEE, new EnemyManager());
        assertEquals(new Rectangle(0, 0, 36, 36), enemy.getHitbox());

        enemy.move(1.0f, Directions.RIGHT);
        enemy.updateHitBox();
        assertEquals(new Rectangle(1, 0, 36, 36), enemy.getHitbox());

        enemy.move(1.0f, Directions.DOWN);
        enemy.updateHitBox();
        assertEquals(new Rectangle(1, 1, 36, 36), enemy.getHitbox());
    }

    @Test
    public void testGetMissingHealth() {
    	MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

    	Enemy enemy = new Bee(map, 0, 0, new int[] {2, 0}, EnemyTypes.BEE, new EnemyManager());
        enemy.hurt(10);
        assertEquals((enemy.getMaxHealth() - enemy.getHealth()), enemy.getMissingHealth());
    }

    @Test
    public void testEnemyAttributes() {
        MapTile[][] map = {
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)},
                {new MapTile(MapTiles.ROAD), new MapTile(MapTiles.GRASS), new MapTile(MapTiles.ROAD)}
        };

        Enemy enemy = new Bee(map, 0, 0, new int[] {2, 0}, EnemyTypes.BEE, new EnemyManager());

        assertEquals(EnemyTypes.BEE, enemy.getType());
        assertEquals(Directions.DOWN, enemy.getLastDir());
        assertEquals(0, enemy.getxOffset());
        assertEquals(0, enemy.getyOffset());
        assertTrue(enemy.isAlive());

        enemy.setxOffset(10);
        enemy.setyOffset(-5);
        enemy.setLastDir(Directions.LEFT);
        enemy.setSpeed(2.0f);
        enemy.setHealth(50);

        assertEquals(10, enemy.getxOffset());
        assertEquals(-5, enemy.getyOffset());
        assertEquals(Directions.LEFT, enemy.getLastDir());
        assertEquals(50, enemy.getHealth());
    }

}
