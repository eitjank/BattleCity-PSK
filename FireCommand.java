public class FireCommand implements Command{
    private final Tank tank;

    public FireCommand(Tank tank) {
        this.tank = tank;
    }
    @Override
    public void execute() {
        tank.fire();
    }
    @Override
    public void stopExecution(){
    }
}
