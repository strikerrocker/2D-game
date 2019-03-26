package io.github.strikerrocker.items;

import java.awt.image.BufferedImage;

public class Item {
    private static Item[] items = new Item[256];
    private final int id;
    protected BufferedImage texture;
    protected String name;
    private boolean isFood = false;
    private int heal = 0;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        items[id] = this;
    }

    public int getHeal() {
        return heal;
    }

    public boolean isFood() {
        return isFood;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void tick() {
    }

    public Item setAsFood(int heathRestore) {
        isFood = true;
        this.heal = heathRestore;
        return this;
    }
}
