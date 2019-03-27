package io.github.strikerrocker.items;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.image.BufferedImage;

import static io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public class Item {
    private static Item[] items = new Item[256];
    @Expose
    private final int id;
    protected BufferedImage texture;
    @Expose
    protected String name;
    private boolean isFood = false;
    private int heal = 0;
    private int attackDamage = 1;

    public Item(BufferedImage texture, String name, int id) {
        this.texture = texture;
        this.name = name;
        this.id = id;
        items[id] = this;
    }

    public static Item getFromId(int id) {
        return items[id];
    }

    public void onRightClick(Handler handler, Player player, ItemStack stack, int x, int y) {
        if (isFood() && !(player.getHealth() >= player.maxHealth)) {
            player.setHealth(player.getHealth() + getHeal());
            stack.decSize(1);
        }
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void onLeftClick(Handler handler, Player player, int x, int y) {
        Rectangle pixelBound = player.getPixelCollisionBounds(0, 0).grow(BLOCKWIDTH, BLOCKHEIGHT);
        for (Entity entity : handler.getWorld().getEntityManager().getEntities()) {
            if (!(entity instanceof Player) && entity instanceof Creature && pixelBound.contains(x, y) && entity.getPixelCollisionBounds(0, 0).contains(x, y)
                    && player.getAttackTimer() > player.getAttackCooldown() && !player.getInventory().isActive()) {
                ((Creature) entity).hurt(getAttackDamage());
                player.setAttackTimer(0);
                return;
            }
        }
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
