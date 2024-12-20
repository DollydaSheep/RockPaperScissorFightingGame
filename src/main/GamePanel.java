package main;

import entity.Player1;
import entity.Player2;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    int FPS = 60;

    KeyHandler keyh = new KeyHandler();
    Thread gameThread;
    Player1 player1 = new Player1(this,keyh);
    Player2 player2 = new Player2(this,keyh);

    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh);
        this.setFocusable(true);
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){



            update();

            repaint();



            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String winMessage = "";
    private boolean isPaused;
    private long pauseTime;

    public void update(){
        if(isPaused){
            if(System.currentTimeMillis() >= pauseTime){
                isPaused = false;
                player1.x = 100;
                player1.y = 400;
                player1.jump = false;
                player2.x = 600;
                player2.y = 400;
                player2.jump = false;
                keyh.upPressed1 = false;
                keyh.upPressed2 = false;
                player1.isDash = false;
                player2.isDash = false;
                player1.dashCooldown = 120;
                player2.dashCooldown = 120;
                player1.dashAvailable = true;
                player2.dashAvailable = true;

            }
            return;
        }

        player1.update();
        player2.update();

        player1.solidArea.setLocation(player1.x, player1.y);
        player2.solidArea.setLocation(player2.x, player2.y);

        if(player1.solidArea.intersects((player2.solidArea))){
            winMessage = "Player 1 Wins!";
            isPaused = true;
            pauseTime = System.currentTimeMillis() + 1000;
            /*try{
                Thread.sleep(1000);
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            System.out.println("collision detected");
        }

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        if(!winMessage.isEmpty()){

            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.BOLD,40));
            int textWidth = g.getFontMetrics().stringWidth(winMessage);
            int textX = (screenWidth - textWidth) / 2;
            int textY = 150;
            g.drawString(winMessage,textX,textY);
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    winMessage = ""; // Clear the message
                }
            }, 1000);
        }

        player1.draw(g2);
        player2.draw(g2);

        g2.dispose();
    }
}
