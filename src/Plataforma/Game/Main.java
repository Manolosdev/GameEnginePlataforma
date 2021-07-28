/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.Game;

import Plataforma.core.Game;



/**
 *
 * @author Seven
 */
public class Main {
    
    //Criando objeto Game e instanciando com sua classe derivada ArcaideI
    
    public static void main (String[] args){
        Game gameRun = new Jogo();
        gameRun.run();
    }
    
}
