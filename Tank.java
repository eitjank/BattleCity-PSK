import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Tank {
	private static final int LENGHT = 35;
	private static final int WIDTH = 35;
	private static int speedX = 6;
	private static int speedY = 6;
	private static int count = 0;
	private Position position;
	private TankDrawer drawer;

	public int getSpeedX() {
		return speedX;
	}

	public static void setSpeedX(int speedX) {
		Tank.speedX = speedX;
	}

	public  int getSpeedY() {
		return speedY;
	}

	public static void setSpeedY(int speedY) {
		Tank.speedY = speedY;
	}

	public  int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Tank.count = count;
	}

	private Direction direction = Direction.STOP;
	private Direction myDirection = Direction.U;
	TankClient tc;
	private int player=0;
	private boolean good;
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	private boolean live = true;
	private int life = 200;
	private int rate=1;
	private static Random r = new Random();
	private int step = r.nextInt(10)+5 ;

	private boolean isMovingLeft = false;
	private boolean isMovingUp = false;
	private boolean isMovingRight = false;
	private boolean isMovingDown = false;


	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] tankImags = null;
	static {
		tankImags = new Image[] {
				tk.getImage(BombTank.class.getResource("Images/tankD.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankU.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankL.gif")),
				tk.getImage(BombTank.class.getResource("Images/tankR.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankD.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankU.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankL.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankR.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankD2.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankU2.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankL2.gif")),
				tk.getImage(BombTank.class.getResource("Images/HtankR2.gif")),
		};

	}

	public Tank(int x, int y, boolean good) {
		this.position = new Position(x, y);
		this.oldX = position.getX();
		this.oldY = position.getY();
		this.good = good;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc, int player) {
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
		this.player = player;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}
		switch (myDirection) {

			case D:
				if(player==1){	g.drawImage(tankImags[4], position.getX(), position.getY(), null);
				}
				else if(tc.Player2&&player==2){
					g.drawImage(tankImags[8], position.getX(), position.getY(), null);
				}else{
					g.drawImage(tankImags[0], position.getX(), position.getY(), null);}
				break;

			case U:
				if(player==1){	g.drawImage(tankImags[5], position.getX(), position.getY(), null);
				}else if(tc.Player2&&player==2){
					g.drawImage(tankImags[9], position.getX(), position.getY(), null);
				}else{
					g.drawImage(tankImags[1], position.getX(), position.getY(), null);}
				break;
			case L:if(player==1){	g.drawImage(tankImags[6], position.getX(), position.getY(), null);
			}else if(tc.Player2&&player==2){
				g.drawImage(tankImags[10], position.getX(), position.getY(), null);
			}else{
				g.drawImage(tankImags[2], position.getX(), position.getY(), null);}
				break;

			case R:if(player==1){	g.drawImage(tankImags[7], position.getX(), position.getY(), null);
			}else if(tc.Player2&&player==2){
				g.drawImage(tankImags[11], position.getX(), position.getY(), null);
			}else{
				g.drawImage(tankImags[3], position.getX(), position.getY(), null);}
				break;

		}

		move();
	}

	void move() {
		this.oldX = position.getX();
		this.oldY = position.getY();

		switch (direction) {
			case L:
				position.setX(position.getX() - speedX);
				break;
			case U:
				position.setY(position.getY() - speedY);
				break;
			case R:
				position.setX(position.getX() + speedX);
				break;
			case D:
				position.setY(position.getY() + speedY);
				break;
			case STOP:
				break;
		}

		if (this.direction != Direction.STOP) {
			this.myDirection = this.direction;
		}

		if (position.getX() < 0)
			position.setX(0);
		if (position.getY() < 40)
			position.setY(40);
		if (position.getX() + Tank.WIDTH > TankClient.Fram_width)
			x = TankClient.Fram_width - Tank.WIDTH;
		if (position.getY() + Tank.LENGHT > TankClient.Fram_length)
			y = TankClient.Fram_length - Tank.LENGHT;

		if (!good) {
			Direction[] directons = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int mod=r.nextInt(9);
				if (playertankaround()){
					if(position.getX()==tc.homeTank.x){ if(position.getY()>tc.homeTank.y) direction=directons[1];
					else if (position.getY()<tc.homeTank.y) direction=directons[3];
					}else if(position.getY()==tc.homeTank.y){ if(position.getX()>tc.homeTank.x) direction=directons[0];
					else if (position.getX()<tc.homeTank.x) direction=directons[2];
					}
					else{
						int rn = r.nextInt(directons.length);
						direction = directons[rn];
					}
					rate=2;
				}else if (mod==1){
					rate=1;
				}else if(1<mod&&mod<=3){
					rate=1;
				}else{
					int rn = r.nextInt(directons.length);
					direction = directons[rn];
					rate=1;}
			}
			step--;
			if(rate==2){
				if (r.nextInt(40) > 35)
					this.fire();
			}else if (r.nextInt(40) > 38)
				this.fire();
		}
	}
	public boolean playertankaround(){
		int rx=position.getX()-15;
		int ry=position.getY()-15;
		if((position.getX()-15)<0) rx=0;
		if((position.getY()-15)<0)ry=0;
		Rectangle a=new Rectangle(rx, ry,60,60);
		if (this.live && a.intersects(tc.homeTank.getRect())) {
			return true;
		}
		return false;
	}
	public int getDirect(int b){
		return 4;
	}
	private void changToOldDir() {
		position.setX(oldX);
		position.setY(oldY);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (player==1){
			switch (key) {
				case KeyEvent.VK_R:
					restartGame();
					break;

				case KeyEvent.VK_D:
					isMovingRight = true;
					break;

				case KeyEvent.VK_A:
					isMovingLeft = true;
					break;

				case KeyEvent.VK_W:
					isMovingUp = true;
					break;

				case KeyEvent.VK_S:
					isMovingDown = true;
					break;
			}}
		if (player==2){
			switch(key){
				case KeyEvent.VK_RIGHT:
					isMovingRight = true;
					break;

				case KeyEvent.VK_LEFT:
					isMovingLeft = true;
					break;

				case KeyEvent.VK_UP:
					isMovingUp = true;
					break;

				case KeyEvent.VK_DOWN:
					isMovingDown = true;
					break;
			}
		}
		decideDirection();
	}

	private void initializeEnemyTanks() {
		for (int i = 0; i < 20; i++) {
			if (i < 9)
				tc.tanks.add(new Tank(150 + 70 * i, 40, false, Direction.R, tc, 0));
			else if (i < 15)
				tc.tanks.add(new Tank(700, 140 + 50 * (i - 6), false, Direction.D, tc, 0));
			else
				tc.tanks.add(new Tank(10, 50 * (i - 12), false, Direction.L, tc, 0));
		}
	}

	private void restartGame() {
		tc.tanks.clear();
		tc.bullets.clear();
		tc.trees.clear();
		tc.otherWall.clear();
		tc.homeWall.clear();
		tc.metalWall.clear();
		tc.homeTank.setLive(false);

		if (tc.tanks.size() == 0) {
			initializeEnemyTanks();
		}

		tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc, 0);

		if (!tc.home.isLive())
			tc.home.setLive(true);

		TankClient abc = new TankClient();
		if (tc.Player2) abc.Player2 = true;
	}

	void decideDirection() {
		if (!isMovingLeft && !isMovingUp && isMovingRight && !isMovingDown)
			direction = Direction.R;

		else if (isMovingLeft && !isMovingUp && !isMovingRight && !isMovingDown)
			direction = Direction.L;

		else if (!isMovingLeft && isMovingUp && !isMovingRight && !isMovingDown)
			direction = Direction.U;

		else if (!isMovingLeft && !isMovingUp && !isMovingRight && isMovingDown)
			direction = Direction.D;

		else if (!isMovingLeft && !isMovingUp && !isMovingRight && !isMovingDown)
			direction = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (player==1){
			switch (key) {

				case KeyEvent.VK_F:
					fire();
					break;

				case KeyEvent.VK_D:
					isMovingRight = false;
					break;

				case KeyEvent.VK_A:
					isMovingLeft = false;
					break;

				case KeyEvent.VK_W:
					isMovingUp = false;
					break;

				case KeyEvent.VK_S:
					isMovingDown = false;
					break;


			}}
		if (player==2){
			switch (key) {

				case KeyEvent.VK_SLASH:
					fire();
					break;

				case KeyEvent.VK_RIGHT:
					isMovingRight = false;
					break;

				case KeyEvent.VK_LEFT:
					isMovingLeft = false;
					break;

				case KeyEvent.VK_UP:
					isMovingUp = false;
					break;

				case KeyEvent.VK_DOWN:
					isMovingDown = false;
					break;


			}}
		decideDirection();
	}

	public Bullets fire() {
		if (!live)
			return null;
		int X = position.getX() + Tank.WIDTH / 2 - Bullets.width / 2;
		int Y = position.getY()  + Tank.LENGHT / 2 - Bullets.length / 2;
		Bullets m = new Bullets(X, Y + 2, good, myDirection, this.tc);
		tc.bullets.add(m);
		return m;
	}


	public Rectangle getRect() {
		return new Rectangle(position.getX(), position.getY(), WIDTH, LENGHT);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public boolean collideWithWall(CommonWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithWall(MetalWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideRiver(River r) {
		if (this.live && this.getRect().intersects(r.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideHome(Home h) {
		if (this.live && this.getRect().intersects(h.getRect())) {
			this.changToOldDir();
			return true;
		}
		return false;
	}

	public boolean collideWithTanks(List<Tank> tanks) {
		for (Tank t : tanks) {
			if (this != t && this.live && t.isLive() && this.getRect().intersects(t.getRect())) {
				this.changToOldDir();
				t.changToOldDir();
				return true;
			}
		}
		return false;
	}


	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean eat(GetBlood b) {
		if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
			if(this.life<=100)
				this.life = this.life+100;
			else
				this.life = 200;
			b.setLive(false);
			return true;
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}