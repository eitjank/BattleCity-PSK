import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Tank extends DestructibleObject {
	private CollisionHandler collisionHandler = new CollisionHandler();
	private static final int TANK_LENGTH = 35;
	private static final int TANK_WIDTH = 35;
	private static int speedX = 6;
	private static int speedY = 6;
	private static int count = 0;
	private static final TankDrawer drawer = new TankDrawer();
	private Direction direction = Direction.STOP;
	private Direction myDirection = Direction.U;
	TankClient tc;
	private int player=0;
	private final java.util.Map<Integer, Command> commandMap = new HashMap<>();
	private boolean good;
	private int oldX;
	private int oldY;
	private int life = 200;
	private int rate=1;
	private static final Random r = new Random();
	private int step = r.nextInt(10)+5 ;

	private boolean isMovingLeft = false;
	private boolean isMovingUp = false;
	private boolean isMovingRight = false;
	private boolean isMovingDown = false;
	private TankHandler tankHandler;

	public Tank(int x, int y, boolean good) {
		super(x, y, TANK_WIDTH, TANK_LENGTH);
		this.oldX = this.pos.getX();
		this.oldY = this.pos.getY();
		this.good = good;
		tankHandler = new TankHandler(this);
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc, int player) {
		this(x, y, good);
		this.direction = dir;
		this.tc = tc;
		this.player = player;
		if(player == 1) {
			commandMap.put(KeyEvent.VK_W, new MoveUpCommand(this));
			commandMap.put(KeyEvent.VK_S, new MoveDownCommand(this));
			commandMap.put(KeyEvent.VK_A, new MoveLeftCommand(this));
			commandMap.put(KeyEvent.VK_D, new MoveRightCommand(this));
			commandMap.put(KeyEvent.VK_F, new FireCommand(this));
		}
		else if(player == 2) {
			commandMap.put(KeyEvent.VK_UP, new MoveUpCommand(this));
			commandMap.put(KeyEvent.VK_DOWN, new MoveDownCommand(this));
			commandMap.put(KeyEvent.VK_LEFT, new MoveLeftCommand(this));
			commandMap.put(KeyEvent.VK_RIGHT, new MoveRightCommand(this));
			commandMap.put(KeyEvent.VK_SLASH, new FireCommand(this));
		}
		commandMap.put(KeyEvent.VK_R, new RestartGameCommand(this));
	}

	public static void setSpeedX(int speedX) {
		Tank.speedX = speedX;
	}

	public static void setSpeedY(int speedY) {
		Tank.speedY = speedY;
	}

	public static void setCount(int count) {
		Tank.count = count;
	}

	public void setMovingLeft(boolean movingLeft) {
		isMovingLeft = movingLeft;
	}

	public void setMovingUp(boolean movingUp) {
		isMovingUp = movingUp;
	}

	public void setMovingRight(boolean movingRight) {
		isMovingRight = movingRight;
	}

	public void setMovingDown(boolean movingDown) {
		isMovingDown = movingDown;
	}

	public void draw(Graphics g) {
		if (!this.live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}
        drawer.draw(g, this.pos, myDirection, this.player);

		move();
	}

	void move() {
		this.oldX = this.pos.getX();
		this.oldY = this.pos.getY();

		switch (direction) {
			case L:
				this.pos.setX(this.pos.getX() - speedX);
				break;
			case U:
				this.pos.setY(this.pos.getY() - speedY);
				break;
			case R:
				this.pos.setX(this.pos.getX() + speedX);
				break;
			case D:
				this.pos.setY(this.pos.getY() + speedY);
				break;
			case STOP:
				break;
		}

		if (this.direction != Direction.STOP) {
			this.myDirection = this.direction;
		}

		tankHandler.handleBoundaries();

		if (!good) {
			Direction[] directions = Direction.values();
			if (step == 0) {
				step = r.nextInt(12) + 3;
				int mod = r.nextInt(9);
				if (playerTankAround()) {
					tankHandler.handlePlayerTankAround(directions);
				} else {
					tankHandler.handleNoPlayerTankAround(mod, directions);
				}
			}
			step--;
			tankHandler.handleRate();
		}
	}

	public boolean playerTankAround(){
		int rx=this.pos.getX()-15;
		int ry=this.pos.getY()-15;
		if((this.pos.getX()-15)<0) rx=0;
		if((this.pos.getY()-15)<0) ry=0;
		Rectangle a=new Rectangle(rx, ry,60,60);
        return this.live && a.intersects(tc.homeTank.getRect());
    }
	private void changToOldDir() {
		this.pos.setX(oldX);
		this.pos.setY(oldY);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (commandMap.containsKey(key)) {
			commandMap.get(key).execute();
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

	public void restartGame() {
		tc.tanks.clear();
		tc.bullets.clear();
		tc.trees.clear();
		tc.otherWall.clear();
		tc.homeWall.clear();
		tc.metalWall.clear();
		tc.homeTank.setLive(false);

		if (tc.tanks.isEmpty()) {
			initializeEnemyTanks();
		}

		tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc, 0);

		if (!tc.home.isLive())
			tc.home.setLive(true);

		TankClient abc = new TankClient();
		if (tc.player2) abc.player2 = true;
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
		if (commandMap.containsKey(key)) {
			commandMap.get(key).stopExecution();
		}
		decideDirection();
	}

	public Bullets fire() {
		if (!live)
			return null;
		int x = this.pos.getX() + Tank.TANK_WIDTH / 2 - Bullets.width / 2;
		int y = this.pos.getY()  + Tank.TANK_LENGTH / 2 - Bullets.length / 2;
		Bullets m = new Bullets(x, y + 2, good, myDirection, this.tc);
		tc.bullets.add(m);
		return m;
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
	public boolean collideWithObject(GameObject gameObject) {
		return collisionHandler.handleCollisionsWithObject(this, gameObject);
	}

	public boolean collideWithWall(CommonWall wall) {
		return collisionHandler.handleCollisionsWithWall(this, wall);
	}

	public boolean collideWithWall(MetalWall wall) {
		return collisionHandler.handleCollisionsWithWall(this, wall);
	}

	public boolean collideHome(Home home) {
		return collisionHandler.handleCollisionsWithHome(this, home);
	}

	public boolean collideWithTanks(List<Tank> tanks) {
		return collisionHandler.handleCollisionsWithTanks(this, tanks);
	}

	public void handleCollision() {
		changToOldDir();
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
	public TankClient getTc() {
		return tc;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public static int getTankWidth() {
		return TANK_WIDTH;
	}

	public static int getTankLength() {
		return TANK_LENGTH;
	}
	public int getRate() {
		return rate;
	}

}