import java.util.List;

public class CollisionHandler {


    public boolean handleCollisionsWithObject(Tank tank, GameObject gameObject) {
        if (tank.isLive() && tank.getRect().intersects(gameObject.getRect())) {
            tank.handleCollision();
            return true;
        }
        return false;
    }

    public boolean handleCollisionsWithWall(Tank tank, CommonWall wall) {
        if (tank.isLive() && tank.getRect().intersects(wall.getRect())) {
            tank.handleCollision();
            return true;
        }
        return false;
    }

    public boolean handleCollisionsWithWall(Tank tank, MetalWall wall) {
        if (tank.isLive() && tank.getRect().intersects(wall.getRect())) {
            tank.handleCollision();
            return true;
        }
        return false;
    }

    public boolean handleCollisionsWithHome(Tank tank, Home home) {
        if (tank.isLive() && tank.getRect().intersects(home.getRect())) {
            tank.handleCollision();
            return true;
        }
        return false;
    }

    public boolean handleCollisionsWithTanks(Tank tank, List<Tank> tanks) {
        for (Tank t : tanks) {
            if (tank != t && tank.isLive() && t.isLive() && tank.getRect().intersects(t.getRect())) {
                tank.handleCollision();
                t.handleCollision();
                return true;
            }
        }
        return false;
    }
}
