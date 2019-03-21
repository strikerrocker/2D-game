package main.java.io.github.strikerrocker.entities;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.items.ItemStack;

import java.awt.image.BufferedImage;

public class ItemEntity extends Creature {
    private ItemStack stack;

    public ItemEntity(Handler handler, float x, float y, ItemStack stack) {
        super(handler, x, y, 32, 32);
        this.stack = stack;
        setHealth(1);
    }

    @Override
    protected void initAITasks() {

    }

    @Override
    public BufferedImage getCurrentFrame() {
        return stack.getTexture();
    }


    @Override
    public void onKilled() {
        stack.setCount(stack.getCount() + 10);
        handler.getWorld().getEntityManager().getPlayer().getInventory().addItem(stack);
    }

    public ItemStack getStack() {
        return stack;
    }
}