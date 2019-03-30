package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
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
    public static final int DEFAULT_HEALTH = 10;
    public static final int DEFAULT_WIDTH = 64;
    public static final int DEFAULT_HEIGHT = 64;
    public static final float DEFAULT_SPEED = 0.03f;
    private static final Timer renderHurtTimer = new Timer();
    public int maxHealth;
    public boolean renderHurt = false;
    @Expose
    protected int health;
    protected float speed;
    @Expose
    protected float xMove, yMove;
    protected long lastAttackTimer, attackCooldown = 1500, attackTimer = attackCooldown;
    protected Map<AI, Integer> aiTasks;

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
        attackTimer += System.currentTimeMillis() - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        sortByValue(aiTasks);
        if (aiTasks != null) {
            for (AI ai : aiTasks.keySet()) {
                if (ai.canExecute(this)) {
                    ai.execute(this);
                    return;
                }
            }
        }
    }

    public boolean canMove() {
        return true;
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
        BufferedImage toBeRendered = renderHurt ? tintRed(getCurrentFrame()) : getCurrentFrame();
        if (renderHurt) {
            renderHurtTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    renderHurt = false;
                }
            }, 500);
        }
        //getPixelCollisionBounds(0, 0).draw(graphics);
        PixelPos pos = getPixelPos();
        graphics.drawImage(toBeRendered, (int) (pos.getX() - handler.getGameCamera().getXOffset()), (int) (pos.getY() - handler.getGameCamera().getYOffset()), (int) width, (int) height, null);
        if (!(this instanceof Player) && renderHurt) {
            Rectangle collisionBound = getPixelCollisionBounds(0, 0);
            Text.drawString(graphics, "" + getHealth(), (int) (collisionBound.getCenterX()), (int) (collisionBound.getY()), true, Color.WHITE, Assets.font28);
        }
    }

    public abstract BufferedImage getCurrentFrame();

    public abstract void onKilled();

    protected void move() {
        if (!entityColliding(xMove, 0))
            moveX();
        if (!entityColliding(0, yMove))
            moveY();
    }

    private void moveX() {
        if (xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width);
            if (!blockCollision(tx, (int) ((y + bounds.y))) && !blockCollision(tx, (int) (y + bounds.y + bounds.height))) {
                x += xMove;
            } else {
                x += tx - Math.floor(tx);
            }
        } else if (xMove < 0) {
            int tx = (int) ((x + xMove + bounds.x));
            if (!blockCollision(tx, (int) (y + bounds.y)) && !blockCollision(tx, (int) (y + bounds.y + bounds.height))) {
                x += xMove;
            } else {
                x = tx + bounds.x + bounds.width;
            }
        }
    }

    private void moveY() {
        if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y);
            if (!blockCollision((int) (x + bounds.x), ty) &&
                    !blockCollision((int) (x + bounds.x + bounds.width), ty)) {
                y += yMove;
            } else {
                y = ty + bounds.y;
            }
        } else if (yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height);
            if (!blockCollision((int) (x + bounds.x), ty) &&
                    !blockCollision((int) (x + bounds.x + bounds.width), ty)) {
                y += yMove;
            } else {
                y += ty - Math.floor(ty);
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
}
