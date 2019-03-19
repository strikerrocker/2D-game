package main.java.io.github.strikerrocker.gfx;

import main.java.io.github.strikerrocker.world.BlockPos;

import static main.java.io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static main.java.io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public class PixelPos {
    private float x, y;

    public PixelPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PixelPos(BlockPos blockPos) {
        this.x = blockPos.getX() * BLOCKWIDTH;
        this.y = blockPos.getY() * BLOCKHEIGHT;
    }

    public float getYPixel() {
        return y;
    }

    public void setYPixel(float y) {
        this.y = y;
    }

    public float getXPixel() {
        return x;
    }

    public void setXPixel(float x) {
        this.x = x;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(x / BLOCKWIDTH, y / BLOCKHEIGHT);
    }
}
