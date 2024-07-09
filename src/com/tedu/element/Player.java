package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Player extends ElementObj {

    private boolean left = false;
    private boolean up = false;
    private boolean right = false;
    private boolean down = false;

    private String fx="up";
    private boolean atk = false;

    private int speed=1;

    private int hp = 5;


    public Player(){}
    public Player(int x, int y, int w, int h, ImageIcon icon) {
        super(x,y,w,h,icon);

    }


    @Override
    public ElementObj createElement(String str){
        //{x,y,icon,hp}

        String [] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
        this.hp = split.length>3?Integer.parseInt(split[3]):5;
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        return this;
    }

    @Override
    public void showElement(Graphics g){

        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);

        // 血量
        g.setColor(Color.RED);
//            g.drawRect(this.getX(),this.getY()-10,this.getW(),10);
//            g.fillRect(this.getX(),this.getY()-10,this.getW()*hp/5,10);
        g.drawString("hp:"+hp,this.getX()+5,this.getY()-10);
    }

    @Override
    public void keyClick(boolean b1, int key) {
//        System.out.println("keyClick000"+key);

        if(b1){
            this.speed=1;
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
        int newX = this.getX();
        int newY = this.getY();

        if (this.left && this.getX() > 0) {
            newX -= this.speed;
        }
        if (this.up && this.getY() > 0) {
            newY -= this.speed;
        }
        if (this.right && this.getX() < 900 - this.getW()) {
            newX += this.speed;
        }
        if (this.down && this.getY() < 570 - this.getH()) {
            newY += this.speed;
        }

        // 碰撞检测
        if (!checkCollision(newX, newY)) {
            this.setX(newX);
            this.setY(newY);
        }
    }

    // 碰撞检测方法
    private boolean checkCollision(int newX, int newY) {
        // 获取所有墙体元素
        ElementManager em=ElementManager.getManager();
        Map<GameElement, List<ElementObj>> all = em.getGameElements();
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

    @Override
    public void stopMovement() {
        this.speed=0;
    }

    @Override
    public void setLive(boolean live){

            this.hp--;
            if(this.hp>0){
                return;
            }

        super.setLive(live);
    }
}
