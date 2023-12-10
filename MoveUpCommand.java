public class MoveUpCommand implements Command{
    private final Tank tank;
    public MoveUpCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setMovingUp(true);
    }
    @Override
    public void stopExecution(){
        tank.setMovingUp(false);
    }
}
