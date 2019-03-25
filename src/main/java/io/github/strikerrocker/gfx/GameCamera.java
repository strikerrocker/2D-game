package io.github.strikerrocker.gfx;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.blocks.Block;
import io.github.strikerrocker.entities.Entity;

public class GameCamera {
    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centreOnEntity(Entity entity) {
        setXOffset(entity.getPixelPos().getX() - handler.getWidth() / 2);
        setYOffset(entity.getPixelPos().getY() - handler.getHeight() / 2);
        checkBlankSpace();
    }

    private void checkBlankSpace() {
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > handler.getWorld().getWorldWidth() * Block.BLOCKWIDTH - handler.getWidth()) {
            xOffset = handler.getWorld().getWorldWidth() * Block.BLOCKWIDTH - handler.getWidth();
        }

        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > handler.getWorld().getWorldHeight() * Block.BLOCKHEIGHT - handler.getHeight()) {
            yOffset = handler.getWorld().getWorldHeight() * Block.BLOCKHEIGHT - handler.getHeight();
        }
    }

    public void move(float x, float y) {
        xOffset += x;
        yOffset += y;
    }

    public float getXOffset() {
        return xOffset;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getYOffset() {
        return yOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
