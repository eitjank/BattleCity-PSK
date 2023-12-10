import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TankCollisionTests {
    @Test
    public void testCollisionWithWall() {
        TankClient tc = new TankClient();
        Tank tank = new Tank(0, 0, true, Direction.U, tc, 1);
        CommonWall wall = new CommonWall(0, 0);
        assertTrue(tank.collideWithObject(wall));
    }

    @Test
    public void testCollisionWithWallNoIntersection() {
        TankClient tc = new TankClient();
        Tank tank = new Tank(0, 0, true, Direction.U, tc, 1);
        CommonWall wall = new CommonWall(100, 100);
        assertFalse(tank.collideWithObject(wall));
    }

    @Test
    public void testCollisionWithWallTankNotLive() {
        TankClient tc = new TankClient();
        Tank tank = new Tank(0, 0, true, Direction.U, tc, 1);
        tank.setLive(false);
        CommonWall wall = new CommonWall(0, 0);
        assertFalse(tank.collideWithObject(wall));
    }
}
