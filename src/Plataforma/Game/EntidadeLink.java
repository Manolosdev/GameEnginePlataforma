/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.Game;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Seven
 */
public class EntidadeLink extends Entidade {

    String levelFileName;
    Jogo game;

    public EntidadeLink(int x, int y, int width, int height, String fileName,Jogo game) {
        super(x, y);
        pos.setRect(x, y, width, height);
        levelFileName = fileName;
        this.game = game;
    }

    @Override
    public void init() {
    }

    @Override
    public void update(int currentTick) {
        for (Entidade e : collidingEntities){
            if(e != null && e instanceof EntidadeJogador){
                game.setNextLevel(levelFileName);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.white);
        g.draw(pos);
        g.drawString(levelFileName, (int) pos.x + 5, (int) pos.y + 15);
    }

}
