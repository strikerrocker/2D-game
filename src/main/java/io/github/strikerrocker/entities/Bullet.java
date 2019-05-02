package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Creature {
    private int damage;

    public Bullet(Handler handler, float x, float y) {
        super(EntityTypes.bullet, handler, x, y, 32, 32, 1);
        bounds = new Rectangle(0, 0, 0.5f, 0.5f);
    }

    public int getDamage() {
        return damage;
    }

    public Bullet setDamage(int damage) {
        this.damage = damage;
        return this;
    }

    @Override
    protected void initAITasks() {

    }

    @Override
    public void tick() {
        super.tick();
        Rectangle attackArea = new Rectangle(x + width, y, 1, 1);
        for (Entity entity : handler.getCurrentLevel().getEntityManager().getEntities()) {
            if (entity != this && entity.getCollisionBounds(0, 0).intersects(attackArea)
                    && !(entity instanceof Portal) && entity instanceof Creature) {
                ((Creature) entity).hurt(damage);
                this.hurt(1);
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
        }
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
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
}
