package main.java.io.github.strikerrocker.entities;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.items.ItemStack;
import main.java.io.github.strikerrocker.items.Items;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Tree extends Creature {
    private boolean hasApple = false;

    public Tree(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setHealth(1);
    }

    @Override
    public void onKilled() {
        handler.getWorld().getEntityManager().addEntity(new ItemEntity(handler, x, y, new ItemStack(Items.wood)));
    }

    @Override
    public void hurt(int amt) {
        if (hasApple) {
            handler.getWorld().getEntityManager().addEntity(new ItemEntity(handler, x, y, new ItemStack(Items.apple, new Random().nextInt(3))));
        } else
            super.hurt(amt);
    }

    @Override
    protected void initAITasks() {

    }

    @Override
    public BufferedImage getCurrentFrame() {
        return Assets.tree;
    }
}
