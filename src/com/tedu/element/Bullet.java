package com.tedu.element;

import com.tedu.controller.GameThread;
import com.tedu.manager.GameLoad;

import java.awt.*;

public class Bullet extends ElementObj {

    private int attack = 1;
    private boolean isFromPlayer;
    // private int speed=3;

    public String fx;

    public Bullet() {
    }

    @Override
    public ElementObj createElement(String str) {
        // {x:3,y:4,fx:up,speed:5}
        String[] s = str.split(",");
        for (String s1 : s) {
            String[] e1 = s1.split(":");
            switch (e1[0]) {
                case "x":
                    this.setX(Integer.parseInt(e1[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(e1[1]));
                    break;
                case "fx":
                    this.fx = e1[1];
                    break;
                case "speed":
                    this.speed = Integer.parseInt(e1[1]);
                    break;
                case "isFromPlayer":
                    this.isFromPlayer = Boolean.parseBoolean(e1[1]);
                    break;
            }

        }
        this.setW(10);
        this.setH(10);

        this.setIcon(GameLoad.imgMap.get("bullet"));

        return this;

    }

    @Override
    public void showElement(Graphics g) {
//        g.setColor(Color.RED);
//        g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getIcon().getIconWidth(), this.getIcon().getIconHeight(), null);

    }

    @Override
    protected void move(long gameTime) {

        this.speed = 3;

        if(GameThread.GameProcess==GameThread.FlyLevel)
            this.speed = 5;
        int x = this.getX();
        int y = this.getY();
        if (x < 0 || y < 0 || x > 800 || y > 600) {
            this.setLive(false);
            return;
        }

        switch (this.fx) {
            case "up":
                this.setY(this.getY() - this.speed);
                break;
            case "down":
                this.setY(this.getY() + this.speed);
                break;
            case "left":
                this.setX(this.getX() - this.speed);
                break;
            case "right":
                this.setX(this.getX() + this.speed);
                break;
        }
    }

    public boolean isFromPlayer() {
        return isFromPlayer;
    }

}
