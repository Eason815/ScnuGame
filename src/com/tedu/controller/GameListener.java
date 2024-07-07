package com.tedu.controller;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameListener implements KeyListener {

    Set<Integer> set = new HashSet<Integer>();

    ElementManager em = ElementManager.getManager();

    @Override
    public void keyTyped(KeyEvent e){

    }

    /**
     * 左37 上38 右39 下40
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println("KeyPressed"+e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        if(set.contains(e.getKeyCode()))
            return;
        set.add(e.getKeyCode());
        for(ElementObj obj:play)
            obj.keyClick(true,e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        System.out.println("KeyPressed"+e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        if(!set.contains(e.getKeyCode()))
            return;
        set.remove(e.getKeyCode());
        for(ElementObj obj:play)
            obj.keyClick(false,e.getKeyCode());
    }


}
