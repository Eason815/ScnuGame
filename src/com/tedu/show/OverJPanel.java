package com.tedu.show;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.tedu.controller.GameThread;
import com.tedu.game.GameStart;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameLoad;

public class OverJPanel extends JPanel {

    public JButton btn;


    public OverJPanel() {



        this.setLayout(null);
        btn = new JButton("确定");
//        btn.setFont(new Font("微软雅黑", Font.BOLD, 40));
//        btn.setBorderPainted(false);
//        btn.setContentAreaFilled(false);
//        btn.setBounds(250, 200, 200, 100);
//
//
//        btn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                GameJFrame.setJPanel("MainJPanel");
//            }
//        });

        customizeButton(btn, GameLoad.imgMap.get("button"), 350, 400, 200, 100, 20);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameJFrame.setJPanel("SelectJPanel");
            }
        });
        this.add(btn);
    }

    private void customizeButton(JButton button, ImageIcon icon, int x, int y, int width, int height,int fontSize) {
        // 设置按钮字体
        button.setFont(new Font("微软雅黑", Font.BOLD, fontSize));
        // 设置按钮无边框
        button.setBorderPainted(false);
        // 设置按钮内容区透明
        button.setContentAreaFilled(false);
        // 设置按钮位置和大小
        button.setBounds(x, y, width, height);
        // 设置焦点不显示
        button.setFocusPainted(false);
        // 缩放图片
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);

        // 加载图片并设置为按钮背景
        button.setIcon(icon);

        // 设置按钮文本位置
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);

        // 设置按钮前景颜色
        button.setForeground(Color.WHITE);

        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.YELLOW);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = GameLoad.imgMap.get("beach");
        g.drawImage(icon.getImage(), 0, 0, GameJFrame.overjp.getWidth(), GameJFrame.overjp.getHeight(), null);

        g.setFont(new Font("Georgia", Font.BOLD, 66));
        String gameOverText = "Game Over";
        int textWidth = g.getFontMetrics().stringWidth(gameOverText);
        int textHeight = g.getFontMetrics().getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 ;
        g.setColor(Color.RED);
        g.drawString(gameOverText, x, y);
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑", Font.BOLD, 24));

        String result = "";
        if (GameThread.EndStat == 1)
            result = "游戏胜利!";
        else
            result = "游戏失败!";

        g.drawString(result, 400, 288);
        g.drawString("Score: " + GameThread.Score, 400, 318);
    }

}
