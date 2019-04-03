package io.github.strikerrocker.blocks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Block {
    /*
     * Tile Width and height in terms of pixels
     */
    public static final int BLOCKWIDTH = 64;
    public static final int BLOCKHEIGHT = 64;
    public static ArrayList<Block> blocks = new ArrayList<>();
    private final int id;
    private BufferedImage texture;

    public Block(BufferedImage texture) {
        this.texture = texture;
        blocks.add(this);
        this.id = blocks.indexOf(this);
    }

    public static Block getFromId(int id) {
        return blocks.get(id);
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
