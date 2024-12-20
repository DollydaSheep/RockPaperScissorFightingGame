package entity;

import java.awt.*;

public class Entity {
    public int x, y;
    public int speed;
    public String color;
    public Rectangle solidArea;

    public int velocityY = 0;
    int gravity = 1;
    int jumpStrength = -20;
    public boolean jump = false;
    boolean faceRight = true;
    public boolean isDash = false;
    int dash = 20;
    public boolean dashAvailable = true;
    public int dashCooldown = 120;
    int currentX;
}
