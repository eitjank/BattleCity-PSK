import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;


public class River extends GameObject {
	public static final int RIVER_WIDTH = 55;
	public static final int RIVER_LENGTH = 154;
	
	private static final Toolkit tk = Toolkit.getDefaultToolkit();
	private static final Image[] riverImages;
	
	static {   
		riverImages = new Image[]{
				tk.getImage(CommonWall.class.getResource("Images/river.jpg")),
		};
	}

	public River(int x, int y) {
		super(x,y, RIVER_WIDTH, RIVER_LENGTH);
	}
	
	public void draw(Graphics g) {
		g.drawImage(riverImages[0],this.pos.getX(), this.pos.getY(), null);
	}

}
