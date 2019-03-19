package main.java.io.github.strikerrocker.gfx;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.entities.Entity;

import static main.java.io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static main.java.io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public class GameCamera {
    private Handler handler;
    private float xOffset, yOffset;

    public GameCamera(Handler handler, float xOffset, float yOffset) {
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void centreOnEntity(Entity entity) {
        setXOffset(entity.getX() - handler.getWidth() / 2);
        setYOffset(entity.getY() - handler.getHeight() / 2);
        checkBlankSpace();
    }

    private void checkBlankSpace() {
        if (xOffset < 0) {
            xOffset = 0;
        } else if (xOffset > handler.getWorld().getWidth() * BLOCKWIDTH - handler.getWidth()) {
            xOffset = handler.getWorld().getWidth() * BLOCKWIDTH - handler.getWidth();
        }

        if (yOffset < 0) {
            yOffset = 0;
        } else if (yOffset > handler.getWorld().getHeight() * BLOCKHEIGHT - handler.getHeight()) {
            yOffset = handler.getWorld().getHeight() * BLOCKHEIGHT - handler.getHeight();
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
