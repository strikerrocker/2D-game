package io.github.strikerrocker.entities;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.image.BufferedImage;

public class ItemEntity extends Creature {
    @Expose
    private ItemStack stack;

    public ItemEntity(Handler handler, float x, float y) {
        super(EntityTypes.item, handler, x, y, 32, 32, 1);
        bounds = new Rectangle(0, 0, 0.5f, 0.5f);
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

    public ItemEntity setStack(ItemStack stack) {
        this.stack = stack;
        return this;
    }
}