import java.awt.*;

public class CommonWall extends GameObject{
	private int x;
	private int y;
	public static final int COMMON_WALL_WIDTH = 22; 
	public static final int COMMON_WALL_LENGTH = 21;
	private static final Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { 
		tk.getImage(CommonWall.class.getResource("Images/commonWall.gif")), };
	}

	public CommonWall(int x, int y) {
		super(x, y, COMMON_WALL_WIDTH, COMMON_WALL_LENGTH);
	}

	public void draw(Graphics g) {
		g.drawImage(wallImags[0], this.pos.getX(),this.pos.getY(), null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		CommonWall other = (CommonWall) obj;
		return x == other.x && y == other.y;
	}
}
