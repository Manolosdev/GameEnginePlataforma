/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.Game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Seven
 */
public abstract class Entidade {
    
    static final int COLLIDING_ABOVE = 0; // cima
    static final int COLLIDING_RIGHT = 1; // direita
    static final int COLLIDING_BELOW = 2; // abaixo
    static final int COLLIDING_LEFT = 3; // esquerda
    Rectangle2D.Double pos;
    int energia;
    Entidade[] collidingEntities;
    
    public Entidade (int x,int y){
        pos = new Rectangle2D.Double(x,y,1,1);
        this.energia = 1;
        collidingEntities = new Entidade[4];
    }
    
    public abstract void init();
    
    public abstract void update(int currentTick);
    
    public abstract void render(Graphics2D g);
    
}
