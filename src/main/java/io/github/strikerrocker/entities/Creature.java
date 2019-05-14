package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.ai.AI;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.entities.type.EntityType;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static io.github.strikerrocker.misc.Utils.sortByValue;
import static io.github.strikerrocker.misc.Utils.tintRed;

public abstract class Creature extends Entity {
    protected static final int DEFAULT_WIDTH = 64;
    protected static final int DEFAULT_HEIGHT = 64;
    static final int DEFAULT_HEALTH = 10;
    private static final float DEFAULT_SPEED = 0.03f;
    private static final Timer renderHurtTimer = new Timer();
    public int maxHealth;
    protected float speed;
    protected float xMove;
    protected float yMove;
    protected long lastAttackTimer;
    protected long attackCooldown = 1500;
    protected long attackTimer = attackCooldown;
    Map<AI, Integer> aiTasks;
    private boolean renderHurt = false;
    private int health;

    public Creature(EntityType type, Handler handler, float x, float y, int width, int height, int maxHealth) {
        super(type, handler, x, y, width, height);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.speed = DEFAULT_SPEED;
        bounds = new Rectangle(0.25f, 0.5f, 0.5f, 0.5f);
        aiTasks = new HashMap<>();
        initAITasks();
    }

    protected abstract void initAITasks();

    @Override
    public void tick() {
        if (canMove())
            move();
        if (health <= 0) {
            active = false;
            renderHurt = false;
            onKilled();
        }
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (aiTasks != null) {
            aiTasks = sortByValue(aiTasks);
            for (AI ai : aiTasks.keySet()) {
                if (ai.canExecute(this)) {
                    ai.execute(this);
                    return;
                }
            }
        }
    }

    public boolean canMove() {
        return false;
    }

    public void hurt(int amt) {
        health -= amt;
        renderHurt = true;
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
        BufferedImage toBeRendered = renderHurt ? tintRed(getCurrentFrame()) : getCurrentFrame();
        if (renderHurt) {
            renderHurtTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    renderHurt = false;
                }
            }, 500);
        }
        PixelPos pos = getPixelPos();
        graphics.drawImage(toBeRendered, (int) (pos.getX() - handler.getGameCamera().getXOffset()), (int) (pos.getY() - handler.getGameCamera().getYOffset()), (int) width, (int) height, null);
        if (!(this instanceof Player) && renderHurt) {
            Rectangle collisionBound = getPixelCollisionBounds(0, 0);
            Text.drawString(graphics, "" + getHealth(), (int) (collisionBound.getCenterX()), (int) (collisionBound.getY()), true, Color.WHITE, Assets.font28);
        }
    }

    public abstract BufferedImage getCurrentFrame();

    public abstract void onKilled();

    private void move() {
        if (!hasEntityCollision(xMove, 0))
            moveX();
        if (!hasEntityCollision(0, yMove))
            moveY();
    }

    private void moveX() {
        if (xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width);
            if (!isSolid(tx, (int) (y + bounds.y)) && !isSolid(tx, (int) (y + bounds.y + bounds.height))) {
                x += xMove;
            } else {
                x += tx - Math.floor(tx);
            }
        } else if (xMove < 0) {
            int tx = (int) (x + xMove + bounds.x);
            if (!isSolid(tx, (int) (y + bounds.y)) && !isSolid(tx, (int) (y + bounds.y + bounds.height))) {
                x += xMove;
            } else {
                x = tx + bounds.x + bounds.width;
            }
        }
    }

    private void moveY() {
        if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y);
            if (!isSolid((int) (x + bounds.x), ty) &&
                    !isSolid((int) (x + bounds.x + bounds.width), ty)) {
                y += yMove;
            } else {
                y = ty + bounds.y;
            }
        } else if (yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height);
            if (!isSolid((int) (x + bounds.x), ty) &&
                    !isSolid((int) (x + bounds.x + bounds.width), ty)) {
                y += yMove;
            } else {
                y += ty - Math.floor(ty);
            }
        }
    }

    public boolean isSolid(int x, int y) {
        return handler.getCurrentLevel().getBlock(x, y).isSolid();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health >= maxHealth) this.health = maxHealth;
        else this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setXMove(float xMove) {
        this.xMove = xMove;
    }

    public void setYMove(float yMove) {
        this.yMove = yMove;
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        object.addProperty("health", health);
        object.addProperty("xMove", xMove);
        object.addProperty("yMove", yMove);
        return object;
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        JsonObject object = element.getAsJsonObject();
        this.xMove = object.get("xMove").getAsFloat();
        this.yMove = object.get("yMove").getAsFloat();
        this.health = object.get("health").getAsInt();
        return this;
    }

    public boolean isHostile() {
        return false;
    }
}
