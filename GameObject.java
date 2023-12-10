import java.awt.*;

public abstract class GameObject {
    protected Position pos;
    protected final int length;
    protected final int width;

    protected GameObject(int x, int y, int length, int width) {
        this.pos = new Position(x, y);
        this.length = length;
        this.width = width;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Rectangle getRect() {
        return new Rectangle(this.pos.getX(), this.pos.getY(), width, length);
    }
}
