/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.core;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Seven
 */
public class ImageManager {

    static private ImageManager instance;
    private HashMap<String, BufferedImage> images;

    private ImageManager() {
        images = new HashMap<String, BufferedImage>();
    }

    public static ImageManager getInstance() {
        if (instance == null) {
            instance = new ImageManager();
        }
        return instance;
    }

    public BufferedImage loadImage(String fileName) throws IOException {
        URL url = getClass().getResource("/" + fileName);
        if (url == null) {
            throw new RuntimeException("A imagem /" + fileName + " não foi encontrada.");
        } else {
            String path = url.getPath();
            if (images.containsKey(path)) {
                return images.get(path);
            } else {
                BufferedImage img = ImageIO.read(url);
                images.put(path, img);
                return img;
            }
        }
    }

    public BufferedImage loadImage(String fileName, int x, int y, int w, int h) throws IOException {
        BufferedImage sheet = loadImage(fileName);
        BufferedImage img = sheet.getSubimage(x, y, w, h);
        return img;
    }

    public BufferedImage flipImage(BufferedImage image, boolean flipHorizontal, boolean flipVertical) {
        int w = 32;
        int h = 48;
        BufferedImage img = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(w, h, BufferedImage.BITMASK);
        if (flipHorizontal) {
            if (flipVertical) {
                img.getGraphics().drawImage(image, w, -h, -w, -h, null);
            } else {
                img.getGraphics().drawImage(image, w, 0, -w, h, null);
            }
        } else if (flipVertical) {
            img.getGraphics().drawImage(image, 0, h, w, -h, null);
        } else {
            img.getGraphics().drawImage(image, 0, 0, w, h, null);
        }
        return img;
    }

    public SpriteAnimation loadAnimatedSprite(String fileName, int qtdFrames) throws IOException {
        BufferedImage sheet = loadImage(fileName);
        if (sheet.getWidth() % qtdFrames != 0) {
            throw new RuntimeException("A imagem /" + fileName + " não possui " + qtdFrames + " sprites de mesmo tamanho.");
        } else {
            SpriteAnimation animation = new SpriteAnimation();
            int w = sheet.getWidth() / qtdFrames;
            int h = sheet.getHeight() / qtdFrames;
            for (int i = 0; i < qtdFrames; i++) {
                animation.addImage(sheet.getSubimage(i * w, 0, w, h));
            }
            return animation;
        }
    }

    public SpriteAnimation loadAnimatedSprite(String fileName, int qtdFrames, int y) throws IOException {
        BufferedImage sheet = loadImage(fileName);
        if (sheet.getWidth() % qtdFrames != 0) {
            throw new RuntimeException("A imagem /" + fileName + " não possui " + qtdFrames + " sprites de mesmo tamanho.");
        } else {
            SpriteAnimation animation = new SpriteAnimation();
            int w = sheet.getWidth() / qtdFrames;
            int h = sheet.getHeight() / qtdFrames;
            for (int i = 0; i < qtdFrames; i++) {
                animation.addImage(sheet.getSubimage(i * w, y, w, h));
            }
            return animation;
        }
    }

}
