package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj{

    private int hp;

    private String name;
    private final int speed=0;


    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);

    }

    public MapObj(){

    }

    public ElementObj createElement(String str){
        String [] arr = str.split(",");
        ImageIcon icon = null;
        switch(arr[0]){
            case "GRASS":icon = new ImageIcon("image/wall/grass.png"); break;
            case "BRICK":icon = new ImageIcon("image/wall/brick.png"); break;
            case "RIVER":icon = new ImageIcon("image/wall/river.png"); break;
            case "IRON":icon = new ImageIcon("image/wall/iron.png");
                    this.hp=4;
                    name = "IRON";
                    break;
        }
        int x=Integer.parseInt(arr[1]);
        int y=Integer.parseInt(arr[2]);
        int w=icon.getIconWidth();
        int h=icon.getIconHeight();
        this.setH(h);
        this.setW(w);
        this.setX(x);
        this.setY(y);
        this.setIcon(icon);
        return this;
    }

    @Override
    public void setLive(boolean live){
        if("IRON".equals(name)){
            this.hp--;
            if(this.hp>0){
                return;
            }
        }
        super.setLive(live);
    }

    @Override
    public int getSpeed() {
        return speed;
    }
}
