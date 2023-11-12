import java.awt.*;

public class TankDrawer {
    private Image[] tankImages;

    public TankDrawer(Image[] tankImages) {
        this.tankImages = tankImages;
    }

    public void draw(Graphics g, Position position, Direction direction, int player) {
        int imageIndex = 4 * (direction.ordinal()) + (player == 2 ? 4 : 0);
        g.drawImage(tankImages[imageIndex], position.getX(), position.getY(), null);
    }
}
