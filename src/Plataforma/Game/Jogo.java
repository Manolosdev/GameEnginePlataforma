/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.Game;

import Plataforma.core.AudioManager;
import Plataforma.core.DataManager;
import Plataforma.core.Game;
import Plataforma.core.InputManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Seven
 */
public class Jogo extends Game {

    Entidade jogador;
    Point rolagem;
    Dimension mundo;

    ArrayList<Entidade> entidades;
    CollisionDetector collisionDetector;
    boolean pause;
    String musica;
    String nextLevel;
    int transicao;

    public Jogo() {
        entidades = new ArrayList<Entidade>();
        collisionDetector = new CollisionDetector(entidades);
        pause = true;
        rolagem = new Point(0, 0);
        mundo = new Dimension();
    }

    @Override
    public void onLoad() {

        // Inicializa a fase.
        // Inclui o objeto do jogador (que inicia na posição (300,300)
        jogador = new EntidadeJogador(0, 0);
        nextLevel = "level1";
    }

    @Override
    public void onUpdate(int currentTick) {
        if (nextLevel != null) {
            transicao++;
            if (transicao > 100) {
                carregaArquivo(nextLevel);
                nextLevel = null;
                transicao = 0;
            }
        } else {
            if (!pause && transicao == 0) {
                for (Entidade e : entidades) {
                    e.update(currentTick);
                    if (e.pos.y > mundo.height || e.pos.x > mundo.width) {
                        e.pos.x = 100;
                        e.pos.y = 0;
                    }
                }
                collisionDetector.update(currentTick);
            }
            if (InputManager.getInstance().isPressed(KeyEvent.VK_ESCAPE)) {
                terminate();
            } else if (InputManager.getInstance().isJustPressed(KeyEvent.VK_ENTER)) {
                pause = !pause;
            }
            rolagem.x = (int) jogador.pos.x - getWidth() / 2;
            rolagem.y = (int) jogador.pos.y - getHeight() / 2;
            if (rolagem.x < 0) {
                rolagem.x = 0;
            } else if (rolagem.x > mundo.width - getWidth()) {
                rolagem.x = mundo.width - getWidth();
            }
            if (rolagem.y < 0) {
                rolagem.y = 0;
            } else if (rolagem.y > mundo.height - getHeight()) {
                rolagem.y = mundo.height - getHeight();
            }
        }
    }

    @Override
    public void onRender(Graphics2D g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(-rolagem.x, -rolagem.y);
        for (Entidade e : entidades) {
            e.render(g2);
        }
        if (transicao > 0) {
            g.setColor(Color.black);
            g.fillRect(0, 0, getWidth(), getHeight() / 100 * transicao);
        }

        if (pause) {
            g.setColor(Color.darkGray);
            g.fillRect(getWidth() / 2 - 200, getHeight() / 3, 380, 250);
            g.setColor(Color.white);
            g.drawRoundRect(getWidth() / 2 - 200, getHeight() / 3, 380, 250, 5, 5);
            g.setFont(new Font("", Font.BOLD, 20));
            g.drawString("Jogo Pausado", getWidth() / 2 - 80, getHeight() / 3 + 30);
            g.setFont(new Font("", Font.BOLD, 12));
            g.drawString("Esquerda                      [A]", getWidth() / 2 - 80, getHeight() / 3 + 60);
            g.drawString("Direita                            [D]", getWidth() / 2 - 80, getHeight() / 3 + 90);
            g.drawString("Pular                 [ESPAÇO]", getWidth() / 2 - 80, getHeight() / 3 + 120);
            g.drawString("Pausar                   [Enter]", getWidth() / 2 - 80, getHeight() / 3 + 150);
            g.drawString("Sair                            [Esc]", getWidth() / 2 - 80, getHeight() / 3 + 210);
        }
    }

    @Override
    public void onUnload() {
        try {
            if (musica != null) {
                AudioManager.getInstance().loadAudio(musica).stop();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao parar musica!\nERRO: " + e);
        }
    }

    public void carregaArquivo(String fileName) {
        try {
            DataManager dm = new DataManager(getClass().getResource("/level/" + fileName).toURI());
            entidades.clear();
            entidades.add(jogador);
            jogador.init();
            jogador.pos.x = 100;
            jogador.pos.y = 0;
            mundo.setSize(getWidth() + 10, getHeight() + 10);
            if (musica != null) {
                AudioManager.getInstance().loadAudio(musica).stop();
            }
            musica = dm.read("musica", musica);
            if (musica != null) {
                AudioManager.getInstance().loadAudio(musica).loop();
            }
            int qtd = 0;
            qtd = dm.read("plataformas", qtd);
            for (int i = 0; i < qtd; i++) {
                String fn = null;
                fn = dm.read("plataforma." + i + ".levelFileName", fn);
                Entidade e = null;
                if (fn == null) {
                    e = new EntidadePlataforma(0, 0, 0, 0);
                } else {
                    e = new EntidadeLink(0, 0, 0, 0, fn,this);
                }
                e.pos.x = dm.read("plataforma." + i + ".x", (int) e.pos.x);
                e.pos.y = dm.read("plataforma." + i + ".y", (int) e.pos.y);
                e.pos.width = dm.read("plataforma." + i + ".width", (int) e.pos.width);
                e.pos.height = dm.read("plataforma." + i + ".height", (int) e.pos.height);
                e.init();
                entidades.add(e);
                if (mundo.width < e.pos.x + e.pos.width) {
                    mundo.width = (int) (e.pos.x + e.pos.width);
                }
                if (mundo.height < e.pos.y + e.pos.height) {
                    mundo.height = (int) (e.pos.y + e.pos.height);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar fase!\nERRO: " + e);
            System.exit(0);
        }
    }
    
    public void setNextLevel(String fileName){
        this.nextLevel = fileName;
    }
}
