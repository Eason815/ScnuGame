package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.*;
import java.util.List;

public class Laser extends ElementObj {
    private boolean horizontal; // true for horizontal, false for vertical
    private long creationTime; // 记录激光束的创建时间
    private static final long DURATION = 1000; // 激光束显示的时间（毫秒）
    private boolean hitPlayer; // 记录是否命中过玩家

    public Laser(int x, int y, boolean horizontal) {
        this.setX(x);
        this.setY(y);
        this.horizontal = horizontal;
        this.setLive(true);
        this.creationTime = System.currentTimeMillis(); // 记录创建时间
        this.hitPlayer = false; // 初始化为未命中
    }

    @Override
    public void showElement(Graphics g) {
        if (this.isLive()) {
            g.setColor(Color.RED); // 设置激光束颜色
            if (horizontal) {
                g.drawLine(0, this.getY(), this.getX() + 900, this.getY()); // 横向激光束
            } else {
                g.drawLine(this.getX(), 0, this.getX(), this.getY() + 570); // 纵向激光束
            }
        }
    }

    @Override
    public void move(long gameTime) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.creationTime > DURATION) {
            this.setLive(false); // 超过显示时间，设置为不可见
            return;
        }

        // 激光束不移动，只需检测碰撞
        if (!hitPlayer) {
            List<ElementObj> players = ElementManager.getManager().getElementsByKey(GameElement.PLAY);
            for (ElementObj player : players) {
                if (this.horizontal && player.getY() < this.getY() && player.getY()+player.getH() > this.getY() && player.isLive()) {
                    player.setLive(false); // 减少玩家HP
                    hitPlayer = true; // 记录已经命中
                    break; // 退出循环
                } else if (!this.horizontal && player.getX() < this.getX() && player.getX()+player.getW() > this.getX() && player.isLive()) {
                    player.setLive(false); // 减少玩家HP
                    hitPlayer = true; // 记录已经命中
                    break; // 退出循环
                }
            }
        }
    }
}

