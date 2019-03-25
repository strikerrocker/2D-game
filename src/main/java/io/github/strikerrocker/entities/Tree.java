package io.github.strikerrocker.entities;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.items.Items;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Tree extends Creature {
    private boolean hasApple = true;
    private Timer appleGrowTimer = new Timer();

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
            handler.getWorld().getEntityManager().addEntity(new ItemEntity(handler, (float) (x + 0.5), (float) (y + 0.5), new ItemStack(Items.apple, new Random().nextInt(2) + 1)));
            hasApple = false;
        } else
            super.hurt(amt);
    }

    @Override
    protected void initAITasks() {

    }

    @Override
    public void tick() {
        super.tick();
        if (!hasApple) {
            appleGrowTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    hasApple = true;
                }
            }, TimeUnit.MINUTES.toMillis(1));
        }
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return hasApple ? Assets.appleTree : Assets.tree;
    }
}
