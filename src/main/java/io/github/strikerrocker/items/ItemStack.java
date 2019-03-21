package main.java.io.github.strikerrocker.items;

import java.awt.image.BufferedImage;

public class ItemStack {
    private static final int MAX_COUNT = 64;
    private Item item;
    private int count;

    public ItemStack(Item item, int count) {
        if (count <= 0 || count > MAX_COUNT) {
            System.out.println("test");
            throw new IllegalArgumentException("Count cant be " + count);
        }
        this.item = item;
        this.count = count;
    }

    public ItemStack(Item item) {
        this(item, 1);
    }

    public int getMaxCount() {
        return MAX_COUNT;
    }

    public Item getItem() {
        return item;
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
        item.tick();
    }

    public BufferedImage getTexture() {
        return item.getTexture();
    }

    public String getName() {
        return item.getName();
    }
}