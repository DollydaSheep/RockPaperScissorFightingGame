package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;

public class Player1 extends Entity{

    GamePanel gp;
    KeyHandler keyh;

    public Player1(GamePanel gp, KeyHandler keyh){

        this.gp = gp;
        this.keyh = keyh;

        solidArea = new Rectangle(0,0,48,48);

        setDefaultValues();
    }
    public void setDefaultValues(){
        x = 100;
        y = 400;
        speed = 4;
    }




    public void update(){
        if(keyh.upPressed1 == true && jump == false){
            jump = true;
            velocityY = jumpStrength;
        }else if(keyh.downPressed1 == true && isDash == false && dashAvailable == true && jump == false){
            currentX = x;
            isDash = true;
            dashAvailable = false;
        }else if(keyh.leftPressed1 == true && isDash == false){
            x -= speed;
            faceRight = false;
        }else if(keyh.rightPressed1 == true && isDash == false){
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
                keyh.downPressed1 = false;
            }
            if(faceRight){
                speed = dash;
                x += speed;
            }
            else{
                speed = dash;
                x -= dash;
            }
        }else{
            speed = 4;
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
            if(keyh.rightPressed1 == true){
                x += speed;
            }else if(keyh.leftPressed1 == true){
                x -= speed;
            }
            y += velocityY;
            velocityY += gravity;
            if(y == 400 && jump == true){
                keyh.upPressed1 = false;
                jump = false;
                velocityY = 0;
            }
        }
        if(y > 400){
            y = 400;
            keyh.upPressed1 = false;
            jump = false;
            velocityY = 0;
        }

    }
    public void draw(Graphics2D g2) {
        if(keyh.rock1 == true){
            g2.setColor(Color.gray);
        }
        if(keyh.paper1 == true){
            g2.setColor(Color.white);
        }
        if(keyh.scissor1 == true){
            g2.setColor(Color.red);
        }
        g2.fillRect(x,y,gp.tileSize,gp.tileSize);
    }
}
