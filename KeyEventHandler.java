import java.awt.event.KeyEvent;

public class KeyEventHandler {
    private boolean movingLeft = false;
    private boolean movingUp = false;
    private boolean movingRight = false;
    private boolean movingDown = false;

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                movingLeft = true;
                break;
            case KeyEvent.VK_UP:
                movingUp = true;
                break;
            case KeyEvent.VK_RIGHT:
                movingRight = true;
                break;
            case KeyEvent.VK_DOWN:
                movingDown = true;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                movingLeft = false;
                break;
            case KeyEvent.VK_UP:
                movingUp = false;
                break;
            case KeyEvent.VK_RIGHT:
                movingRight = false;
                break;
            case KeyEvent.VK_DOWN:
                movingDown = false;
                break;
        }
    }

    public Direction getDirection() {
        if (movingRight && !movingUp && !movingLeft && !movingDown) return Direction.R;
        if (movingLeft && !movingUp && !movingRight && !movingDown) return Direction.L;
        if (!movingLeft && movingUp && !movingRight && !movingDown) return Direction.U;
        if (!movingLeft && !movingUp && !movingRight && movingDown) return Direction.D;
        return Direction.STOP;
    }
}
