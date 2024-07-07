package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * &#064;说明  本类是元素管理器 专门存储所有的元素，同时，提供方法
 *      给予视图和控制获取数据
 *
 * @author Eason
 * &#064;问题一：存储所有元素数据，怎么存放          list map set 3大集合
 * &#064;问题二：管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 *
 * 单例模式（Singleton Pattern）是一种常用的软件设计模式，该模式的主要目标是确保一个类只有一个实例，并提供一个全局访问点。
 */
public class ElementManager {
//    private List<Object> listMap;
//    private List<Object> listPlay;

    /*
     * String 作为Key 匹配所有的元素 play -> List<Object> listPlay
     *                           emery -> List<Object> listEmery
     * 枚举类型 当做map的key用来区分不一样的资源，用于获取资源
     * List 中元素的泛型应该是元素基类
     * 所有元素都可以存放到map集合中，显示模块只需要获取到这个map就可以
     * 显示是有的界面需要显示的元素(调用元素基类的showElement())
     */

    private Map<GameElement,List<ElementObj>> gameElements;

    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    public void addElement(ElementObj obj,GameElement ge){
        gameElements.get(ge).add(obj);
    }

    public List<ElementObj> getElementsByKey(GameElement ge) {
        return gameElements.get(ge);
    }

    /*
     *单例模式 内存中有且只有一个实例
     * 饿汉模式：在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快。这是因为单例的实例在类加载时就已经创建。这种方式基于类加载机制，避免了多线程的同步问题。
     * 饱汉模式（也称为懒汉模式）：在类加载时不初始化。这种方式的主要优点是起到了Lazy Loading的效果，但是也因此可能会产生线程安全问题。
     *
     * 编写方式
     * 1 需要一个静态的属性(定义一个常量) 单例的引用
     * 2 提供一个静态方法(返回这个实例) return 单例的引用
     * 3 一般为防止其他人自己使用(类是可以实例化) 构造方法私有化
     *
     * ElementManager em = new ElementManager();
     */

    private  static ElementManager EM=null;

    public static synchronized ElementManager getManager(){
        if (EM==null){
            EM=new ElementManager();
        }
        return EM;
    }

    private ElementManager(){
        //初始化map
        init();
    }

    private void init() {
        gameElements = new HashMap<GameElement,List<ElementObj>>();
        for(GameElement ge : GameElement.values()){
            gameElements.put(ge,new ArrayList<>());
        }
    }



}
