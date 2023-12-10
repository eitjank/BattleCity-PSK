public class MoveRightCommand implements Command{
    private final Tank tank;
    public MoveRightCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setDirection(Direction.R);
    }
    @Override
    public void stopExecution(){
        tank.setDirection(Direction.STOP);
    }
}
