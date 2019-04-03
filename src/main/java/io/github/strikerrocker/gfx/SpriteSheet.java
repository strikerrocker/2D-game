package io.github.strikerrocker.gfx;

import java.awt.image.BufferedImage;

class SpriteSheet {
    private BufferedImage sheet;

    SpriteSheet(BufferedImage sheet) {
        this.sheet = sheet;
    }

    BufferedImage crop(int x, int y, int width, int height) {
        return sheet.getSubimage(x, y, width, height);
    }

}