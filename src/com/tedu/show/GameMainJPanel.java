package com.tedu.show;

import com.tedu.controller.GameThread;
import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMainJPanel extends JPanel implements Runnable{
    private ElementManager em;
    public GameMainJPanel() {
        init();
    }

    public void init() {
        em = ElementManager.getManager();
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);

//        Map<GameElement, List<ElementObj>> all = em.getGameElements();

        // 创建集合的副本
        Map<GameElement, List<ElementObj>> all = new HashMap<>();
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> originalList = em.getGameElements().get(ge);
            List<ElementObj> copyList = new ArrayList<>(originalList);
            all.put(ge, copyList);
        }

        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            for (ElementObj obj : list) {
                obj.showElement(g); // 调用showElement方法渲染元素
            }
        }

        // 左上角显示得分
        g.setColor(Color.black);
        g.setFont(new Font("黑体", Font.BOLD, 20));
        g.drawString("得分: " + GameThread.Score, 10, 30);
        g.drawString("第"+(GameThread.GameProcess+1)+"关", 10, 60);

    }

    @Override
    public void run() {  //接口实现
        while(true) {
//			System.out.println("多线程运动");
            this.repaint();
//			一般情况下，多线程都会使用一个休眠,控制速度
            try {
                Thread.sleep(10); //休眠10毫秒 1秒刷新20次
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
