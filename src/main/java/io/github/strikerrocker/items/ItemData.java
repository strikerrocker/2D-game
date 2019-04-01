package io.github.strikerrocker.items;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.blocks.Block;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.world.BlockPos;

import java.awt.image.BufferedImage;

public class ItemData {
    private static ItemData[] itemData = new ItemData[256];
    @Expose
    private final int id;
    @Expose
    private String name;
    private BufferedImage texture;
    private boolean isFood = false;
    private boolean isPickaxe = false;
    private int heal = 0;
    private int attackDamage = 1;
    private Block block;

    public ItemData(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        itemData[id] = this;
    }

    public static ItemData getFromId(int id) {
        return itemData[id];
    }

    public boolean isPickaxe() {
        return isPickaxe;
    }

    public ItemData setAsPickaxe(boolean pickAxe) {
        isPickaxe = pickAxe;
        return this;
    }

    public void onRightClick(Handler handler, Player player, Item stack, int x, int y) {
        if (isFood() && !(player.getHealth() >= player.maxHealth)) {
            player.setHealth(player.getHealth() + getHeal());
            stack.decSize(1);
            player.setItemUseTimer(0);
        }
        if (isItemBlock()) {
            BlockPos pos = new PixelPos(x, y).toBlockPos();
            handler.getCurrentLevel().setBlock((int) pos.getX(), (int) pos.getY(), block);
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

    public ItemData setAsItemBlock(Block block) {
        this.block = block;
        return this;
    }

    public boolean isItemBlock() {
        return block != null;
    }
}