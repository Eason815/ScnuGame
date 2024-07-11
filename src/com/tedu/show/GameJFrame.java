package com.tedu.show;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameJFrame extends JFrame {
    public static int GameX = 900;
    public static int GameY = 600;
    private JPanel jPanel=null;
    private KeyListener keyListener=null;
    private MouseListener mouseListener=null;
    private MouseMotionListener mouseMotionListener=null;
    private Thread thread =null;



    public static GameJFrame gj = new GameJFrame();
    // 主面板
    public static StartJPanel startjp;
    // 挑选地图面板
    public static LevelSelectJPanel leveljp;
    // 游戏面板
    public static GameMainJPanel mainjp;
    // 结束面板
    public static OverJPanel overjp;



    public GameJFrame() {
        init();
    }

    private void init() {
        this.setTitle("坦克大战");
        this.setSize(GameX, GameY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//顺带关闭
        this.setLocationRelativeTo(null);//居中
    }



    public static void setJPanel(String Name) {
        if (Name.equals("MainJPanel")) {
            startjp = new StartJPanel();
            gj.setjPanel(startjp);
            gj.start();
        }
        if (Name.equals("SelectJPanel")) {
            leveljp = new LevelSelectJPanel();
            gj.setjPanel(leveljp);
            gj.start();
        }
        if (Name.equals("GameMainJPanel")) {
            // 实例化监听
            GameListener listener = new GameListener();
            // 实例化主线程
            GameThread th = new GameThread(GameThread.GameProcess);

            mainjp = new GameMainJPanel();
            gj.setjPanel(mainjp);
            gj.setKeyListener(listener);
            gj.setThread(th);
            gj.start();
            gj.setFocusable(true);
        }
        if (Name.equals("OverJPanel")) {
            overjp = new OverJPanel();
            gj.setjPanel(overjp);
            gj.setThread(null);
            gj.start();
        }
        gj.setVisible(false);
        gj.setVisible(true);
    }




    public void setjPanel(JPanel jPanel) {
        if (this.jPanel != null) {
            this.remove(this.jPanel);
        }
        this.jPanel = jPanel;
    }


    // 窗体启动方法
    public void start() {
        if (jPanel != null) {
            this.add(jPanel);
        }
        if (keyListener != null) {
            this.addKeyListener(keyListener);
        }
        if (mouseMotionListener != null) {
            this.addMouseMotionListener(mouseMotionListener);
        }
        if (mouseListener != null) {
            this.addMouseListener(mouseListener);
        }
        if (thread != null) {
            System.out.println();
            thread.start();
        }
        // 界面刷新，显示窗体
        this.setVisible(true);

        // 做了类型判断，强制类型不会出错
        if (jPanel instanceof Runnable) {
            // 启动子线程来不断刷新界面
            new Thread((Runnable) jPanel).start();
        }
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }


}
