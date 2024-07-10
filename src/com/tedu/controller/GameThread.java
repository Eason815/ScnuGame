package com.tedu.controller;

import com.tedu.element.Bullet;
import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.MapObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameThread extends Thread{

    private ElementManager em;

    public static int Score = 0;
    public static int GameProcess = 2;

    public static int EndStat=0;
    public GameThread() {
        em = ElementManager.getManager();
    }

    @Override
    public void run() {
        while (true){
            //游戏开始前
            gameLoad();
            //游戏进行时
            gameRun();
            //游戏场景结束
            gameOver();
        }
    }

    private void gameLoad() {

                em.clearElements();
                GameLoad.loadImg();
                GameLoad.MapLoad(GameProcess+1);
                GameLoad.loadPlay();
                GameLoad.loadEnemy(GameProcess);

    }

    private void gameRun() {
        long GameTime = 0L;
        EndStat=0;//初始化
        while(true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> emeries = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);

            if(CheckNoEnemy(all)){
                EndStat=1;//游戏胜利
                break;
            }



            UpdateThing(all, GameTime);

            CheckCrash(bullets, emeries);
            CheckCrash(bullets, maps);
            CheckCrash(players, emeries);




            GameTime++;

            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(CheckNoPlayer(all)){
                EndStat=2;//游戏失败
                break;
            }


        }
    }



    private static void CheckCrash(List<ElementObj> list1, List<ElementObj> list2) {
        for(ElementObj obj1 : list1) {
            for (ElementObj obj2 : list2) {
                if(obj2 instanceof MapObj){
                    if(obj2.getName().equals("GRASS"))
                        continue;
                }

                if (obj1.isCash(obj2)) {
                    obj2.setLive(false);
                    obj1.setLive(false);
                    if(obj1 instanceof Bullet && obj2 instanceof Enemy) {
                        Score++;
                    }
                    break;
                }
            }
        }
    }

    private static void UpdateThing(Map<GameElement, List<ElementObj>> all, long GameTime) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            List<ElementObj> toRemove = new ArrayList<>(); // 暂存需要删除的元素
            // 不能用for each
            for(int i=list.size()-1;i>=0;i--){
                ElementObj obj = list.get(i);
                if (!obj.isLive()) {
                    obj.die();
                    toRemove.add(obj); // 记录需要删除的元素
                    continue;
                }
                obj.model(GameTime);
            }
            list.removeAll(toRemove); // 统一删除元素
        }
    }

    private static boolean CheckNoEnemy(Map<GameElement, List<ElementObj>> all) {
        return all.get(GameElement.ENEMY).isEmpty();
    }

    private static boolean CheckNoPlayer(Map<GameElement, List<ElementObj>> all) {
        return all.get(GameElement.PLAY).isEmpty();
    }

    private static void FiveSecondLoad(String inputStr) {
        for (int i = 5; i > 0; i--) {
            int remainingTime = i;
            JOptionPane pane = new JOptionPane("您的得分为：" + Score +"\n剩余" + remainingTime + "秒"+ "\n"+inputStr,
                    JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

            JDialog dialog = pane.createDialog("关卡倒计时");

            Timer timer = new Timer(1000 , new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });

            timer.setRepeats(false);
            timer.start();
            dialog.setVisible(true);
        }
    }

    private void gameOver() {

        //显示游戏结束

        if(EndStat==1) {
            switch (GameProcess++) {

                case 0:
                    FiveSecondLoad("即将进入下一关\n敌人增多");
                    break;
                case 1:
                    FiveSecondLoad("即将进入下一关\n激光束");
                    break;
                case 11:
                    FiveSecondLoad("即将进入下一关\n激光束");
                    break;
                case 12:
                    FiveSecondLoad("即将进入下一关\n激光束");
                    break;
                case 13:
                    FiveSecondLoad("即将进入下一关\n激光束");
                    break;
                case 2:
                    FiveSecondLoad("游戏结束");
                    System.exit(0);

            }
        } else if (EndStat==2) {
            FiveSecondLoad("游戏失败 将重新开始第"+(GameProcess+1)+"关");

        }
        Score=0;
        EndStat=0;


    }



}
