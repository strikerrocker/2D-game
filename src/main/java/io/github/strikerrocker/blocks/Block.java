package main.java.io.github.strikerrocker.blocks;

import main.java.io.github.strikerrocker.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block {
    /*
     * Tile Width and height in terms of pixels
     */
    public static final int BLOCKWIDTH = 64;
    public static final int BLOCKHEIGHT = 64;
    public static Block[] blocks = new Block[256];
    public static Block grass = new Block(Assets.grass, 0);
    public static Block dirt = new Block(Assets.dirt, 1);
    public static Block rock = new SolidBlock(Assets.stone, 2);
    protected final int id;
    protected BufferedImage texture;

    public Block(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;
        blocks[id] = this;
    }

    public void tick() {
    }

    public void render(Graphics graphics, int x, int y) {
        graphics.drawImage(texture, x, y, BLOCKWIDTH, BLOCKHEIGHT, null);
    }

    public boolean isSolid() {
        return false;
    }

    public int getId() {
        return id;
    }
}
