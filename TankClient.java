import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

public class TankClient extends Frame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_LENGTH = 600;
    private boolean printable = true;
    private MenuBar menu = null;
    private Menu gameMenu = null;
    private Menu pauseContinueMenu = null;
    private Menu helpMenu = null;
    private Menu levelMenu = null;
    private Menu AdditionMenu = null;
    private MenuItem newGameMenuItem = null;
    private MenuItem exitMenuItem = null;
    private MenuItem stopMenuItem = null;
    private MenuItem continueMenuItem = null;
    private MenuItem helpMenuItem = null;
    private MenuItem level1MenuItem = null;
    private MenuItem level2MenuItem = null;
    private MenuItem level3MenuItem = null;
    private MenuItem level4MenuItem = null;
    private MenuItem addPlayer2MenuItem = null;
    private MenuItem joinOthersGameMenuItem = null;
    private Image screenImage = null;

    Tank homeTank = new Tank(300, 560, true, Direction.STOP, this, 1);
    Tank homeTank2 = new Tank(449, 560, true, Direction.STOP, this, 2);
    Boolean player2 = false;
    GetBlood blood = new GetBlood();
    Home home = new Home(373, 557, this);
    Boolean win = false;
    Boolean lose = false;
    List<River> theRiver = new ArrayList<River>();
    List<Tank> tanks = new ArrayList<Tank>();
    List<BombTank> bombTanks = new ArrayList<BombTank>();
    List<Bullets> bullets = new ArrayList<Bullets>();
    List<Tree> trees = new ArrayList<Tree>();
    List<CommonWall> homeWall = new ArrayList<CommonWall>();
    List<CommonWall> otherWall = new ArrayList<CommonWall>();
    List<MetalWall> metalWall = new ArrayList<MetalWall>();
    private static final String TIMES_NEW_ROMAN_TEXT = "Times New Roman";

    public void update(Graphics graphics) {

        screenImage = this.createImage(FRAME_WIDTH, FRAME_LENGTH);

        Graphics gps = screenImage.getGraphics();
        Color color = gps.getColor();
        gps.setColor(Color.GRAY);
        gps.fillRect(0, 0, FRAME_WIDTH, FRAME_LENGTH);
        gps.setColor(color);
        framPaint(gps);
        graphics.drawImage(screenImage, 0, 0, null);
    }

    public void framPaint(Graphics graphics) {

        Color c = graphics.getColor();
        graphics.setColor(Color.green);

        Font f1 = graphics.getFont();
        graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 20));
        graphics.drawString("Tanks left in the field: ", player2 ? 100 : 200, 70);
        graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.ITALIC, 30));
        graphics.drawString("" + tanks.size(), player2 ? 300 : 400, 70);
        graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 20));
        graphics.drawString("Health: ", player2 ? 380 : 580, 70);
        graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.ITALIC, 30));
        if (!player2) graphics.drawString("" + homeTank.getLife(), 650, 70);
        else graphics.drawString("Player1: " + homeTank.getLife() + "    Player2:" + homeTank2.getLife(), 450, 70);
        graphics.setFont(f1);
        if (!player2) {
            if (tanks.isEmpty() && home.isLive() && homeTank.isLive() && !lose) {
                Font f = graphics.getFont();
                graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 60));
                this.otherWall.clear();
                graphics.drawString("Congratulations! ", 200, 300);
                graphics.setFont(f);
                win = true;
            }

            if (!homeTank.isLive() && !win) {
                Font f = graphics.getFont();
                graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 40));
                tanks.clear();
                bullets.clear();
                graphics.drawString("Sorry. You lose!", 200, 300);
                lose = true;
                graphics.setFont(f);
            }
        } else {
            if (tanks.isEmpty() && home.isLive() && (homeTank.isLive() || homeTank2.isLive()) && !lose) {
                Font f = graphics.getFont();
                graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 60));
                this.otherWall.clear();
                graphics.drawString("Congratulations! ", 200, 300);
                graphics.setFont(f);
                win = true;
            }

            if (!homeTank.isLive() && !homeTank2.isLive() && !win) {
                Font f = graphics.getFont();
                graphics.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 40));
                tanks.clear();
                bullets.clear();
                graphics.drawString("Sorry. You lose!", 200, 300);
                graphics.setFont(f);
                lose = true;
            }
        }
        graphics.setColor(c);

        for (River r : theRiver) {
            r.draw(graphics);
        }

        for (int i = 0; i < theRiver.size(); i++) {
            River r = theRiver.get(i);
            homeTank.collideWithObject(r);
            if (player2) homeTank2.collideWithObject(r);
            r.draw(graphics);
        }

        home.draw(graphics);
        homeTank.draw(graphics);
        homeTank.eat(blood);
        if (player2) {
            homeTank2.draw(graphics);
            homeTank2.eat(blood);
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullets m = bullets.get(i);
            m.hitTanks(tanks);
            m.hitTank(homeTank);
            m.hitTank(homeTank2);
            m.hitHome();
            for (int j = 0; j < bullets.size(); j++) {
                if (i == j) continue;
                Bullets bts = bullets.get(j);
                m.hitBullet(bts);
            }
            for (int j = 0; j < metalWall.size(); j++) {
                MetalWall mw = metalWall.get(j);
                m.hitWall(mw);
            }

            for (int j = 0; j < otherWall.size(); j++) {
                CommonWall w = otherWall.get(j);
                m.hitWall(w);
            }

            for (int j = 0; j < homeWall.size(); j++) {
                CommonWall cw = homeWall.get(j);
                m.hitWall(cw);
            }
            m.draw(graphics);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);

            for (int j = 0; j < homeWall.size(); j++) {
                CommonWall cw = homeWall.get(j);
                t.collideWithWall(cw);
                cw.draw(graphics);
            }
            for (int j = 0; j < otherWall.size(); j++) {
                CommonWall cw = otherWall.get(j);
                t.collideWithWall(cw);
                cw.draw(graphics);
            }
            for (int j = 0; j < metalWall.size(); j++) {
                MetalWall mw = metalWall.get(j);
                t.collideWithWall(mw);
                mw.draw(graphics);
            }
            for (int j = 0; j < theRiver.size(); j++) {
                River r = theRiver.get(j);
                t.collideWithObject(r);
                r.draw(graphics);
            }

            t.collideWithTanks(tanks);
            t.collideHome(home);

            t.draw(graphics);
        }

        for (int i = 0; i < trees.size(); i++) {
            Tree tr = trees.get(i);
            tr.draw(graphics);
        }

        for (int i = 0; i < bombTanks.size(); i++) {
            BombTank bt = bombTanks.get(i);
            bt.draw(graphics);
        }

        for (int i = 0; i < otherWall.size(); i++) {
            CommonWall cw = otherWall.get(i);
            cw.draw(graphics);
        }

        for (int i = 0; i < metalWall.size(); i++) {
            MetalWall mw = metalWall.get(i);
            mw.draw(graphics);
        }

        homeTank.collideWithTanks(tanks);
        homeTank.collideHome(home);
        if (player2) {
            homeTank2.collideWithTanks(tanks);
            homeTank2.collideHome(home);
        }

        for (int i = 0; i < metalWall.size(); i++) {
            MetalWall w = metalWall.get(i);
            homeTank.collideWithWall(w);
            if (player2) homeTank2.collideWithWall(w);
            w.draw(graphics);
        }

        for (int i = 0; i < otherWall.size(); i++) {
            CommonWall cw = otherWall.get(i);
            homeTank.collideWithWall(cw);
            if (player2) homeTank2.collideWithWall(cw);
            cw.draw(graphics);
        }

        for (int i = 0; i < homeWall.size(); i++) {
            CommonWall w = homeWall.get(i);
            homeTank.collideWithWall(w);
            if (player2) homeTank2.collideWithWall(w);
            w.draw(graphics);
        }

    }

    public TankClient() {

        initializeMenus();

        addMenuListener(newGameMenuItem, "NewGame");
        addMenuListener(exitMenuItem, "Exit");
        addMenuListener(stopMenuItem, "Stop");
        addMenuListener(continueMenuItem, "Continue");
        addMenuListener(helpMenuItem, "help");
        addMenuListener(level1MenuItem, "level1");
        addMenuListener(level2MenuItem, "level2");
        addMenuListener(level3MenuItem, "level3");
        addMenuListener(level4MenuItem, "level4");
        addMenuListener(addPlayer2MenuItem, "Player2");
        addMenuListener(joinOthersGameMenuItem, "Join");

        this.setMenuBar(menu);
        this.setVisible(true);

        initializeHomeWalls();
        initializeOtherWalls();
        initializeMetalWalls();
        initializeTrees();
        initializeRiver();
        initializeTanks();
        initializeFrame();
    }

    private void initializeFrame() {
        this.setSize(FRAME_WIDTH, FRAME_LENGTH);
        this.setLocation(280, 50);
        this.setTitle("Battle City    Final Project for CPE 640");

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.GREEN);
        this.setVisible(true);

        this.addKeyListener(new KeyMonitor());
        new Thread(new PaintThread()).start();
    }

    private void initializeTanks() {
        for (int i = 0; i < 20; i++) {
            int tankX1 = 150 + 70 * i;
            int tankX2 = 700;
            int tankY1 = 40;
            int tankY2 = 140 + 50 * (i - 6);
            int tankY3 = 50 * (i - 12);

            if (i < 9)
                tanks.add(new Tank(tankX1, tankY1, false, Direction.D, this, 0));
            else if (i < 15)
                tanks.add(new Tank(tankX2, tankY2, false, Direction.D, this, 0));
            else
                tanks.add(new Tank(10, tankY3, false, Direction.D, this, 0));
        }
    }

    private void initializeRiver() {
        theRiver.add(new River(85, 100));
    }

    private void initializeTrees() {
        for (int i = 0; i < 4; i++) {
            int treeX = 30 * i;
            int treeY = 360;

            trees.add(new Tree(treeX, treeY, this));
            trees.add(new Tree(220 + treeX, treeY, this));
            trees.add(new Tree(440 + treeX, treeY, this));
            trees.add(new Tree(660 + treeX, treeY, this));
        }
    }

    private void initializeMetalWalls() {
        for (int i = 0; i < 20; i++) {
            int metalWallX1 = 140 + 30 * i;
            int metalWallX2 = 600;
            int metalWallY1 = 150;
            int metalWallY2 = 400 + 20 * i;
            int metalWallY3 = 180;

            if (i < 10) {
                metalWall.add(new MetalWall(metalWallX1, metalWallY1, this));
                metalWall.add(new MetalWall(metalWallX2, metalWallY2, this));
            } else if (i < 20)
                metalWall.add(new MetalWall(metalWallX1, metalWallY3, this));
        }
    }

    private void initializeOtherWalls() {
        for (int i = 0; i < 32; i++) {
            int otherWallX1 = 200 + 21 * i;
            int otherWallX2 = 500 + 21 * i;
            int otherWallY1 = 300;
            int otherWallY2 = 180;
            int otherWallY3 = 400;

            if (i < 16) {
                otherWall.add(new CommonWall(otherWallX1, otherWallY1, this));
                otherWall.add(new CommonWall(otherWallX2, otherWallY2, this));
                otherWall.add(new CommonWall(200, otherWallY3 + 21 * i, this));
                otherWall.add(new CommonWall(500, otherWallY3 + 21 * i, this));
            } else if (i < 32) {
                otherWall.add(new CommonWall(otherWallX1, otherWallY1 + 20, this));
                otherWall.add(new CommonWall(otherWallX2, otherWallY2 + 20, this));
                otherWall.add(new CommonWall(222, otherWallY3 + 21 * (i - 16), this));
                otherWall.add(new CommonWall(522, otherWallY3 + 21 * (i - 16), this));
            }
        }
    }

    private void initializeHomeWalls() {
        int homeWallX = 350;
        int homeWallY1 = 580;
        int homeWallY2 = 517;
        int homeWallY3 = 538;

        for (int i = 0; i < 10; i++) {
            if (i < 4)
                homeWall.add(new CommonWall(homeWallX, homeWallY1 - 21 * i, this));
            else if (i < 7)
                homeWall.add(new CommonWall(372 + 22 * (i - 4), homeWallY2, this));
            else
                homeWall.add(new CommonWall(416, homeWallY3 + (i - 7) * 21, this));
        }
    }

    private void addMenuListener(MenuItem jmi1, String command) {
        jmi1.addActionListener(this);
        jmi1.setActionCommand(command);
    }

    private void addMenuItems() {
        newGameMenuItem = new MenuItem("New Game");
        exitMenuItem = new MenuItem("Exit");
        stopMenuItem = new MenuItem("Stop");
        continueMenuItem = new MenuItem("Continue");
        helpMenuItem = new MenuItem("Help");
        level1MenuItem = new MenuItem("Level1");
        level2MenuItem = new MenuItem("Level2");
        level3MenuItem = new MenuItem("Level3");
        level4MenuItem = new MenuItem("Level4");
        addPlayer2MenuItem = new MenuItem("Add Player 2");
        joinOthersGameMenuItem = new MenuItem("Join other's game");
    }

    private void initializeMenus() {
        menu = new MenuBar();
        gameMenu = new Menu("Game");
        pauseContinueMenu = new Menu("Pause/Continue");
        helpMenu = new Menu("Help");
        levelMenu = new Menu("Level");
        AdditionMenu = new Menu("Addition");
        setMenuFont(gameMenu, pauseContinueMenu, helpMenu, levelMenu, AdditionMenu);

        addMenuItems();

        gameMenu.add(newGameMenuItem);
        gameMenu.add(exitMenuItem);
        pauseContinueMenu.add(stopMenuItem);
        pauseContinueMenu.add(continueMenuItem);
        helpMenu.add(helpMenuItem);
        levelMenu.add(level1MenuItem);
        levelMenu.add(level2MenuItem);
        levelMenu.add(level3MenuItem);
        levelMenu.add(level4MenuItem);
        AdditionMenu.add(addPlayer2MenuItem);
        AdditionMenu.add(joinOthersGameMenuItem);

        menu.add(gameMenu);
        menu.add(pauseContinueMenu);

        menu.add(levelMenu);
        menu.add(AdditionMenu);
        menu.add(helpMenu);
    }

    private void setMenuFont(Menu... menus) {
        for (Menu menu : menus) {
            menu.setFont(new Font(TIMES_NEW_ROMAN_TEXT, Font.BOLD, 15));
        }
    }

    public static void main(String[] args) {
        new TankClient();
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (printable) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            homeTank.keyReleased(e);
            homeTank2.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            homeTank.keyPressed(e);
            homeTank2.keyPressed(e);
        }

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("NewGame")) {
            printable = false;
            Object[] options = {"Confirm", "Cancel"};
            int response = JOptionPane.showOptionDialog(this, "Confirm to start a new game?", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {

                printable = true;
                this.dispose();
                new TankClient();
            } else {
                printable = true;
                new Thread(new PaintThread()).start();
            }

        } else if (e.getActionCommand().endsWith("Stop")) {
            printable = false;
        } else if (e.getActionCommand().equals("Continue")) {

            if (!printable) {
                printable = true;
                new Thread(new PaintThread()).start();
            }
        } else if (e.getActionCommand().equals("Exit")) {
            printable = false;
            Object[] options = {"Confirm", "Cancel"};
            int response = JOptionPane.showOptionDialog(this, "Confirm to exit?", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {
                System.out.println("break down");
                System.exit(0);
            } else {
                printable = true;
                new Thread(new PaintThread()).start();

            }

		} else if(e.getActionCommand().equals("Player2")){
			printable = false;
			Object[] options = { "Confirm", "Cancel" };
			int response = JOptionPane.showOptionDialog(this, "Confirm to add player2?", "",
					JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				printable = true;
				this.dispose();
				TankClient Player2add=new TankClient();
				Player2add.player2=true;
			} else {
				printable = true;
				new Thread(new PaintThread()).start();
			}
		}
		else if (e.getActionCommand().equals("help")) {
			printable = false;
			JOptionPane.showMessageDialog(null, "Use WSAD to control Player1's direction, use F to fire and restart with pressing R\nUse diection key to Control Player2, use slash to fire",
					"Help", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			printable = true;
			new Thread(new PaintThread()).start();
		} else if (e.getActionCommand().equals("level1")) {
			Tank.setCount(12);
			Tank.setSpeedX(6);
			Tank.setSpeedY(6);
			Bullets.speedX = 10;
			Bullets.speedY = 10;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level2")) {
			Tank.setCount(12);
			Tank.setSpeedX(10);
			Tank.setSpeedY(10);
			Bullets.speedX = 12;
			Bullets.speedY = 12;
			this.dispose();
			new TankClient();

		} else if (e.getActionCommand().equals("level3")) {
			Tank.setCount(20);
			Tank.setSpeedX(14);
			Tank.setSpeedY(14);
			Bullets.speedX = 16;
			Bullets.speedY = 16;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("level4")) {
			Tank.setCount(20);
			Tank.setSpeedX(16);
			Tank.setSpeedY(16);
			Bullets.speedX = 18;
			Bullets.speedY = 18;
			this.dispose();
			new TankClient();
		} else if (e.getActionCommand().equals("Join")){
			printable = false;
			String s=JOptionPane.showInputDialog("Please input URL:");
			System.out.println(s);
			printable = true;
			new Thread(new PaintThread()).start();
		}

	}
}
