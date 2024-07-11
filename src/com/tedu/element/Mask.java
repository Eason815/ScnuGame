package com.tedu.element;

import java.awt.*;

public class Mask extends ElementObj {

//    private int visionX, visionY, visionWidth, visionHeight;
    public static int wid=300;
    public Mask(){}


    @Override
    public ElementObj createElement(String str) {

        String[] split = str.split(",");

        this.setX(Integer.parseInt(split[0])-wid/2);
        this.setY(Integer.parseInt(split[1])-wid/2);
        this.setW(Integer.parseInt(split[2]));
        this.setH(Integer.parseInt(split[3]));

        return this;
    }

    @Override
    public void showElement(Graphics g) {
//        int width = GameJFrame.getInstance().getWidth();
//        int height = GameJFrame.getInstance().getHeight();

        int width = 1000;
        int height = 1000;

        // 绘制半透明的黑色背景
        g.setColor(new Color(0, 0, 0, 250));
        g.fillRect(0, 0, width, this.getY());
        g.fillRect(0, this.getY(), this.getX(), this.getH());
        g.fillRect(this.getX() + this.getW(), this.getY(), width - (this.getX() + this.getW()), this.getH());
        g.fillRect(0, this.getY() + this.getH(), width, height - (this.getY() + this.getH()));
    }


}