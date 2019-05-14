package io.github.strikerrocker.entities.pathfinding;

import io.github.strikerrocker.world.BlockPos;

class PathStep {
    private int g, h;
    private BlockPos pos;
    private PathStep parent;

    PathStep(BlockPos pos) {
        this.pos = pos;
        this.g = 0;
        this.h = 0;
    }

    int getG() {
        return g;
    }

    void setG(int g) {
        this.g = g;
    }

    private int getH() {
        return h;
    }

    void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return getG() + getH();
    }

    BlockPos getPos() {
        return pos;
    }

    PathStep getParent() {
        return parent;
    }

    void setParent(PathStep parent) {
        this.parent = parent;
    }
}