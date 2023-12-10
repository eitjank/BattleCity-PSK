public class MoveDownCommand implements Command{
    private final Tank tank;
    public MoveDownCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setDirection(Direction.D);
    }
    @Override
    public void stopExecution(){
        tank.setDirection(Direction.STOP);
    }
}
