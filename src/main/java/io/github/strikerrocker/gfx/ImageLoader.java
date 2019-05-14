package io.github.strikerrocker.gfx;

import io.github.strikerrocker.Handler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
    private ImageLoader() {
    }

    static BufferedImage loadImage(String path, Handler handler) {
        try {
            return ImageIO.read(ImageLoader.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            handler.getGame().stop();
        }
        return null;
    }
}