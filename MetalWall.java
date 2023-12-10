import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class MetalWall extends GameObject {
	public static final int WIDTH = 36;
	public static final int LENGTH = 37;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("Images/metalWall.gif")), };
	}

	public MetalWall(int x, int y) {
		super(x, y, WIDTH, LENGTH);
	}

	public void draw(Graphics g) { 
		g.drawImage(wallImags[0], this.pos.getX(), this.pos.getY(), null);
	}

}
