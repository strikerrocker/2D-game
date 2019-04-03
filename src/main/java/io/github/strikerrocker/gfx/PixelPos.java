package io.github.strikerrocker.gfx;

import io.github.strikerrocker.blocks.Block;
import io.github.strikerrocker.world.BlockPos;

public class PixelPos {
    private float x, y;

    public PixelPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PixelPos(BlockPos blockPos) {
        this.x = blockPos.getX() * Block.BLOCKWIDTH;
        this.y = blockPos.getY() * Block.BLOCKHEIGHT;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(x / Block.BLOCKWIDTH, y / Block.BLOCKHEIGHT);
    }

    @Override
    public String toString() {
        return "PixelPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
