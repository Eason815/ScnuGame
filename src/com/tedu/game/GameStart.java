package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

/*
作业内容：
       在day04代码的基础上添加以下功能实现：

     1 、开始游戏后，在不同的位置显示5辆同一类型会左右运动的敌军坦克。
     2、控制主角坦克开始运动并射击敌军坦克，坦克消灭后记录记录得分。
     3、当所有敌军坦克被消灭完后，显示统计结果。5 秒后进入下一关。
     4、第二关 加载新的地图，显示10辆坦克，其中5辆跟第一关的一样，另外5辆类型不同，且会上下移动。
     5、控制主角坦克开始运动并射击敌军坦克，坦克消灭后记录记录得分。
     6、当所有敌军坦克被消灭完后，显示统计结果。5秒后游戏结束。

    注意事项：

       1  、 个人独立完成，与小组其他成员无关。检验自己前第一阶段的学习效果，测试自己是否已经具备开完成下一阶段任务的能力。
       2 、  提交项目 src 文件夹所有文件压缩成zip 或 rar 文件。
 */


public class GameStart {
    public static void main(String[] args) {
//        GameJFrame gj = new GameJFrame();
//        GameMainJPanel jm = new GameMainJPanel();
//        GameListener gl = new GameListener();
//        GameThread th = new GameThread();
//
//        gj.setJPanel(jm);
//        gj.setKeyListener(gl);
//        gj.setThread(th);
//
//        gj.start();

        GameJFrame.setJPanel("MainJPanel");


    }
}
