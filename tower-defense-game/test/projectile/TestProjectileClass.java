package projectile;

import static org.junit.Assert.*;
import java.awt.geom.Point2D;
import org.junit.Test;
import helpers.Projectile;

public class TestProjectileClass {

	@Test
	public void testMove() {
        Projectile projectile = new Projectile(0, 0, 1.0f, 2.0f, 10, 45.0f);

        projectile.move();

        assertEquals(1.0f, projectile.getPos().x, 0.001);
        assertEquals(2.0f, projectile.getPos().y, 0.001);
    }

    @Test
    public void testGetters() {
        Projectile projectile = new Projectile(10, 20, 2.0f, 3.0f, 15, 90.0f);

        assertEquals(new Point2D.Float(10, 20), projectile.getPos());
        assertTrue(projectile.isActive());
        assertEquals(15, projectile.getDamage());
        assertEquals(90.0f, projectile.getRotation(), 0.001);
    }

    @Test
    public void testSetters() {
        Projectile projectile = new Projectile(5, 5, 1.0f, 1.0f, 5, 180.0f);

        projectile.setPos(new Point2D.Float(8, 8));
        assertTrue(projectile.isActive());
        assertEquals(8, projectile.getPos().x, 0.001);
        assertEquals(8, projectile.getPos().y, 0.001);
        projectile.setActive(false);
        assertFalse(projectile.isActive());

        projectile.setDamage(20);
        assertEquals(20, projectile.getDamage());

        projectile.setRotation(270.0f);
        assertEquals(270.0f, projectile.getRotation(), 0.001);
    }

}
