import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Bullets extends DestructibleObject {
	public static int speedX = 12;
	public static int speedY = 12;

	public static final int WIDTH = 10;
	public static final int LENGTH = 10;

	private Direction direction;

	private boolean good;

	private TankClient tc;
	private static final Map<String, Image> imgs = new HashMap<String, Image>();

	public Bullets(int x, int y, Direction dir) {
		super(x, y, WIDTH, LENGTH);
		this.direction = dir;
	}

	public Bullets(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}

	private void move() {
		switch (direction) {
			case L:
				this.setPos(new Position(this.pos.getX() - speedX, this.pos.getY()));
				break;

			case U:
				this.setPos(new Position(this.pos.getX(), this.pos.getY() - speedY));
				break;

			case R:
				this.setPos(new Position(this.pos.getX() + speedX, this.pos.getY()));
				break;

			case D:
				this.setPos(new Position(this.pos.getX(), this.pos.getY() + speedY));
				break;

			case STOP:
				break;
		}

		if (this.pos.getX() < 0 || this.pos.getY() < 0 || this.pos.getX() > TankClient.FRAME_WIDTH || this.pos.getY() > TankClient.FRAME_LENGTH) {
			live = false;
		}
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}

		switch (direction) {
			case L:
				g.drawImage(imgs.get("L"), this.pos.getX(), this.pos.getY(), null);
				break;

			case U:
				g.drawImage(imgs.get("U"), this.pos.getX(), this.pos.getY(), null);
				break;

			case R:
				g.drawImage(imgs.get("R"), this.pos.getX(), this.pos.getY(), null);
				break;

			case D:
				g.drawImage(imgs.get("D"), this.pos.getX(), this.pos.getY(), null);
				break;
		}

		Image bulletImage = BulletImageLoader.getBulletImage(direction.toString());
		g.drawImage(bulletImage, this.pos.getX(), this.pos.getY(), null);

		move();
	}

	public boolean isGood() {
		return good;
	}
}