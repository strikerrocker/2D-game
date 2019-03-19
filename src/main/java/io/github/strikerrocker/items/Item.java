package main.java.io.github.strikerrocker.items;

import main.java.io.github.strikerrocker.gfx.Assets;

import java.awt.image.BufferedImage;

public class Item {
    public static Item woodItem = new Item(Assets.wood, "Wood", 0);
    public static Item rockItem = new Item(Assets.rock, "Rock", 1);
    private static Item[] items = new Item[256];
    private final int id;
    protected BufferedImage texture;
    protected String name;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        items[id] = this;
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
}
