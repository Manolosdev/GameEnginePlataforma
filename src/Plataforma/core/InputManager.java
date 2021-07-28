/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.core;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.MouseInputListener;

/**
 * Informar Eventos de teclado,verifica quais
 *
 * @author Seven
 */
public class InputManager implements KeyListener, MouseInputListener {

    static protected int KEY_RELEASED = 0;
    static protected int KEY_JUST_PRESSED = 1;
    static protected int KEY_PRESSED = 2;
    static private InputManager instance;

    HashMap<Integer, Integer> keyCache;
    private ArrayList<Integer> pressedkeys;
    private ArrayList<Integer> releasedkeys;

    private boolean mouseButton1;
    private boolean mouseButton2;
    private Point mousePos;

    private InputManager() {
        keyCache = new HashMap<Integer, Integer>();
        pressedkeys = new ArrayList<Integer>();
        releasedkeys = new ArrayList<Integer>();
        mousePos = new Point();
    }

    static public InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
    /*///////////////////////////////////////////////////////////////////////////
     / Eventos do Teclado                                                        /
     ///////////////////////////////// /////////////////////////////////////////*/

    public boolean isPressed(int keyId) {
        return keyCache.containsKey(keyId) && keyCache.get(keyId).equals(KEY_PRESSED);
    }

    public boolean isJustPressed(int keyId) {
        return keyCache.containsKey(keyId) && keyCache.get(keyId).equals(KEY_JUST_PRESSED);
    }

    public boolean isReleased(int keyId) {
        return !keyCache.containsKey(keyId) || keyCache.get(keyId).equals(KEY_RELEASED);
    }

    public void update() {
        for (Integer keyCode : keyCache.keySet()) {
            if (isJustPressed(keyCode)) {
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        for (Integer keyCode : pressedkeys) {
            if (isReleased(keyCode)) {
                keyCache.put(keyCode, KEY_JUST_PRESSED);
            } else {
                keyCache.put(keyCode, KEY_PRESSED);
            }
        }
        for (Integer keyCode : releasedkeys) {
            keyCache.put(keyCode, KEY_RELEASED);
        }
        pressedkeys.clear();
        releasedkeys.clear();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedkeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        releasedkeys.add(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Rotina nao utilizada
    }

    /*///////////////////////////////////////////////////////////////////////////
     / Eventos do mouse                                                          /
     ///////////////////////////////// /////////////////////////////////////////*/
    public boolean isMousePressed(int buttonId) {
        if (buttonId == MouseEvent.BUTTON1) {
            return mouseButton1;
        }
        if (buttonId == MouseEvent.BUTTON2) {
            return mouseButton2;
        }
        return false;
    }

    public int getMouseX() {
        return (int) mousePos.getX();
    }

    public int getMouseY() {
        return (int) mousePos.getY();
    }

    public Point getMousePos() {
        return mousePos;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseButton1 = true;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            mouseButton2 = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseButton1 = false;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            mouseButton2 = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos.setLocation(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos.setLocation(e.getPoint());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Rotina nao utilizada
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Rotina nao utilizada
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Rotina nao utilizada
    }
}
