/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.Game;

import Plataforma.core.AudioManager;
import Plataforma.core.InputManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Seven
 */
public class EntidadeJogador extends EntidadeQueMove {

    static final int STATE_STANDING = 0; // Parado
    static final int STATE_WALKING = 1; // Andando
    static final int STATE_JUMPING = 2; // Pulando
    static final int STATE_FALLING = 3; // Caindo
    int state;
    int stepInterval;
    int lastStepTick;
    ArrayList<BufferedImage> sprites;

    public EntidadeJogador(int x, int y) {
        super(x, y, 10);
        stepInterval = 20;
        sprites = new ArrayList();
    }

    @Override
    public void init() {
        // Inicializa o objeto, criando os sprites.
        // Os sprites consistem apenas de imagens com retângulos coloridos.
        BufferedImage img = new BufferedImage(30, 50, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        sprites.add(img);
        img = new BufferedImage(30, 50, BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(Color.green);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        sprites.add(img);
        img = new BufferedImage(30, 50, BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(Color.yellow);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        sprites.add(img);
        img = new BufferedImage(30, 50, BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(Color.orange);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        sprites.add(img);
        // Inicializa o estado com STANDING (parado)
        state = STATE_STANDING;
        // Ajusta o tamanho do corpo de acordo com o tamanho sprite.
        pos.width = sprites.get(state).getWidth();
        pos.height = sprites.get(state).getHeight();
    }

    @Override
    public void update(int currentTick) {
        if (InputManager.getInstance().isPressed(KeyEvent.VK_D)) {
            accel.x = 0.4;
        } else if (InputManager.getInstance().isPressed(KeyEvent.VK_A)) {
            accel.x = -0.4;
        }
        if (InputManager.getInstance().isJustPressed(KeyEvent.VK_SPACE) && collidingEntities[COLLIDING_BELOW] != null) {
            // Se ESPAÇO está pressionado, atribui um Grande valor negativo á aceleração vertical.
            accel.y = -10;
        }
        // Atualiza a velocidade e posição do objeto.
        super.update(currentTick);
        // Muda o estado de acordo com a velocidade resultando.
        if (speed.y < 0) {
            // Se a velocidade vertical é negativa,está subindo
            if (state != STATE_JUMPING) {
                // Se acabou de mudar para o estado JUMPING, executa o som de pulo.
                playSound("jump.wav");
            }
            state = STATE_JUMPING;
        } else if (speed.y > 0) {
            // Se a velocidade vertical é positiva, está subindo.
            state = STATE_FALLING;
        } else if (speed.x != 0) {
            // Se a velocidade horizontal é diferente de zero, está caminhando.
            if (state == STATE_FALLING) {
                // Se o estado anterior era FALLING, executa o som de bater no chão.
                playSound("jumpEnd.wav");
            }
            state = STATE_WALKING;
            if (currentTick - lastStepTick > stepInterval) {
                // a cada "stepInterval" ticks, executa um som de passo.
                playSound("step.wav");
                lastStepTick = currentTick;
            }
        } else {
            // Se a velocidade horizontal é zero, está parado.
            if (state == STATE_FALLING) {
                // Se o estado anterior era FALLING, executa som de bater no chão.
                playSound("jumpEnd.wav");
            }
            state = STATE_STANDING;
        }
    }
    
    @Override
    public void render(Graphics2D g) {
        // Desenha o sprite pego da posição correspondente ao estado atual.
        int y = (int) (pos.y + pos.height) - sprites.get(state).getHeight();
        int x = (int) (pos.x + pos.width / 2) - (sprites.get(state).getWidth() / 2);
        g.drawImage(sprites.get(state), x, y, null);
    }

    public void playSound(String fileName) {
        try {
            AudioManager.getInstance().loadAudio(fileName).play();
        } catch (Exception e) {
        }
    }
}
