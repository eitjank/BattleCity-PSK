public class MoveUpCommand implements Command{
    private final Tank tank;
    public MoveUpCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setDirection(Direction.U);
    }
    @Override
    public void stopExecution(){
        tank.setDirection(Direction.STOP);
    }
}
