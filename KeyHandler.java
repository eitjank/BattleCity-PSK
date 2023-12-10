import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private Tank homeTank;
    private Tank homeTank2;

    public KeyHandler(Tank homeTank, Tank homeTank2, TankClient tankClient) {
        this.homeTank = homeTank;
        this.homeTank2 = homeTank2;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        handlePlayer1Input(key);
        handlePlayer2Input(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        handlePlayer1Release(key);
        handlePlayer2Release(key);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void handlePlayer1Input(int key) {
        switch (key) {
            case KeyEvent.VK_W:
                homeTank.setDirection(Direction.U);
                break;
            case KeyEvent.VK_S:
                homeTank.setDirection(Direction.D);
                break;
            case KeyEvent.VK_A:
                homeTank.setDirection(Direction.L);
                break;
            case KeyEvent.VK_D:
                homeTank.setDirection(Direction.R);
                break;
            case KeyEvent.VK_F:
                homeTank.fire();
                break;
        }
    }

    private void handlePlayer2Input(int key) {
        if (homeTank2 != null) {
            switch (key) {
                case KeyEvent.VK_UP:
                    homeTank2.setDirection(Direction.U);
                    break;
                case KeyEvent.VK_DOWN:
                    homeTank2.setDirection(Direction.D);
                    break;
                case KeyEvent.VK_LEFT:
                    homeTank2.setDirection(Direction.L);
                    break;
                case KeyEvent.VK_RIGHT:
                    homeTank2.setDirection(Direction.R);
                    break;
                case KeyEvent.VK_SLASH:
                    homeTank2.fire();
                    break;
            }
        }
    }

    private void handlePlayer1Release(int key) {
        if (key == KeyEvent.VK_W || key == KeyEvent.VK_S || key == KeyEvent.VK_A || key == KeyEvent.VK_D) {
            homeTank.setDirection(Direction.STOP);
        }
    }

    private void handlePlayer2Release(int key) {
        if (homeTank2 != null) {
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                homeTank2.setDirection(Direction.STOP);
            }
        }
    }
}