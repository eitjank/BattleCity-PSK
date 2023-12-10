import java.util.Random;

public class TankHandler {
    private static final int PLAYER_TANK_RATE = 2;
    private static final int NO_PLAYER_TANK_RATE = 1;

    private Tank tank;
    private Random random = new Random();

    public TankHandler(Tank tank) {
        this.tank = tank;
    }

    public void handlePlayerTankAround(Direction[] directions) {
        int currentX = tank.getPos().getX();
        int currentY = tank.getPos().getY();
        int homeTankX = tank.getTc().homeTank.getPos().getX();
        int homeTankY = tank.getTc().homeTank.getPos().getY();

        if (currentX == homeTankX) {
            handlePlayerTankAroundX(directions, currentY, homeTankY);
        } else if (currentY == homeTankY) {
            handlePlayerTankAroundY(directions, currentX, homeTankX);
        } else {
            handleRandomDirection(directions);
        }

        tank.setRate(PLAYER_TANK_RATE);
    }

    private void handlePlayerTankAroundX(Direction[] directions, int currentY, int homeTankY) {
        if (currentY > homeTankY) {
            tank.setDirection(directions[1]);
        } else if (currentY < homeTankY) {
            tank.setDirection(directions[3]);
        }
    }

    private void handlePlayerTankAroundY(Direction[] directions, int currentX, int homeTankX) {
        if (currentX > homeTankX) {
            tank.setDirection(directions[0]);
        } else if (currentX < homeTankX) {
            tank.setDirection(directions[2]);
        } else {
            handleRandomDirection(directions);
        }
    }

    private void handleRandomDirection(Direction[] directions) {
        int rn = random.nextInt(directions.length);
        tank.setDirection(directions[rn]);
    }

    public void handleNoPlayerTankAround(int mod, Direction[] directions) {
        if (mod != 1 && (1 >= mod || mod > 3)) {
            handleRandomDirection(directions);
        }
        tank.setRate(NO_PLAYER_TANK_RATE);
    }

    public void handleRate() {
        int currentRate = tank.getRate();
        int randomValue = random.nextInt(40);

        if ((currentRate == PLAYER_TANK_RATE && randomValue > 35) ||
                (currentRate != PLAYER_TANK_RATE && randomValue > 38)) {
            tank.fire();
        }
    }

    public void handleBoundaries() {
        int tankWidth = Tank.getTankWidth();
        int tankLength = Tank.getTankLength();
        int frameWidth = TankClient.FRAME_WIDTH;
        int frameLength = TankClient.FRAME_LENGTH;

        int currentX = tank.getPos().getX();
        int currentY = tank.getPos().getY();

        if (currentX < 0) tank.getPos().setX(0);
        if (currentY < 40) tank.getPos().setY(40);
        if (currentX + tankWidth > frameWidth) tank.getPos().setX(frameWidth - tankWidth);
        if (currentY + tankLength > frameLength) tank.getPos().setY(frameWidth - tankLength);
    }
}