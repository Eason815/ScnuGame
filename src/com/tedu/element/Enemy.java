package com.tedu.element;

import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
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


        int x = new Random().nextInt(800);
        int y = new Random().nextInt(600);
        this.setX(x);
        this.setY(y);

        ImageIcon icon2 = GameLoad.imgMap.get("right1");
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);



        return this;

    }

    @Override
    public void updateImg(long GameTime){

        int Num =new Random().nextInt(10,1000);


        if(GameTime%Num==0){
            if(Objects.equals(this.fx, "right"))
                this.setFx("left");
            else
                this.setFx("right");
        }

        this.setIcon(GameLoad.imgMap.get(this.fx+"1"));
    }

    @Override
    public void move(long GameTime){

        int x=this.getX();
        int y=this.getY();
        if(x<0||y<0||x>800||y>600){
            switch (this.fx){
                case "right": this.setX(x-10);this.fx="left";break;
                case "left": this.setX(x+10); this.fx="right";break;
            }
            return;
        }

        switch (this.fx) {
            case "right": this.setX(this.getX() + this.speed);break;
            case "left": this.setX(this.getX() - this.speed);break;
        }

    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }
}
