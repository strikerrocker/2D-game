package io.github.strikerrocker.items;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.player.Player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ItemData {
    private static ArrayList<ItemData> itemData = new ArrayList<>();
    @Expose
    private final int id;
    @Expose
    private String name;
    private BufferedImage texture;
    private boolean isFood = false;
    private int healAmt = 0;
    private int attackDamage = 1;

    ItemData(BufferedImage texture, String name) {
        this.texture = texture;
        this.name = name;
        itemData.add(this);
        this.id = itemData.indexOf(this);
    }

    public static ItemData getFromId(int id) {
        return itemData.get(id);
    }

    void onRightClick(Handler handler, Player player, Item stack, int x, int y) {
        if (isFood && !(player.getHealth() >= player.maxHealth)) {
            player.setHealth(player.getHealth() + healAmt);
            stack.decSize(1);
            player.setItemUseTimer(0);
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    ItemData setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    void onLeftClick(Handler handler, Player player, int x, int y) {

    }

    public int getId() {
        return id;
    }

    BufferedImage getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public void tick() {
    }

    ItemData setAsFood(int healAmt) {
        isFood = true;
        this.healAmt = healAmt;
        return this;
    }
}