import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {

    private final java.util.Map<Integer, Command> commandMap = new HashMap<>();


    public KeyHandler(Tank homeTank, Tank homeTank2) {

        commandMap.put(KeyEvent.VK_W, new MoveUpCommand(homeTank));
        commandMap.put(KeyEvent.VK_S, new MoveDownCommand(homeTank));
        commandMap.put(KeyEvent.VK_A, new MoveLeftCommand(homeTank));
        commandMap.put(KeyEvent.VK_D, new MoveRightCommand(homeTank));
        commandMap.put(KeyEvent.VK_F, new FireCommand(homeTank));
        commandMap.put(KeyEvent.VK_R, new RestartGameCommand(homeTank));
        commandMap.put(KeyEvent.VK_UP, new MoveUpCommand(homeTank2));
        commandMap.put(KeyEvent.VK_DOWN, new MoveDownCommand(homeTank2));
        commandMap.put(KeyEvent.VK_LEFT, new MoveLeftCommand(homeTank2));
        commandMap.put(KeyEvent.VK_RIGHT, new MoveRightCommand(homeTank2));
        commandMap.put(KeyEvent.VK_SLASH, new FireCommand(homeTank2));

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(commandMap.containsKey(key)) {
            commandMap.get(key).execute();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(commandMap.containsKey(key)) {
            commandMap.get(key).stopExecution();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}