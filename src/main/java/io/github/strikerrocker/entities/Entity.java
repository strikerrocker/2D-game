package io.github.strikerrocker.entities;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.world.BlockPos;

import java.awt.*;

public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Handler handler;
    protected Rectangle bounds;
    protected boolean active = true;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        PixelPos pos = new BlockPos(x, y).toPixelPos();
        this.x = pos.getXPixel();
        this.y = pos.getYPixel();
        this.width = width;
        this.height = height;

        bounds = new Rectangle(0, 0, width, height);
    }

    public BlockPos getPos() {
        return new PixelPos(x, y).toBlockPos();
    }

    public void setPos(PixelPos pos) {
        this.x = pos.getXPixel();
        this.y = pos.getYPixel();
    }

    public void setPos(BlockPos pos) {
        PixelPos pos1 = pos.toPixelPos();
        this.x = pos1.getXPixel();
        this.y = pos1.getYPixel();
    }

    public PixelPos getPixelPos() {
        return new PixelPos(x, y);
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public boolean entityColliding(float xOffset, float yOffset) {
        for (Entity entity : handler.getWorld().getEntityManager().getEntities()) {
            if (entity != this && entity.getCollisionBounds(1, 1).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public boolean entityCollidingExceptPlayer(float xOffset, float yOffset) {
        for (Entity entity : handler.getWorld().getEntityManager().getEntities()) {
            if (entity != this && !(entity instanceof Player) && entity.getCollisionBounds(1, 1).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void tick();

    public abstract void render(Graphics graphics);

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Handler getHandler() {
        return handler;
    }
}
