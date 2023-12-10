public class MoveRightCommand implements Command{
    private final Tank tank;
    public MoveRightCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setMovingRight(true);
    }
    @Override
    public void stopExecution(){
        tank.setMovingRight(false);
    }
}
