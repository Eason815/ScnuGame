package com.tedu.element;

import com.tedu.controller.GameThread;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.SoundPlayer;


import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class Enemy extends ElementObj {

    // private int speed = 1;
    private ElementManager em = ElementManager.getManager();
    private String fx = "left";
    private boolean atk = false;
    private int moveMode = 0;
    public int trend = 0;
    public boolean inDanger = false;
    public long LockTime = 0L;

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        // str:
        // 初始方向,移动方式(1左右，2上下,3向左),

        String[] s1 = str.split(",");
        ImageIcon icon2 = GameLoad.imgMap.get(this.fx + "1");
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        this.moveMode = Integer.parseInt(s1[1]);

        int x = new Random().nextInt(700);
        int y = new Random().nextInt(500);
        if (this.moveMode == 3){
            x = 788;
            y = new Random().nextInt(520) + 40;
            this.trend = y%2;
        }
        else {
            // 碰撞检测
            while (checkCollision(x, y, 1)) {
                x = new Random().nextInt(700);
                y = new Random().nextInt(500);
            }
        }

        this.setX(x);
        this.setY(y);
        this.fx = s1[0];

        return this;

    }

    @Override
    public void updateImg(long GameTime) {

        if(GameThread.GameProcess==GameThread.FlyLevel)
            return;

        if(LockTime>GameTime)
            return;
        else
            LockTime = 0L;

        int moveDirection = 0;
        inDanger = false;
        for (ElementObj obj : em.getElementsByKey(GameElement.BULLET)) {
            if (obj instanceof Bullet bullet) {
                if (isBulletThreat(bullet)) {
                    inDanger = true;
                    LockTime = GameTime + 100;// 紧急争用期
                    moveDirection = getDodgeDirection(bullet);
                    break;
                }
            }
        }

        if(inDanger){
            moveInDirection(moveDirection);
        }
        else {
            int min = 10;
            int max = 100;

            int Num = new Random().nextInt(max - min + 1) + min;

            if (GameTime % Num == 0) {
                if (Objects.equals(this.fx, "right"))
                    this.setFx("left");
                else if (Objects.equals(this.fx, "left"))
                    this.setFx("right");
                else if (Objects.equals(this.fx, "up"))
                    this.setFx("down");
                else if (Objects.equals(this.fx, "down"))
                    this.setFx("up");
            }
        }

        this.setIcon(GameLoad.imgMap.get(this.fx + "1"));
    }

    private void moveInDirection(int direction) {
        // 检查垂直方向是否有障碍物并移动
        switch (direction) {
            case 0 -> { // 向左
                this.fx = "left"; break;
            }
            case 1 -> { // 向右
                this.fx = "right"; break;
            }
            case 2 -> { // 向上
                this.fx ="up"; break;
            }
            case 3 -> { // 向下
                this.fx="down"; break;
            }
        }
    }

    private int getDodgeDirection(Bullet bullet) {
        // 根据子弹方向决定躲避方向
        if (Objects.equals(bullet.fx, "left") ||Objects.equals(bullet.fx,"right")) { // 子弹水平移动
            return new Random().nextBoolean() ? 2 : 3; // 垂直方向移动（2：向上，3：向下）
        } else { // 子弹垂直移动
            return new Random().nextBoolean() ? 0 : 1; // 水平方向移动（0：向左，1：向右）
        }
    }


    @Override
    public void move(long GameTime) {

        int x = this.getX();
        int y = this.getY();

        if(this.moveMode!=3) {
            this.speed = 2;

            switch (this.fx) {
                case "right":
                    x += this.speed;
                    break;
                case "left":
                    x -= this.speed;
                    break;
                case "up":
                    y -= this.speed;
                    break;
                case "down":
                    y += this.speed;
                    break;
            }

            // 碰撞检测
            if (!checkCollision(x, y, 0) && !(x < 0 || y < 0 || x > 800 || y > 500)) {
                this.setX(x);
                this.setY(y);
            }
        }

        else {
            // 敌军飞机向左移动
            setX(getX() - 2);

            if(new Random().nextInt(100)<25) {
                if (this.trend == 1) {
                    setY(getY() + 2);
                } else
                    setY(getY() - 2);
            }
            // 边界检测，如果敌军飞机离开窗体，则设置为不可见
            if (x < 0 || x > 800 ) {
                setLive(false);
            } else if (y < 0 || y > 500) {
                if(this.trend==1)
                    this.trend=0;
                else
                    this.trend=1;
            }

        }



    }



    @Override
    public void add(long GameTime) {

        int num =4;

        if(inDanger){
            num = 16;
        }

        // 每次移动概率发射子弹
        int p = new Random().nextInt(1000);
        if (p < num) {
            this.atk = true;
        }

        if (!this.atk) {
            return;
        }
        this.atk = false;
        ElementObj obj = new Bullet().createElement(this.toString());
        ElementManager.getManager().addElement(obj, GameElement.BULLET);

    }

    @Override
    public String toString() {// 类似使用JSON传递数据
        int x = this.getX();
        int y = this.getY();
        switch (this.fx) {
            case "left":
                y += 12;
                x -= 10;
                break;
            case "up":
                x += 12;
                y -= 10;
                break;
            case "right":
                x += 35;
                y += 12;
                break;
            case "down":
                x += 12;
                y += 35;
                break;
        }

        return "x:" + x + ",y:" + y + ",fx:" + this.fx + ",isFromPlayer:false";
    }

    @Override
    public boolean checkCash1(int newX,int newY,ElementObj map){
        return newX < map.getX() + map.getIcon().getIconWidth() &&//从右边   左边界越过对方右边界
                newX + this.getW() > map.getX() &&                //从左边
                newY < map.getY() + map.getIcon().getIconHeight() &&//从上边
                newY + this.getH() > map.getY();                    //从下边
    }

    @Override
    public void die(){
        int score = GameThread.Score;
        if(GameThread.GameProcess<4) {
            if (score == 0)
                return;
            else if(score < 5) {
                String path = "sound/"+score+".wav";
                new SoundPlayer(path).play();
            }
            else
                new SoundPlayer("sound/5.wav").play();
        }
    }

    public boolean checkCash2(int newX,int newY,ElementObj map){
        return newX < map.getX() + map.getW() &&
                newX + this.getW() > map.getX() &&
                newY < map.getY() + map.getH() &&
                newY + this.getH() > map.getY();
    }

    // 碰撞检测方法
    private boolean checkCollision(int newX, int newY,int mode) {
        // 获取所有墙体元素
        ElementManager em = ElementManager.getManager();
        Map<GameElement, java.util.List<ElementObj>> all = em.getGameElements();
        List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);

        for (ElementObj map : maps) {
            if (checkCash1(newX,newY,map)) {
                return true; // 发生碰撞
            }
        }

        if (mode==1) {
            List<ElementObj> masks = em.getElementsByKey(GameElement.MASK);
            if (!masks.isEmpty()) {
                for (ElementObj mask : masks) {
                    if (checkCash2(newX, newY, mask)) {
                        return true;
                    }
                }
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

    private boolean isBulletThreat(Bullet bullet) {
        int dx = bullet.getX() - this.getX();
        int dy = bullet.getY() - this.getY();
        return Math.sqrt(dx * dx + dy * dy) < 100; // 检测子弹威胁半径
    }


}
