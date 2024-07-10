package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/**
 * &#064;说明  所有元素的基类
 * @author Eason
 */
public abstract class ElementObj {

    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;
    private boolean live=true;
    protected int speed = 0;
    protected String name="ElementObj";


    public ElementObj(){
    }

    public ElementObj(int x, int y,int w,int h, ImageIcon icon){
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;

    }

    public ElementObj createElement(String str){
        return null;
    }

    public abstract void showElement(Graphics g);
    public void keyClick(boolean b1,int key){
    }

    protected void move(long gameTime){
    }

    public void model(long GameTime){
        updateImg(GameTime);
        move(GameTime);
        add(GameTime);
    }

    protected void updateImg(long GameTime) {
    }

    protected void add(long GameTime){
    }

    public void die(){

    }


    public Rectangle getRect(){
        return new Rectangle(this.getX(),this.getY(),this.getW(),this.getH());
    }


    public boolean isCash(ElementObj obj){
        return this.getRect().intersects(obj.getRect());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void stopMovement() {
        this.speed=0;
    }

//    public void updatePosition() {
//        this.x += this.speed;
//        this.y += this.speed;
//    }
}
