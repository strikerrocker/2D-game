package io.github.strikerrocker.entities.player;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.*;
import io.github.strikerrocker.gfx.Animation;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.misc.Rectangle;
import io.github.strikerrocker.states.DeathScreen;
import io.github.strikerrocker.states.State;
import io.github.strikerrocker.world.BlockPos;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Creature {
    protected long lastItemUseTimer, itemUseCooldown = 1500, itemUseTimer = itemUseCooldown;

    private Animation down;
    private Animation up;
    private Animation right;
    private Animation left;
    @Expose
    private Inventory inventory;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, 10);

        down = new Animation(500, Assets.player_down);
        up = new Animation(500, Assets.player_up);
        right = new Animation(500, Assets.player_right);
        left = new Animation(500, Assets.player_left);
        inventory = new Inventory(handler);

        attackCooldown = 500;
        attackTimer = 500;
        speed = 0.07f;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    protected void initAITasks() {

    }

    @Override
    public void tick() {
        super.tick();
        down.tick();
        up.tick();
        right.tick();
        left.tick();
        getInput();
        handler.getGameCamera().centreOnEntity(this);

        checkAttack();
        inventory.tick();
    }

    private void checkAttack() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer > attackCooldown && inventory.isActive()) {

            Rectangle cb = getCollisionBounds(0, 0);
            Rectangle ar = new Rectangle();
            int arSize = 20;
            ar.width = arSize;
            ar.height = arSize;

            if (handler.getKeyManager().attackUp) {
                ar.x = cb.x + cb.width / 2 - arSize / 2;
                ar.y = cb.y - arSize;
            } else if (handler.getKeyManager().attackDown) {
                ar.x = cb.x + cb.width / 2 - arSize / 2;
                ar.y = cb.y + cb.height;
            } else if (handler.getKeyManager().attackLeft) {
                ar.x = cb.x - arSize;
                ar.y = cb.y + cb.height / 2 - arSize / 2;
            } else if (handler.getKeyManager().attackRight) {
                ar.x = cb.x + cb.width;
                ar.y = cb.y + cb.height / 2 - arSize / 2;
            } else {
                return;
            }

            attackTimer = 0;

            for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
                if (e.getCollisionBounds(0, 0).intersects(ar) && !e.equals(this) && e instanceof Creature) {
                    ((Creature) e).hurt(1);
                    return;
                }
            }
        }
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
                handler.getWorld().getEntityManager().addEntity(new Zombie(handler, x, y + 2));
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_H)) {
                setHealth(maxHealth);
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_T)) {
                handler.getWorld().getEntityManager().addEntity(new Tree(handler, x, y + 2));
            }
            if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_B)) {
                switch (handler.getWorld().getName()) {
                    case "world1":
                        handler.setWorld("world2");
                        break;
                    case "world2":
                        handler.setWorld("world1");
                        break;
                }
            }
        }
    }

    private void getMouseInput() {
        itemUseTimer += System.currentTimeMillis() - lastItemUseTimer;
        lastItemUseTimer = System.currentTimeMillis();
        if (handler.getMouseManager().isRightPressed()) {
            if (itemUseTimer > itemUseCooldown) {
                if (inventory.getHotbarItem() != null && inventory.getHotbarItem().getItem().isFood() && !(health >= maxHealth)) {
                    setHealth(getHealth() + inventory.getHotbarItem().getItem().getHeal());
                    inventory.getHotbarItem().decSize(1);
                }
            }
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
        for (ItemStack stack : inventory.getInventoryItems()) {
            BlockPos pos = new PixelPos(x, y).toBlockPos();
            handler.getWorld().getEntityManager().addEntity(new ItemEntity(handler, pos.getX(), pos.getY(), stack));
        }
        State.setCurrentState(new DeathScreen(handler));
    }

    @Override
    public boolean canMove() {
        return !inventory.isActive();
    }
}