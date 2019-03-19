package main.java.io.github.strikerrocker.world;

import main.java.io.github.strikerrocker.gfx.PixelPos;

public class BlockPos {
    private float x, y;

    public BlockPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
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

    @Override
    public String toString() {
        return "BlockPos{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}