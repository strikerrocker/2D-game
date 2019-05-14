package io.github.strikerrocker.world;

import io.github.strikerrocker.gfx.PixelPos;

import java.util.Objects;

public class BlockPos {
    private float x;
    private float y;

    public BlockPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockPos blockPos = (BlockPos) o;
        return Float.compare(blockPos.x, x) == 0 &&
                Float.compare(blockPos.y, y) == 0;
    }

    public PixelPos toPixelPos() {
        return new PixelPos(this);
    }

    public BlockPos intForm() {
        return new BlockPos((int) x, (int) y);
    }

    public BlockPos left() {
        return new BlockPos(x - 1, y);
    }

    public BlockPos right() {
        return new BlockPos(x + 1, y);
    }

    public BlockPos up() {
        return new BlockPos(x, y - 1);
    }

    public BlockPos down() {
        return new BlockPos(x, y + 1);
    }

    @Override
    public String toString() {
        return "BlockPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}