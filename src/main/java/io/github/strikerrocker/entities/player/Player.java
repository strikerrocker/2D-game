package io.github.strikerrocker.entities.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.*;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Animation;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.items.Item;
import io.github.strikerrocker.items.Items;
import io.github.strikerrocker.misc.Rectangle;
import io.github.strikerrocker.misc.Serializers;
import io.github.strikerrocker.states.DeathState;
import io.github.strikerrocker.states.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static io.github.strikerrocker.blocks.Block.BLOCKWIDTH;
import static io.github.strikerrocker.misc.Deserializers.inventoryJsonDeserializer;

public class Player extends Creature {
    private long lastItemUseTimer, itemUseCooldown = 1500, itemUseTimer = itemUseCooldown;
    private Animation down;
    private Animation up;
    private Animation right;
    private Animation left;
    @Expose
    private Inventory inventory;

    private String lvlName;

    public Player(Handler handler, float x, float y) {
        super(EntityTypes.player, handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 10);

        down = new Animation(500, Assets.player_down);
        up = new Animation(500, Assets.player_up);
        right = new Animation(500, Assets.player_right);
        left = new Animation(500, Assets.player_left);
        inventory = new Inventory(handler);

        attackCooldown = 500;
        attackTimer = 500;
        speed = 0.07f;
        lvlName = "level1";
    }

    @Override
    public String toString() {
        return "Player{" +
                "down=" + down +
                ", up=" + up +
                ", right=" + right +
                ", left=" + left +
                ", inventory=" + inventory +
                ", lvlName='" + lvlName + '\'' +
                ", xMove=" + xMove +
                ", yMove=" + yMove +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public void setItemUseTimer(long itemUseTimer) {
        this.itemUseTimer = itemUseTimer;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    protected void initAITasks() {

    }

    private void getInput() {
        xMove = 0;
        yMove = 0;
        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = speed;
        }
        if (handler.getKeyManager().right) {
            xMove = speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
        }
        getHotBarInput();
        getMouseInput();
        if (handler.getGame().isDev()) {
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
                handler.getCurrentLevel().getEntityManager().addEntity(new Zombie(handler, x, y + 2));
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_H)) {
                setHealth(maxHealth);
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_P)) {
                handler.getCurrentLevel().getEntityManager().addEntity(new Portal(handler, x, y));
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_G)) {
                inventory.addStack(new Item(Items.grass));
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
                Bullet bullet = new Bullet(handler, x + 1, y).setDamage(2);
                handler.getCurrentLevel().getEntityManager().addEntity(bullet);
                bullet.move(2);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        down.tick();
        up.tick();
        right.tick();
        left.tick();
        handler.getGameCamera().centreOnEntity(this);

        getInput();
        inventory.tick();
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        Gson gson = new GsonBuilder().registerTypeAdapter(Inventory.class, Serializers.inventoryJsonSerializer).create();
        object.add("inventory", gson.toJsonTree(inventory, Inventory.class));
        object.addProperty("level", lvlName);
        return object;
    }

    private void getMouseInput() {
        itemUseTimer += System.currentTimeMillis() - lastItemUseTimer;
        lastItemUseTimer = System.currentTimeMillis();
        if (handler.getMouseManager().isRightPressed() && itemUseTimer > itemUseCooldown && inventory.getHotbarStack() != null) {
            inventory.getHotbarStack().onRightClick(handler, this, handler.getMouseManager().getX(), handler.getMouseManager().getY());
            itemUseTimer = 0;
        }
        if (handler.getMouseManager().isLeftPressed() && attackTimer > attackCooldown) {
            int attackDamage = inventory.getHotbarStack() != null ? inventory.getHotbarStack().getItemData().getAttackDamage() : 1;
            Rectangle pixelBound = getPixelCollisionBounds(0, 0).grow(BLOCKWIDTH, BLOCKHEIGHT);
            for (Entity entity : handler.getCurrentLevel().getEntityManager().getEntities()) {
                if (!(entity instanceof Player) && entity instanceof Creature && pixelBound.contains(handler.getMouseManager().getX(), handler.getMouseManager().getY())
                        && entity.getPixelCollisionBounds(0, 0).contains(handler.getMouseManager().getX(), handler.getMouseManager().getY())
                        && getAttackTimer() > getAttackCooldown() && !getInventory().isActive()) {
                    ((Creature) entity).hurt(attackDamage);
                    setAttackTimer(0);
                    return;
                }
            }
            if (inventory.getHotbarStack() != null)
                inventory.getHotbarStack().onLeftClick(handler, this, handler.getMouseManager().getX(), handler.getMouseManager().getY());
        }
    }

    private void getHotBarInput() {
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_1)) inventory.setHotBarSelectedItem(0);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_2)) inventory.setHotBarSelectedItem(1);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_3)) inventory.setHotBarSelectedItem(2);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_4)) inventory.setHotBarSelectedItem(3);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_5)) inventory.setHotBarSelectedItem(4);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_6)) inventory.setHotBarSelectedItem(5);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_7)) inventory.setHotBarSelectedItem(6);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_8)) inventory.setHotBarSelectedItem(7);
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_9)) inventory.setHotBarSelectedItem(8);
    }

    public void postRender(Graphics graphics) {
        inventory.renderHotbar(graphics);
        inventory.renderInv(graphics);
    }

    @Override
    public BufferedImage getCurrentFrame() {
        if (xMove < 0) {
            return left.getCurrentFrame();
        } else if (xMove > 0) {
            return right.getCurrentFrame();
        } else if (yMove < 0) {
            return up.getCurrentFrame();
        } else if (yMove > 0) {
            return down.getCurrentFrame();
        } else {
            return Assets.player;
        }
    }

    @Override
    public void onKilled() {
        System.out.println("You Suck");
        for (Item stack : inventory.getInventoryItems()) {
            handler.getCurrentLevel().getEntityManager().addEntity(((ItemEntity) EntityTypes.item.createNew(handler, x, y)).setItem(stack));
        }
        State.setCurrentState(new DeathState(handler));
    }

    @Override
    public boolean canMove() {
        return !inventory.isActive();
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        Gson gson = new GsonBuilder().registerTypeAdapter(Inventory.class, inventoryJsonDeserializer).create();
        setInventory(gson.fromJson(element.getAsJsonObject().get("inventory"), Inventory.class));
        lvlName = element.getAsJsonObject().has("level") ? element.getAsJsonObject().get("level").getAsString() : "level1";
        return this;
    }

    public String getLevel() {
        return lvlName;
    }

    public void setLevel(String lvlName) {
        this.lvlName = lvlName;
    }
}