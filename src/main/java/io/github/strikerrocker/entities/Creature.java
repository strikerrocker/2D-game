package main.java.io.github.strikerrocker.entities;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.Utils;
import main.java.io.github.strikerrocker.entities.ai.AI;
import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.gfx.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static main.java.io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static main.java.io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public abstract class Creature extends Entity {
    public static final int DEFAULT_HEALTH = 10;
    public static final int DEFAULT_WIDTH = 64;
    public static final int DEFAULT_HEIGHT = 64;
    public static final float DEFAULT_SPEED = 2.0f;
    private static final Timer renderHurtTimer = new Timer();
    public boolean renderHurt = false;
    protected int health;
    protected float speed;
    protected float xMove, yMove;
    protected long lastAttackTimer, attackCooldown = 1500, attackTimer = attackCooldown;
    protected Map<AI, Integer> aiTasks = new HashMap<>();

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        this.health = DEFAULT_HEALTH;
        this.speed = DEFAULT_SPEED;
        bounds = new Rectangle(16, 32, 32, 32);
        initAITasks();
    }

    protected abstract void initAITasks();

    @Override
    public void tick() {
        move();
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        Utils.sortByValue(aiTasks);
        for (AI ai : aiTasks.keySet()) {
            if (ai.canExecute(this)) {
                ai.execute(this);
                return;
            }
        }
    }

    public void hurt(int amt) {
        health -= amt;
        renderHurt = true;
        if (health <= 0) {
            renderHurt = false;
            active = false;
            onKilled();
        }
    }

    public long getAttackCooldown() {
        return attackCooldown;
    }

    public long getAttackTimer() {
        return attackTimer;
    }

    public void setAttackTimer(long attackTimer) {
        this.attackTimer = attackTimer;
    }

    @Override
    public void render(Graphics graphics) {
        BufferedImage toBeRendered = renderHurt ? Utils.tintRed(getCurrentFrame()) : getCurrentFrame();
        if (renderHurt) {
            renderHurtTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    renderHurt = false;
                }
            }, 500);
        }
        graphics.drawImage(toBeRendered, (int) (x - handler.getGameCamera().getXOffset()), (int) (y - handler.getGameCamera().getYOffset()), width, height, null);
        if (!(this instanceof Player) && renderHurt)
            Text.drawString(graphics, "" + getHealth(), (int) (getCollisionBounds(0, 0).getCenterX()), (int) (getCollisionBounds(0, 0).getY() - 10), true, Color.WHITE, Assets.font28);
        //graphics.setColor(Color.RED);
        //graphics.fillRect((int) (x + bounds.x - handler.getGameCamera().getXOffset()), (int) (y + bounds.y - handler.getGameCamera().getYOffset()), bounds.width, bounds.height);
    }

    public abstract BufferedImage getCurrentFrame();

    public abstract void onKilled();

    public void move() {
        if (!entityColliding(xMove, 0))
            moveX();
        if (!entityColliding(0, yMove))
            moveY();
    }

    public void moveX() {
        if (xMove > 0) {
            int tx = (int) ((x + xMove + bounds.x + bounds.width) / BLOCKWIDTH);
            if (!blockCollision(tx, (int) ((y + bounds.y) / BLOCKHEIGHT)) && !blockCollision(tx, (int) ((y + bounds.y + bounds.height) / BLOCKHEIGHT))) {
                x += xMove;
            } else {
                x = tx * BLOCKWIDTH - bounds.x - bounds.width - 1;
            }
        } else if (xMove < 0) {
            int tx = (int) ((x + xMove + bounds.x) / BLOCKWIDTH);
            if (!blockCollision(tx, (int) ((y + bounds.y) / BLOCKHEIGHT)) && !blockCollision(tx, (int) ((y + bounds.y + bounds.height) / BLOCKHEIGHT))) {
                x += xMove;
            } else {
                x = tx * BLOCKWIDTH + BLOCKWIDTH - bounds.x;
            }
        }
    }

    public void moveY() {
        if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y) / BLOCKHEIGHT;

            if (!blockCollision((int) (x + bounds.x) / BLOCKWIDTH, ty) &&
                    !blockCollision((int) (x + bounds.x + bounds.width) / BLOCKWIDTH, ty)) {
                y += yMove;
            } else {
                y = ty * BLOCKHEIGHT + BLOCKHEIGHT - bounds.y;
            }

        } else if (yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height) / BLOCKHEIGHT;

            if (!blockCollision((int) (x + bounds.x) / BLOCKWIDTH, ty) &&
                    !blockCollision((int) (x + bounds.x + bounds.width) / BLOCKWIDTH, ty)) {
                y += yMove;
            } else {
                y = ty * BLOCKHEIGHT - bounds.y - bounds.height - 1;
            }

        }
    }

    private boolean blockCollision(int x, int y) {
        return handler.getWorld().getBlock(x, y).isSolid();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setXMove(float xMove) {
        this.xMove = xMove;
    }

    public void setYMove(float yMove) {
        this.yMove = yMove;
    }
}
