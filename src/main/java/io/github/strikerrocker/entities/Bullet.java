package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.image.BufferedImage;

public class Bullet extends Creature {
    private int damage;

    public Bullet(Handler handler, float x, float y) {
        super(EntityTypes.bullet, handler, x, y, 32, 32, 1);
        bounds = new Rectangle(0, 0, 0.5f, 0.5f);
        speed = 0.03f;
    }

    public Bullet setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    protected void initAITasks() {
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        Rectangle attackArea = new Rectangle((float) (x + getCollisionBounds().getWidth()), y, 0.5f, 0.5f);
        for (Entity entity : handler.getCurrentLevel().getEntityManager().getEntities()) {
            if (entity != this && entity.getCollisionBounds().intersects(attackArea)
                    && !(entity instanceof Portal) && entity instanceof Creature && attackTimer > attackCooldown) {
                setAttackTimer(0);
                ((Creature) entity).hurt(damage);
                this.hurt(1);
                return;
            }
        }
        if (xMove > 0) {
            int tx = (int) (x + xMove + bounds.x + bounds.width);
            if (isSolid(tx, (int) ((y + bounds.y))) || isSolid(tx, (int) (y + bounds.y + bounds.height))) {
                this.hurt(1);
            }
        } else if (xMove < 0) {
            int tx = (int) ((x + xMove + bounds.x));
            if (isSolid(tx, (int) (y + bounds.y)) || isSolid(tx, (int) (y + bounds.y + bounds.height))) {
                this.hurt(1);
            }
        } else if (yMove < 0) {
            int ty = (int) (y + yMove + bounds.y);
            if (!isSolid((int) (x + bounds.x), ty) &&
                    !isSolid((int) (x + bounds.x + bounds.width), ty)) {
                this.hurt(1);
            }
        } else if (yMove > 0) {
            int ty = (int) (y + yMove + bounds.y + bounds.height);
            if (!isSolid((int) (x + bounds.x), ty) &&
                    !isSolid((int) (x + bounds.x + bounds.width), ty)) {
                this.hurt(1);
            }
        }
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return Assets.bullet;
    }

    @Override
    public void onKilled() {
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        object.addProperty("damageDealt", damage);
        return object;
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        damage = element.getAsJsonObject().get("damageDealt").getAsInt();
        return this;
    }

    /**
     * @param side 1:up ,2:right ,3:down ,4:left
     */
    public void move(int side) {
        switch (side) {
            case 1:
                yMove = -speed;
                break;
            case 2:
                xMove = speed;
                break;
            case 3:
                yMove = speed;
                break;
            case 4:
                xMove = -speed;
                break;
            default:
                break;
        }
    }
}
