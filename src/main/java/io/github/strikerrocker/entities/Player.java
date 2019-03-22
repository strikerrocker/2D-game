package io.github.strikerrocker.entities;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.Inventory;
import io.github.strikerrocker.gfx.Animation;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.world.BlockPos;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private Animation down;
    private Animation up;
    private Animation right;
    private Animation left;
    private Inventory inventory;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        down = new Animation(500, Assets.player_down);
        up = new Animation(500, Assets.player_up);
        right = new Animation(500, Assets.player_right);
        left = new Animation(500, Assets.player_left);
        inventory = new Inventory(handler);
        attackCooldown = 500;
        attackTimer = 500;
        setSpeed(3.0f);
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
        if (!inventory.isActive())
            move();
        handler.getGameCamera().centreOnEntity(this);

        checkAttack();
        inventory.tick();
    }

    private void checkAttack() {
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer > attackCooldown && !inventory.isActive()) {

            Rectangle cb = getCollisionBounds(0, 0);
            Rectangle ar = new Rectangle();
            int arSize = 20;
            ar.width = arSize;
            ar.height = arSize;

            if (handler.getKeyManager().aup) {
                ar.x = cb.x + cb.width / 2 - arSize / 2;
                ar.y = cb.y - arSize;
            } else if (handler.getKeyManager().adown) {
                ar.x = cb.x + cb.width / 2 - arSize / 2;
                ar.y = cb.y + cb.height;
            } else if (handler.getKeyManager().aleft) {
                ar.x = cb.x - arSize;
                ar.y = cb.y + cb.height / 2 - arSize / 2;
            } else if (handler.getKeyManager().aright) {
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
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_X)) {
            BlockPos pos = new PixelPos(x, y).toBlockPos();
            handler.getWorld().getEntityManager().addEntity(new Zombie(handler, pos.getX(), pos.getY() + 2));
        }
        if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_H)) {
            setHealth(10);
        }
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        //graphics.setColor(Color.RED);
        //graphics.fillRect((int) (x + bounds.x - handler.getGameCamera().getXOffset()), (int) (y + bounds.y - handler.getGameCamera().getYOffset()), bounds.width, bounds.height);
    }

    public void postRender(Graphics graphics) {
        inventory.render(graphics);
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
    }
}