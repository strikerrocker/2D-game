package io.github.strikerrocker.items;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.player.Player;

import java.awt.image.BufferedImage;

public class ItemData {
    private static ItemData[] itemDatas = new ItemData[256];
    @Expose
    private final int id;
    protected BufferedImage texture;
    @Expose
    protected String name;
    private boolean isFood = false;
    private int heal = 0;
    private int attackDamage = 1;

    public ItemData(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        itemDatas[id] = this;
    }

    public static ItemData getFromId(int id) {
        return itemDatas[id];
    }

    public void onRightClick(Handler handler, Player player, Item stack, int x, int y) {
        if (isFood() && !(player.getHealth() >= player.maxHealth)) {
            player.setHealth(player.getHealth() + getHeal());
            stack.decSize(1);
            player.setItemUseTimer(0);
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void onLeftClick(Handler handler, Player player, int x, int y) {
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

    public String getName() {
        return name;
    }

    public void tick() {
    }

    public ItemData setAsFood(int heathRestore) {
        isFood = true;
        this.heal = heathRestore;
        return this;
    }
}