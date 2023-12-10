import java.awt.*;
import java.util.List;

public class GameInitializer {

    private TankClient tankClient;
    private List<Tank> tanks;
    private List<Tree> trees;
    private List<CommonWall> homeWall;
    private List<CommonWall> otherWall;
    private List<MetalWall> metalWall;

    public GameInitializer(TankClient tankClient) {
        this.tankClient = tankClient;
        this.tanks = tankClient.getTanks();
        this.trees = tankClient.getTrees();
        this.homeWall = tankClient.getHomeWall();
        this.otherWall = tankClient.getOtherWall();
        this.metalWall = tankClient.getMetalWall();
    }


    public void initializeGame() {
        initializeHomeWalls();
        initializeOtherWalls();
        initializeMetalWalls();
        initializeTrees();
        initializeRiver();
        initializeTanks();
        initializeFrame();
    }

    private void initializeFrame() {
        tankClient.setSize(TankClient.FRAME_WIDTH, TankClient.FRAME_LENGTH);
        tankClient.setLocation(280, 50);
        tankClient.setTitle("Battle City    Final Project for CPE 640");

        tankClient.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
        tankClient.setResizable(false);
        tankClient.setBackground(Color.GREEN);
        tankClient.setVisible(true);

        new Thread(new PaintThread()).start();
    }

    private void initializeTanks() {
        List<Tank> tanks = tankClient.getTanks();
        for (int i = 0; i < 20; i++) {
            int tankX1 = 150 + 70 * i;
            int tankX2 = 700;
            int tankY1 = 40;
            int tankY2 = 140 + 50 * (i - 6);
            int tankY3 = 50 * (i - 12);

            if (i < 9)
                tanks.add(new Tank(tankX1, tankY1, false, Direction.D, tankClient, 0));
            else if (i < 15)
                tanks.add(new Tank(tankX2, tankY2, false, Direction.D, tankClient, 0));
            else
                tanks.add(new Tank(10, tankY3, false, Direction.D, tankClient, 0));
        }
    }

    private void initializeRiver() {
        List<River> theRiver = tankClient.getTheRiver();
        theRiver.add(new River(85, 100));
    }

    private void initializeTrees() {
        List<Tree> trees = tankClient.getTrees();
        for (int i = 0; i < 4; i++) {
            int treeX = 30 * i;
            int treeY = 360;

            trees.add(new Tree(treeX, treeY));
            trees.add(new Tree(220 + treeX, treeY));
            trees.add(new Tree(440 + treeX, treeY));
            trees.add(new Tree(660 + treeX, treeY));
        }
    }

    private void initializeMetalWalls() {
        List<MetalWall> metalWall = tankClient.getMetalWall();
        for (int i = 0; i < 20; i++) {
            int metalWallX1 = 140 + 30 * i;
            int metalWallX2 = 600;
            int metalWallY1 = 150;
            int metalWallY2 = 400 + 20 * i;
            int metalWallY3 = 180;

            if (i < 10) {
                metalWall.add(new MetalWall(metalWallX1, metalWallY1));
                metalWall.add(new MetalWall(metalWallX2, metalWallY2));
            } else if (i < 20)
                metalWall.add(new MetalWall(metalWallX1, metalWallY3));
        }
    }

    private void initializeOtherWalls() {
        List<CommonWall> otherWall = tankClient.getOtherWall();
        for (int i = 0; i < 32; i++) {
            int otherWallX1 = 200 + 21 * i;
            int otherWallX2 = 500 + 21 * i;
            int otherWallY1 = 300;
            int otherWallY2 = 180;
            int otherWallY3 = 400;

            if (i < 16) {
                otherWall.add(new CommonWall(otherWallX1, otherWallY1));
                otherWall.add(new CommonWall(otherWallX2, otherWallY2));
                otherWall.add(new CommonWall(200, otherWallY3 + 21 * i));
                otherWall.add(new CommonWall(500, otherWallY3 + 21 * i));
            } else if (i < 32) {
                otherWall.add(new CommonWall(otherWallX1, otherWallY1 + 20));
                otherWall.add(new CommonWall(otherWallX2, otherWallY2 + 20));
                otherWall.add(new CommonWall(222, otherWallY3 + 21 * (i - 16)));
                otherWall.add(new CommonWall(522, otherWallY3 + 21 * (i - 16)));
            }
        }
    }

    public void initializeHomeWalls() {
        List<CommonWall> homeWall = tankClient.getHomeWall();
        int homeWallX = 350;
        int homeWallY1 = 580;
        int homeWallY2 = 517;
        int homeWallY3 = 538;

        for (int i = 0; i < 10; i++) {
            if (i < 4)
                homeWall.add(new CommonWall(homeWallX, homeWallY1 - 21 * i));
            else if (i < 7)
                homeWall.add(new CommonWall(372 + 22 * (i - 4), homeWallY2));
            else
                homeWall.add(new CommonWall(416, homeWallY3 + (i - 7) * 21));
        }
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (tankClient.isPrintable()) {
                tankClient.repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
