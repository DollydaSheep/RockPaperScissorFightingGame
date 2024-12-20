package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player2 extends Entity{

    GamePanel gp;
    KeyHandler keyh;

    public Player2(GamePanel gp, KeyHandler keyh){

        this.gp = gp;
        this.keyh = keyh;

        solidArea = new Rectangle(0,0,48,48);

        setDefaultValues();
    }
    public void setDefaultValues(){
        x = 600;
        y = 400;
        speed = 4;
    }


    public void update(){
        if(keyh.upPressed2 == true && jump == false){
            jump = true;
            velocityY = jumpStrength;
        }else if(keyh.downPressed2 == true && isDash == false && dashAvailable == true && jump == false){
            currentX = x;
            isDash = true;
            dashAvailable = false;
        }else if(keyh.leftPressed2 == true && isDash == false){
            x -= speed;
            faceRight = false;
        }else if(keyh.rightPressed2 == true && isDash == false){
            x += speed;
            faceRight = true;
        }

        if(dashAvailable == false){
            if(dashCooldown == 0) {
                dashCooldown = 120;
                dashAvailable = true;
                System.out.println("available");
            }
            dashCooldown--;
            System.out.println("cooldown on");
        }

        if(isDash){
            if(x==currentX+120 || x==currentX-120) {
                isDash = false;
                keyh.downPressed2 = false;
            }
            if(faceRight) x += dash;
            else x -= dash;
        }

        if(x < 0 ){
            isDash = false;
            x = 0;
        }
        if(x > 720){
            isDash = false;
            x = 720;
        }

        if(jump == true){
            if(keyh.rightPressed2 == true){
                x += speed;
            }else if(keyh.leftPressed2 == true){
                x -= speed;
            }
            y += velocityY;
            velocityY += gravity;
            if(y == 400 && jump == true){
                keyh.upPressed2 = false;
                jump = false;
                velocityY = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if(keyh.rock2 == true){
            g2.setColor(new Color(46, 46, 46));
        }
        if(keyh.paper2 == true){
            g2.setColor(new Color(0xB59D87));
        }
        if(keyh.scissor2 == true){
            g2.setColor(new Color(0x700000));
        }
        g2.fillRect(x,y,gp.tileSize,gp.tileSize);
    }
}
