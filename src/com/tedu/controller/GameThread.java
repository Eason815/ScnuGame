package com.tedu.controller;

import com.tedu.element.*;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

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
    public static int GameProcess = 0;
    public static int EndStat=0;

    public static int FlyLevel = 4;//0 1 2 3
    public GameThread(int Process) {
        GameProcess = Process;
        Score = 0;
        EndStat = 0;
        em = ElementManager.getManager();
    }

    @Override
    public void run() {
        //游戏开始前
        gameLoad();
        //游戏进行时
        gameRun();
        //游戏场景结束
        gameOver();
    }

    private void gameLoad() {

        em.clearElements();
        GameLoad.loadImg();
        GameLoad.MapLoad(GameProcess + 1);
        GameLoad.loadPlay();
        GameLoad.loadEnemy(GameProcess);
        if(GameProcess==3)
            GameLoad.loadMask();

    }

    private void gameRun() {
        long GameTime = 0L;
        EndStat = 0;// 初始化
        InfoAndGoal();

        long lastTime = 0; //记录上次生成敌军的时间
        long enemyInterval = 500; //敌军生成间隔
        while (true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> emeries = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);

            if (CheckWin(all)) {
                EndStat = 1;// 游戏胜利
                break;
            }

            UpdateThing(all, GameTime);

            CheckCrash(bullets, emeries);
            CheckCrash(bullets, players);
            CheckCrash(bullets, maps);
            CheckCrash(players, emeries);


            // 每隔一段时间生成新的敌军
            if (GameProcess==FlyLevel && System.currentTimeMillis() - lastTime > enemyInterval) {
                GameLoad.loadEnemy(GameProcess);
                lastTime = System.currentTimeMillis();
            }

            GameTime++;
//            System.out.println(GameTime);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(CheckFailed(all)){
                EndStat=2;//游戏失败
                break;
            }


        }
    }



    private static void CheckCrash(List<ElementObj> list1, List<ElementObj> list2) {
        for (ElementObj obj1 : list1) {
            for (ElementObj obj2 : list2) {
                if (obj2 instanceof MapObj) {
                    if (obj2.getName().equals("GRASS") || obj2.getName().equals("RIVER"))
                        continue;
                }

                if (obj1.isCash(obj2)) {
                    // 敌人发射的子弹击中敌人 忽略掉
                    if (obj1 instanceof Bullet && obj2 instanceof Enemy && !((Bullet) obj1).isFromPlayer()) {
                        continue;
                    }
                    obj2.setLive(false);
                    obj1.setLive(false);
                    if (obj1 instanceof Bullet && obj2 instanceof Enemy) {
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
            for (int i = list.size() - 1; i >= 0; i--) {
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

        List<ElementObj> player = all.get(GameElement.PLAY);
        List<ElementObj> masks = all.get(GameElement.MASK);
        for (ElementObj obj : player) {
            for (ElementObj mask : masks) {
                if ((obj instanceof Player) && (mask instanceof Mask)) {
                    Player p1 = (Player) obj;
                    Mask m1 = (Mask) mask;
                    m1.setX(p1.getX()-Mask.wid/2);
                    m1.setY(p1.getY()-Mask.wid/2);
//                    System.out.println(1111);
                }
            }
        }

    }
    private static boolean CheckWin(Map<GameElement, List<ElementObj>> all) {
        if(all.get(GameElement.ENEMY).isEmpty() && GameProcess!=FlyLevel)
            return true;
        else return Score >= 200;
    }

    private static boolean CheckFailed(Map<GameElement, List<ElementObj>> all) {
        List<ElementObj> player = all.get(GameElement.PLAY);
        if (player.isEmpty())
            return true;

        for(ElementObj obj: player) {
            if(!(obj instanceof Player)) {
                break;
            }
            Player object = (Player) obj;
            if (object.getBulletsNum()==0)
                return true;
        }

        return false;
    }


    private void InfoAndGoal(){
        Object[] options = { "确定" };

        String [] Info = {"1 击碎墙体有5%掉落血包","\n2 击碎墙体有15%掉落弹药补给","\n3 击碎墙体有5%触发激光束陷阱","\n4 视野受阻!"};
        StringBuilder Info1= new StringBuilder();
        if (GameProcess==FlyLevel) Info1.append("飞机大战来临！");
        else {
            for (int i = 0; i < GameProcess + 1; i++)
                Info1.append(Info[i]);
        }

        String [] Goals = {"存活并击败所有敌人","得分200"};
        String s1 = GameProcess==(FlyLevel)?Goals[1]:Goals[0];

        JOptionPane.showOptionDialog(null, "第"+(GameProcess+1)+"关" + "\n"
                        + "规则:"+Info1 + "\n"
                        + "目标:"+s1 + "\n"
                        + " ", "游戏说明",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, null);
    }



    private void gameOver() {

        em.clearElements();
        GameJFrame.setJPanel("OverJPanel");


    }



}
