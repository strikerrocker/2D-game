package io.github.strikerrocker.entities.pathfinding;

import io.github.strikerrocker.world.BlockPos;

public class PathStep {
    private int g, h;
    private BlockPos pos;
    private PathStep parent;

    public PathStep(BlockPos pos) {
        this.pos = pos;
        this.parent = null;
        this.g = 0;
        this.h = 0;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return getG() + getH();
    }

    public BlockPos getPos() {
        return pos;
    }

    public PathStep getParent() {
        return parent;
    }

    public void setParent(PathStep parent) {
        this.parent = parent;
    }
}
