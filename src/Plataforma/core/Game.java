/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

/**
 *
 * @author Manolo
 */
abstract public class Game implements WindowListener {

    private JFrame mainWindow;
    private boolean active;
    private BufferStrategy bs;
    private GameSpeedTracker speedTracker;
    private int expectedTPS;
    private double expectedNanosPerTicks;

    public Game() {
        mainWindow = new JFrame("Plataforma   By: Manolo's");
        mainWindow.setSize(800, 600);
        mainWindow.addWindowListener(this);
        mainWindow.addKeyListener(InputManager.getInstance());
        mainWindow.addMouseListener(InputManager.getInstance());
        mainWindow.addMouseMotionListener(InputManager.getInstance());
        active = false;
    }

    //run 'Game Loop',metodos que executa os processos como logica,imagens e 
    //sons no jogo
    public void run() {
        active = true;
        load();
        expectedTPS = 60;

        expectedNanosPerTicks = GameSpeedTracker.NANOS_IN_ONE_SECOND / expectedTPS;
        long nanoTimeAtNextTick = System.nanoTime();

        while (active) {
            speedTracker.update();
            if (System.nanoTime() > nanoTimeAtNextTick) {
                nanoTimeAtNextTick += expectedNanosPerTicks;
                InputManager.getInstance().update();
                update();
                render();
            }
            if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
                active = false;
            }
        }
        unload();
    }

    public void load() {
        mainWindow.setUndecorated(false);
        mainWindow.setIgnoreRepaint(true);
        mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);
        mainWindow.createBufferStrategy(2);
        bs = mainWindow.getBufferStrategy();
        speedTracker = new GameSpeedTracker();
        onLoad();
        speedTracker.start();
    }

    public void update() {
        speedTracker.countTick();
        onUpdate(speedTracker.totalTicks);
        Thread.yield();
    }

    public void render() {
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        onRender(g);
        g.setColor(Color.darkGray);
        g.fillRect(14, 39, 51, 18);
        g.setColor(Color.white);
        g.drawRoundRect(13, 38, 50, 18, 3, 3);
        g.setFont(new Font("", Font.BOLD, 12));
        g.drawString("FPS", 15, 51);
        g.drawString("" + speedTracker.getTPS(), 45, 51);
        g.dispose();
        bs.show();
    }

    public void unload() {
        onUnload();
        bs.dispose();
        mainWindow.dispose();
    }

    public void terminate() {
        active = false;

    }

    //Metodos abstratos para execucao nas classes derivadas
    abstract public void onLoad();

    abstract public void onUpdate(int currentTick);

    abstract public void onRender(Graphics2D g);

    abstract public void onUnload();

    //Geters
    public JFrame getMainWindow() {
        return mainWindow;
    }

    public int getWidth() {
        return mainWindow.getWidth();
    }

    public int getHeight() {
        return mainWindow.getHeight();
    }

    //Açoes da Window
    @Override
    public void windowClosing(WindowEvent e) {
        terminate();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

}
