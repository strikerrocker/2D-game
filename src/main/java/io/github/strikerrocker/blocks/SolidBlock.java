package io.github.strikerrocker.blocks;

import java.awt.image.BufferedImage;

public class SolidBlock extends Block {
    SolidBlock(BufferedImage texture) {
        super(texture);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
