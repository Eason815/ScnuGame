package com.tedu.show;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.game.GameStart;
import com.tedu.manager.GameLoad;

/**
 * 选关卡面板
 */
public class LevelSelectJPanel extends JPanel {

    public JButton btn1;
    public JButton btn2;

    public JButton btn3;
    public JButton btn4;
    public JButton btn5;
    public JButton btn6;
    public JLabel jl;

    public static int map = 0;// 选择关卡

    public LevelSelectJPanel() {

        this.setLayout(null);
        ImageIcon icon = GameLoad.imgMap.get("button");
        Font font = new Font("微软雅黑", Font.BOLD, 30);

        jl = new JLabel("关卡选择");
        jl.setFont(font);
        jl.setBounds(380, 200, 200, 100);


        btn1 = new JButton("第一关");
        customizeButton(btn1,icon,155,300,90,45,16,1);

        btn2 = new JButton("第二关");
        customizeButton(btn2,icon,275,300,90,45,16,2);

        btn3 = new JButton("第三关");
        customizeButton(btn3,icon,395,300,90,45,16,3);

        btn4 = new JButton("第四关");
        customizeButton(btn4,icon,515,300,90,45,16,4);

        btn5 = new JButton("第五关");
        customizeButton(btn5,icon,635,300,90,45,16,5);

        this.add(jl);
        this.add(btn1);
        this.add(btn2);
        this.add(btn3);
        this.add(btn4);
        this.add(btn5);
    }

    private void customizeButton(JButton button, ImageIcon icon, int x, int y, int width, int height,int fontSize,int num) {
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

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameThread.GameProcess = num-1;
                GameJFrame.setJPanel("GameMainJPanel");
            }
        });
    }



    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon icon = GameLoad.imgMap.get("ground");
        g.drawImage(icon.getImage(), 0, 0, GameJFrame.leveljp.getWidth(), GameJFrame.leveljp.getHeight(), null);
    }

}
