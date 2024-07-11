package com.tedu.show;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.game.GameStart;
import com.tedu.manager.GameLoad;

/**
 * @说明 开始页面
 */
public class StartJPanel extends JPanel {

    public JButton startBtn;
    public JButton introBtn;

    public StartJPanel() {

        GameLoad.loadImg();
        ImageIcon icon = GameLoad.imgMap.get("button");


        this.setLayout(null);

        startBtn = new JButton("开始游戏");
        customizeButton(startBtn, icon, 330, 250, 200, 100);

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameJFrame.setJPanel("SelectJPanel");
            }
        });


        introBtn = new JButton("说明");
        customizeButton(introBtn, icon, 330, 350, 200, 100);

        introBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = { "确定" };
                JOptionPane.showOptionDialog(null, "<html><body><tr>Player1: 上下左右wasd, space发射弹药</tr>"
                                + "<tr></tr>"
                                + "<tr>泡泡: 增加泡泡的放置数量</tr>"
                                + "<tr>药水: 增加泡泡的范围</tr></body></html>", "游戏说明",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, null);
            }
        });
        this.add(startBtn);
        this.add(introBtn);
    }

    private void customizeButton(JButton button, ImageIcon icon, int x, int y, int width, int height) {
        // 设置按钮字体
        button.setFont(new Font("微软雅黑", Font.BOLD, 30));
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
        super.paintComponent(g);
        ImageIcon icon = GameLoad.imgMap.get("ground");
        g.drawImage(icon.getImage(), 0, 0, GameJFrame.startjp.getWidth(), GameJFrame.startjp.getHeight(), null);
    }

}
