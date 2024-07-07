package com.tedu.controller;

import com.tedu.element.Bullet;
import com.tedu.element.ElementObj;
import com.tedu.element.Enemy;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class GameThread extends Thread{

    private ElementManager em;

    public static int Score = 0;
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

        GameLoad.loadImg();
        GameLoad.MapLoad(8);
        GameLoad.loadPlay();
        GameLoad.loadEnemy();

    }

    private void gameRun() {
        long GameTime = 0L;

        while(true) {
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> emeries = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
            List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);


            UpdateThing(all, GameTime);

            CheckCrash(bullets, emeries);
            CheckCrash(bullets, maps);

            CheckBorder(players, maps);


            if(Score==5){

            }
            GameTime++;


            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //检测坦克与墙体碰撞
    private static void CheckBorder(List<ElementObj> list1, List<ElementObj> list2){

        for(ElementObj obj1 : list1) {
            for (ElementObj obj2 : list2) {
                if (obj1.isCash(obj2)) {

                    break;
                }
            }
        }
    }



    private static void CheckCrash(List<ElementObj> list1, List<ElementObj> list2) {
        for(ElementObj obj1 : list1) {
            for (ElementObj obj2 : list2) {
                if (obj1.isCash(obj2)) {
                    obj2.setLive(false);
                    obj1.setLive(false);
                    if(obj1 instanceof Bullet && obj2 instanceof Enemy) {
                        Score++;
                        System.out.println(Score);
                    }
                    break;
                }
            }
        }
    }

    private static void UpdateThing(Map<GameElement, List<ElementObj>> all, long GameTime) {
        for (GameElement ge : GameElement.values()) {
            List<ElementObj> list = all.get(ge);
            // 不能用for each
            for(int i=list.size()-1;i>=0;i--){
                ElementObj obj = list.get(i);
                if(!obj.isLive()){
                    obj.die();
                    list.remove(i);
                    continue;
                }
                obj.model(GameTime);
            }
        }
    }


    private void gameOver() {


    }



}
