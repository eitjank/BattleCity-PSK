public class MoveLeftCommand implements Command{
    private final Tank tank;
    public MoveLeftCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setDirection(Direction.L);
    }
    @Override
    public void stopExecution(){
        tank.setDirection(Direction.STOP);
    }
}
