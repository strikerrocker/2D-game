package io.github.strikerrocker.blocks;

import java.awt.image.BufferedImage;

public class SolidBlock extends Block {
    SolidBlock(BufferedImage texture, int id) {
        super(texture, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
