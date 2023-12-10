import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankClientTest {

    @Test
    public void testInitializeHomeWalls() {
        TankClient tankClient = new TankClient();

        List<CommonWall> homeWall = tankClient.getHomeWall();

        assertEquals(10, homeWall.size(), "Size of homeWall should be 10");

        assertEquals(new CommonWall(350, 580), homeWall.get(0), "Invalid wall at index 0");
        assertEquals(new CommonWall(350, 559), homeWall.get(1), "Invalid wall at index 1");
        assertEquals(new CommonWall(350, 538), homeWall.get(2), "Invalid wall at index 2");
        assertEquals(new CommonWall(350, 517), homeWall.get(3), "Invalid wall at index 3");

        assertEquals(new CommonWall(372, 517), homeWall.get(4), "Invalid wall at index 4");
        assertEquals(new CommonWall(394, 517), homeWall.get(5), "Invalid wall at index 5");
        assertEquals(new CommonWall(416, 517), homeWall.get(6), "Invalid wall at index 6");

        assertEquals(new CommonWall(416, 538), homeWall.get(7), "Invalid wall at index 7");
        assertEquals(new CommonWall(416, 559), homeWall.get(8), "Invalid wall at index 8");
        assertEquals(new CommonWall(416, 580), homeWall.get(9), "Invalid wall at index 9");

    }
}