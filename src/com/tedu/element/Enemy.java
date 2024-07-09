package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Enemy extends ElementObj {

    private int speed=1;
    private String fx="right";

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

    @Override
    public ElementObj createElement(String str) {
        //str:
        // 初始方向,移动方式(1左右，2上下),

        String [] s1= str.split(",");
        ImageIcon icon2 = GameLoad.imgMap.get(this.fx+"1");
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);


        int x = new Random().nextInt(700);
        int y = new Random().nextInt(500);
        // 碰撞检测
        while (checkCollision(x, y)) {
            x = new Random().nextInt(700);
            y = new Random().nextInt(500);
        }

        this.setX(x);
        this.setY(y);
        this.fx = s1[0];





        return this;

    }

    @Override
    public void updateImg(long GameTime){

        int Num =new Random().nextInt(10,1000);


        if(GameTime%Num==0){
            if(Objects.equals(this.fx, "right"))
                this.setFx("left");
            else if (Objects.equals(this.fx, "left"))
                this.setFx("right");
            else if (Objects.equals(this.fx, "up"))
                this.setFx("down");
            else if (Objects.equals(this.fx, "down"))
                this.setFx("up");
        }

        this.setIcon(GameLoad.imgMap.get(this.fx+"1"));
    }

    @Override
    public void move(long GameTime){

        int x=this.getX();
        int y=this.getY();
        if(x<0||y<0||x>800||y>500){
            switch (this.fx){
                case "right": this.setX(x-11);this.fx="left";break;
                case "left": this.setX(x+11); this.fx="right";break;
                case "up": this.setY(y+11); this.fx="down";break;
                case "down": this.setY(y-11); this.fx="up";break;
            }
            return;
        }

        switch (this.fx) {
            case "right": x += this.speed;break;
            case "left": x -= this.speed;break;
            case "up": y -= this.speed;break;
            case "down": y += this.speed;break;
        }

        // 碰撞检测
        if (!checkCollision(x, y)) {
            this.setX(x);
            this.setY(y);
        }

    }

    // 碰撞检测方法
    private boolean checkCollision(int newX, int newY) {
        // 获取所有墙体元素
        ElementManager em=ElementManager.getManager();
        Map<GameElement, java.util.List<ElementObj>> all = em.getGameElements();
        List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
        for (ElementObj map : maps) {
            if (newX < map.getX() + map.getIcon().getIconWidth() &&
                    newX + this.getW() > map.getX() &&
                    newY < map.getY() + map.getIcon().getIconHeight() &&
                    newY + this.getH() > map.getY()) {
                return true; // 发生碰撞
            }
        }
        return false; // 没有发生碰撞
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }
}
