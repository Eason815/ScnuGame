package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class Play extends ElementObj {

    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    private String fx="up";
    private boolean atk = false;

    private int speed = 1;


    public Play(){}
    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x,y,w,h,icon);


    }


    @Override
    public ElementObj createElement(String str){
        String [] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        return this;
    }

    @Override
    public void showElement(Graphics g){

        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

    @Override
    public void keyClick(boolean b1, int key) {
//        System.out.println("keyClick000"+key);

        if(b1){
            switch(key){
                case 37:
                    this.down=false; this.up=false;
                    this.right=false; this.left=true; fx="left"; break;
                case 38:
                    this.left=false; this.right=false;
                    this.down=false; this.up=true; fx="up"; break;
                case 39:
                    this.up=false; this.down=false;
                    this.left=false; this.right=true; fx="right"; break;
                case 40:
                    this.left=false; this.right=false;
                    this.up=false; this.down=true; fx="down"; break;
                case 32:
                    this.atk=true; break;
            }
        }
        else{
            switch(key){
                case 37: this.left=false; break;
                case 38: this.up=false; break;
                case 39: this.right=false; break;
                case 40: this.down=false; break;
                case 32: this.atk=false; break;
            }
        }



    }

    @Override
    public void move(long gameTime) {
        if(this.left && this.getX()>0){
            this.setX(this.getX()-this.speed);
        }
        if(this.up && this.getY()>0){
            this.setY(this.getY()-this.speed);
        }
        if(this.right && this.getX()<900-this.getW()){
            this.setX(this.getX()+this.speed);
        }
        if(this.down &&this.getY()<570-this.getH()){
            this.setY(this.getY()+this.speed);
        }


    }

    @Override
    public void updateImg(long GameTime){
        this.setIcon(GameLoad.imgMap.get(fx));
    }

    @Override
    public void add(long GameTime) {
        if (!this.atk) {
            return;
        }
        this.atk=false;
        ElementObj obj = new Bullet().createElement(this.toString());
        ElementManager.getManager().addElement(obj,GameElement.BULLET);



    }


    @Override
    public String toString() {//类似使用JSON传递数据
        int x=this.getX();
        int y=this.getY();
        switch (this.fx){
            case "left":
                y+=15;break;
            case "up":
                x+=15;break;
            case "right":
                x+=50;y+=15;break;
            case "down":
                x+=15;y+=50;break;
        }

        return "x:"+x+",y:"+y+",fx:"+this.fx;
    }


}
