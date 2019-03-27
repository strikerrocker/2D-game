package io.github.strikerrocker.entities;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.image.BufferedImage;

public class ItemEntity extends Creature {
    private ItemStack stack;

    public ItemEntity(Handler handler, float x, float y, ItemStack stack) {
        super(handler, x, y, 32, 32, 1);
        bounds = new Rectangle(0, 0, 0.5f, 0.5f);
        this.stack = stack;
    }

    @Override
    protected void initAITasks() {
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return stack.getTexture();
    }

    @Override
    public void onKilled() {
        handler.getWorld().getEntityManager().getPlayer().getInventory().addStack(getStack());
    }

    public ItemStack getStack() {
        return stack;
    }
}