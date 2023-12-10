public abstract class DestructibleObject extends GameObject {
    protected boolean live = true;

    protected DestructibleObject(int x, int y, int length, int width) {
        super(x, y, length, width);
    }
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

}
