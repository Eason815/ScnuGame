package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.*;
import java.util.List;

public class Laser extends ElementObj {
    private boolean horizontal; // true for horizontal, false for vertical

    public Laser(int x, int y, boolean horizontal) {
        this.setX(x);
        this.setY(y);
        this.horizontal = horizontal;
        this.setLive(true);
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.RED); // 设置激光束颜色
        if (horizontal) {
            g.drawLine(this.getX(), this.getY(), this.getX() + 900, this.getY()); // 横向激光束
        } else {
            g.drawLine(this.getX(), this.getY(), this.getX(), this.getY() + 570); // 纵向激光束
        }
    }

    @Override
    public void move(long gameTime) {
        // 激光束不移动，只需检测碰撞
        // 检测是否击中玩家
        List<ElementObj> players = ElementManager.getManager().getElementsByKey(GameElement.PLAY);
        for (ElementObj player : players) {
            if (this.horizontal && player.getY() == this.getY()) {
                player.setLive(false); // 玩家被击中
            } else if (!this.horizontal && player.getX() == this.getX()) {
                player.setLive(false); // 玩家被击中
            }
        }
    }
}

