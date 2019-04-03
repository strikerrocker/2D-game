package io.github.strikerrocker.items;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.blocks.Block;
import io.github.strikerrocker.blocks.Blocks;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.world.BlockPos;

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
    private boolean isPick = false;
    private int healAmt = 0;
    private int attackDamage = 1;
    private Block block;

    ItemData(BufferedImage texture, String name) {
        this.texture = texture;
        this.name = name;
        itemData.add(this);
        this.id = itemData.indexOf(this);
    }

    public static ItemData getFromId(int id) {
        return itemData.get(id);
    }

    ItemData setAsPick() {
        isPick = true;
        return this;
    }

    void onRightClick(Handler handler, Player player, Item stack, int x, int y) {
        if (isFood && !(player.getHealth() >= player.maxHealth)) {
            player.setHealth(player.getHealth() + healAmt);
            stack.decSize(1);
            player.setItemUseTimer(0);
        }
        if (handler.getCurrentLevel().isConquered() && isItemBlock()) {
            BlockPos pos = new PixelPos(x, y).toBlockPos();
            handler.getCurrentLevel().setBlock((int) pos.getX(), (int) pos.getY(), block);
            stack.decSize(1);
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
        if (handler.getCurrentLevel().isConquered() && isPick) {
            BlockPos pos = new PixelPos(x, y).toBlockPos();
            handler.getCurrentLevel().setBlock((int) pos.getX(), (int) pos.getY(), Blocks.grass);
        }
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

    ItemData setAsItemBlock(Block block) {
        this.block = block;
        return this;
    }

    public boolean isItemBlock() {
        return block != null;
    }
}