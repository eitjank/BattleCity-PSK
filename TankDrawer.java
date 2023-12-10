import java.awt.*;

public class TankDrawer {
    private static final Toolkit tk = Toolkit.getDefaultToolkit();
    private static final Image[] tankImages;
    static {
        tankImages = new Image[] {
                tk.getImage(BombTank.class.getResource("Images/tankL.gif")),
                tk.getImage(BombTank.class.getResource("Images/tankU.gif")),
                tk.getImage(BombTank.class.getResource("Images/tankR.gif")),
                tk.getImage(BombTank.class.getResource("Images/tankD.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankL.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankU.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankR.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankD.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankL2.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankU2.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankR2.gif")),
                tk.getImage(BombTank.class.getResource("Images/HtankD2.gif")),
        };

    }

    public void draw(Graphics g, Position position, Direction direction, int player) {
        int imageIndex = direction.ordinal();
        if(player == 1)
            imageIndex += 4;
        else if(player == 2){
            imageIndex += 8;
        }
        g.drawImage(tankImages[imageIndex], position.getX(), position.getY(), null);
    }
}
