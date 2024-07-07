package com.tedu.show;

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
    private Thread thead=null;

    public GameJFrame() {
        init();
    }

    private void init() {
        this.setTitle("坦克大战");
        this.setSize(GameX, GameY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//顺带关闭
        this.setLocationRelativeTo(null);//居中
    }

    public void start(){
        if(jPanel!=null) {
            this.add(jPanel);
        }
        if(keyListener !=null) {
            this.addKeyListener(keyListener);
        }
        if(thead !=null) {
            thead.start();//启动线程
        }
        this.setVisible(true);//显示界面
//		如果jp 是 runnable的 子类实体对象
//		如果这个判定无法进入就是 instanceof判定为 false 那么 jpanel没有实现runnable接口
        if(this.jPanel instanceof Runnable) {
//			已经做类型判定，强制类型转换不会出错
//			new Thread((Runnable)this.jPanel).start();
            Runnable run=(Runnable)this.jPanel;
            Thread th=new Thread(run);
            th.start();//
        }


    }


    public void setJPanel(JPanel jPanel) {
        this.jPanel = jPanel;
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
        this.thead = thread;
    }


}
