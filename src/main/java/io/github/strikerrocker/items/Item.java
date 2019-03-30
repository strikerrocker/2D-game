package io.github.strikerrocker.items;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.player.Player;

import java.awt.image.BufferedImage;

public class Item {
    private static final int MAX_COUNT = 64;
    @Expose
    private ItemData itemData;
    @Expose
    private int count;

    public Item(ItemData itemData, int count) {
        if (count <= 0 || count > MAX_COUNT) {
            System.out.println("test");
            throw new IllegalArgumentException("Count cant be " + count);
        }
        this.itemData = itemData;
        this.count = count;
    }

    public Item(ItemData itemData) {
        this(itemData, 1);
    }

    public int getMaxCount() {
        return MAX_COUNT;
    }

    public ItemData getItemData() {
        return itemData;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int incSize(int count) {
        int toSet = getCount() + count;
        int notSet = 0;
        if (count <= 0) {
            toSet = 0;
            notSet = count;
        } else if (count + getCount() > MAX_COUNT) {
            toSet = MAX_COUNT;
            notSet = count - (MAX_COUNT - getCount());
        } else if (count > MAX_COUNT) {
            toSet = getMaxCount();
            notSet = count - getMaxCount();
        }
        setCount(toSet);
        return notSet;
    }

    public int decSize(int count) {
        int toSet = getCount() - count;
        int notSet = 0;
        if (count <= 0) {
            toSet = 0;
            notSet = count;
        } else if (getCount() - count < 0) {
            toSet = 0;
            notSet = count - getCount();
        } else if (count > MAX_COUNT) {
            toSet = 0;
            notSet = count - getMaxCount();
        }
        setCount(toSet);
        return notSet;
    }

    public void tick() {
        itemData.tick();
    }

    public BufferedImage getTexture() {
        return itemData.getTexture();
    }

    public String getName() {
        return itemData.getName();
    }

    public void onRightClick(Handler handler, Player player, int x, int y) {
        itemData.onRightClick(handler, player, this, x, y);
    }

    public void onLeftClick(Handler handler, Player player, int x, int y) {
        itemData.onLeftClick(handler, player, x, y);
    }
}