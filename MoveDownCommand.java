public class MoveDownCommand implements Command{
    private final Tank tank;
    public MoveDownCommand(Tank tank){
        this.tank = tank;
    }
    @Override
    public void execute(){
        tank.setMovingDown(true);
    }
    @Override
    public void stopExecution(){
        tank.setMovingDown(false);
    }
}
