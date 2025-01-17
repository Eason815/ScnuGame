package com.tedu.manager;

import com.tedu.controller.GameThread;
import com.tedu.element.ElementObj;
import com.tedu.element.MapObj;
import com.tedu.element.Mask;


import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;



public class GameLoad {

    private static ElementManager em=ElementManager.getManager();
    public static Map<String,ImageIcon> imgMap = new HashMap<>();

    public static Map<String, Clip> soundMap = new HashMap<>();
    private static Properties pro =new Properties();

    public static void MapLoad(int mapId) {

        if(GameThread.GameProcess==GameThread.FlyLevel){

            return;
        }

        String mapName="com/tedu/text/"+mapId+".map";

        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if(maps ==null) {
            System.out.println("配置文件读取异常,请重新安装");
            return;
        }
        try {
//			以后用的 都是 xml 和 json
            pro.clear();
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            while(names.hasMoreElements()) {//获取是无序的
//				这样的迭代都有一个问题：一次迭代一个元素。
                String key=names.nextElement().toString();
//                System.out.println(pro.getProperty(key));
//				就可以自动的创建和加载 我们的地图啦
                String [] arrs=pro.getProperty(key).split(";");
                for(int i=0;i<arrs.length;i++) {
                    ElementObj element = new MapObj().createElement(key+","+arrs[i]);
//                    System.out.println(element);
                    em.addElement(element, GameElement.MAPS);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static void loadImg() {//可以带参数，因为不同的关也可能需要不一样的图片资源
        String texturl="com/tedu/text/GameData.pro";//文件的命名可以更加有规律
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);

        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set) {
                String url=pro.getProperty(o.toString());
                imgMap.put(o.toString(), new ImageIcon(url));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void loadMAV(){
        String texturl="com/tedu/text/MAV.pro";//文件的命名可以更加有规律
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);

        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();
            for(Object o:set) {
                String url=pro.getProperty(o.toString());
                soundMap.put(o.toString(), SoundPlayer(url));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static Clip SoundPlayer(String filePath) {
        Clip clip = null;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    public static void play(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0); // 从头开始播放
            clip.start();
        }
    }

    public static void loadPlay() {
        loadObj();
        String playStr="";
        if(GameThread.GameProcess==GameThread.FlyLevel)
            playStr = "100,100,right2,6,10000,2";
        else {
            int[] hps = {1, 1, 2, 2};
            int[] bls = {1000, 30, 30, 20};
            playStr = "500,500,up," +
                    hps[GameThread.GameProcess] + "," +
                    bls[GameThread.GameProcess] + "," +
                    "1";
        }
        ElementObj obj=getObj("play");
        ElementObj play = obj.createElement(playStr);
//		ElementObj play = new Play().createElement(playStr);
//		解耦,降低代码和代码之间的耦合度 可以直接通过 接口或者是抽象父类就可以获取到实体对象
        em.addElement(play, GameElement.PLAY);
    }

    public static void loadEnemy(int mode){
        loadObj();
        if (mode == 0) {

            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("right,1");
                em.addElement(enemy, GameElement.ENEMY);
            }
        } else if (mode == 1) {
            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("right,1");
                em.addElement(enemy, GameElement.ENEMY);
            }
            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("up,2");
                em.addElement(enemy, GameElement.ENEMY);
            }

        } else if (mode==2) {
            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("right,1");
                em.addElement(enemy, GameElement.ENEMY);
            }
            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("up,2");
                em.addElement(enemy, GameElement.ENEMY);
            }

        }else if(mode==3){
            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("right,1");
                em.addElement(enemy, GameElement.ENEMY);
            }
            for (int i = 0; i < 5; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("up,2");
                em.addElement(enemy, GameElement.ENEMY);
            }
        }
        else if(mode==4){
            int enemyCount = 1;

            for (int i = 0; i < enemyCount; i++) {
                ElementObj obj = getObj("enemy");
                ElementObj enemy = obj.createElement("left,3"); // 假设窗体宽度为900，敌军飞机出现在850的位置
                em.addElement(enemy, GameElement.ENEMY);
            }
        }
    }

    public static void loadMask(){
            loadObj();
            ElementObj obj = getObj("mask");
            String build = "400,400"+","+ Mask.wid + "," +Mask.wid;
            ElementObj mask = obj.createElement(build);
            em.addElement(mask, GameElement.MASK);
    }

    public static ElementObj getObj(String str) {
        try {
            Class<?> class1 = objMap.get(str);
            Object newInstance = class1.newInstance();
            if(newInstance instanceof ElementObj) {
                return (ElementObj)newInstance;   //这个对象就和 new Play()等价
//				新建立啦一个叫  GamePlay的类
            }
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String,Class<?>> objMap=new HashMap<>();

    public static void loadObj() {
        String texturl="com/tedu/text/obj.pro";//文件的命名可以更加有规律
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet();//是一个set集合
            for(Object o:set) {
                String classUrl=pro.getProperty(o.toString());
//				使用反射的方式直接将 类进行获取
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(), forName);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
