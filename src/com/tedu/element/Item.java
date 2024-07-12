package com.tedu.element;

import java.awt.*;

public class Item extends ElementObj {
    public enum ItemType {
        HEALTH,  //道具1 血包
        AMMUNITION //道具2 弹药
    }

    private ItemType type;

    public Item(int x, int y,int w,int h, ItemType type) {

        this.setX(x);
        this.setY(y);
        this.setW(w);
        this.setH(h);
        this.type = type;
        this.setLive(true);
    }

    public ItemType getType() {
        return type;
    }


    @Override
    public void showElement(Graphics g) {
        if (this.isLive()) {
            if (type == ItemType.HEALTH) {
                g.setColor(Color.GREEN);
            } else if (type == ItemType.AMMUNITION) {
                g.setColor(Color.ORANGE);
            }
            g.fillRect(this.getX(), this.getY(), 20, 20); // 简单的矩形表示道具
        }
    }

    @Override
    public void move(long gameTime) {
        // 道具不移动，可以留空
    }
}
