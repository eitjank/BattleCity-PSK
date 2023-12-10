import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bullets {
	public static int speedX = 12;
	public static int speedY = 12;

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	private Direction direction;

	private boolean good;
	private boolean live = true;

	private TankClient tc;
	private static Map<String, Image> imgs = new HashMap<String, Image>();

	public Bullets(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
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
				x -= speedX;
				break;

			case U:
				y -= speedY;
				break;

			case R:
				x += speedX;
				break;

			case D:
				y += speedY;
				break;

			case STOP:
				break;
		}

		if (x < 0 || y < 0 || x > TankClient.FRAME_WIDTH || y > TankClient.FRAME_LENGTH) {
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
				g.drawImage(imgs.get("L"), x, y, null);
				break;

			case U:
				g.drawImage(imgs.get("U"), x, y, null);
				break;

			case R:
				g.drawImage(imgs.get("R"), x, y, null);
				break;

			case D:
				g.drawImage(imgs.get("D"), x, y, null);
				break;
		}

		Image bulletImage = BulletImageLoader.getBulletImage(direction.toString());
		g.drawImage(bulletImage, x, y, null);

		move();
	}

	public boolean isLive() {
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	public boolean isGood() {
		return good;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
}