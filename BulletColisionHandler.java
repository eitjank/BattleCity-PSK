import java.util.List;

public class BulletColisionHandler {

    private TankClient tc;

    public BulletColisionHandler(TankClient tc) {
        this.tc = tc;
    }

    public boolean handleHit(Tank tank, Bullets bullet) {
        if (bullet.isLive() && bullet.getRect().intersects(tank.getRect()) && tank.isLive()
                && bullet.isGood() != tank.isGood()) {

            BombTank explosion = new BombTank(tank.getPos().getX(), tank.getPos().getY(), tc);
            tc.bombTanks.add(explosion);

            if (tank.isGood()) {
                tank.setLife(tank.getLife() - 50);
                if (tank.getLife() <= 0)
                    tank.setLive(false);
            } else {
                tank.setLive(false);
            }

            bullet.setLive(false);

            return true;
        }

        return false;
    }

    public boolean handleHit(CommonWall wall, Bullets bullet) {
        return hitCommonWall(wall, bullet);
    }

    public boolean handleHit(MetalWall wall, Bullets bullet) {
        return hitMetalWall(wall, bullet);
    }

    private boolean hitCommonWall(CommonWall wall, Bullets bullet) {
        if (bullet.isLive() && bullet.getRect().intersects(wall.getRect())) {
            bullet.setLive(false);
            tc.otherWall.remove(wall);
            tc.homeWall.remove(wall);
            return true;
        }
        return false;
    }

    private boolean hitMetalWall(MetalWall wall, Bullets bullet) {
        if (bullet.isLive() && bullet.getRect().intersects(wall.getRect())) {
            bullet.setLive(false);
            tc.otherWall.remove(wall);
            tc.homeWall.remove(wall);
            return true;
        }
        return false;
    }

    public boolean handleHit(Bullets otherBullet, Bullets bullet) {
        return hitBullet(otherBullet, bullet);
    }

    public boolean handleHit(Home home, Bullets bullet) {
        return hitHome(home, bullet);
    }

    private boolean hitBullet(Bullets otherBullet, Bullets bullet) {
        if (bullet.isLive() && bullet.getRect().intersects(otherBullet.getRect())) {
            bullet.setLive(false);
            tc.bullets.remove(otherBullet);
            return true;
        }

        return false;
    }

    private boolean hitHome(Home home, Bullets bullet) {
        if (bullet.isLive() && bullet.getRect().intersects(home.getRect())) {
            bullet.setLive(false);
            home.setLive(false);
            return true;
        }

        return false;
    }

    public void handleHitTanks(List<Tank> tanks, Bullets bullet) {
        for (Tank tank : tanks) {
            if (handleHit(tank, bullet)) {
                return;
            }
        }
    }

    public void handleHitCommonWall(CommonWall wall, Bullets bullet) {
        if (handleHit(wall, bullet)) {
            return;
        }
    }

    public void handleHitMetalWall(MetalWall wall, Bullets bullet) {
        if (handleHit(wall, bullet)) {
            return;
        }
    }

    public void handleHitBullet(Bullets otherBullet, Bullets bullet) {
        if (handleHit(otherBullet, bullet)) {
            return;
        }
    }

    public void handleHitHome(Home home, Bullets bullet) {
        if (handleHit(home, bullet)) {
            return;
        }
    }
}
