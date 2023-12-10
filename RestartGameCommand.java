public class RestartGameCommand implements Command{
    private Tank tank;

    public RestartGameCommand(Tank tank) {
        this.tank = tank;
    }
    @Override
    public void execute() {
        tank.restartGame();
    }
    @Override
    public void stopExecution() {
    }
}
