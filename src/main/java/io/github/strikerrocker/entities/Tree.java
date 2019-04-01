package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.items.Item;
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
        super(EntityTypes.tree, handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 1);
    }

    public boolean hasApple() {
        return hasApple;
    }

    public void setHasApple(boolean hasApple) {
        this.hasApple = hasApple;
    }

    @Override
    public void onKilled() {
        handler.getCurrentLevel().getEntityManager().addEntity(new ItemEntity(handler, x, y).setItem(new Item(Items.wood)));
    }

    @Override
    public void hurt(int amt) {
        if (hasApple) {
            handler.getCurrentLevel().getEntityManager().addEntity(new ItemEntity(handler, (float) (x + 0.5), (float) (y + 0.5)).setItem(new Item(Items.apple, new Random().nextInt(2) + 1)));
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

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        object.addProperty("hasApple", hasApple());
        return object;
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        setHasApple(element.getAsJsonObject().get("hasApple").getAsBoolean());
        return this;
    }
}
