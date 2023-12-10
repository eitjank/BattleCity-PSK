public class DestructibleObject extends GameObject {
    protected boolean live = true;

    protected DestructibleObject(int x, int y, int length, int width) {
        super(x, y, length, width);
    }
}
