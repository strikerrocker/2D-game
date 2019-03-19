package main.java.io.github.strikerrocker.gfx;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static main.java.io.github.strikerrocker.Main.game;

public class ImageLoader {
    static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            game.stop();
        }
        return null;
    }
}
