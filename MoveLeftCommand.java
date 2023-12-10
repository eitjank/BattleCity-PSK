public class MoveLeftCommand implements Command{
    private final Tank tank;
    public MoveLeftCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setMovingLeft(true);
    }
    @Override
    public void stopExecution(){
        tank.setMovingLeft(false);
    }
}
