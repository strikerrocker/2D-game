package main.java.io.github.strikerrocker.entities;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.items.Item;
import main.java.io.github.strikerrocker.items.ItemStack;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tree extends Creature {

    public Tree(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setHealth(1);
    }

    @Override
    public void die() {
        handler.getWorld().getEntityManager().addEntity(new ItemEntity(handler, x, y, new ItemStack(Item.woodItem)));
    }

    @Override
    public void render(Graphics graphics) {
        /**if (renderHurt) {
         renderHurt = false;
         graphics.drawImage(Utils.tintRed(Assets.tree), (int) (x - handler.getGameCamera().getXOffset()), (int) (y - handler.getGameCamera().getYOffset()), width, height, null);
         } else {
         graphics.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getXOffset()), (int) (y - handler.getGameCamera().getYOffset()), width, height, null);
         }*/
        super.render(graphics);
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return Assets.tree;
    }
}
