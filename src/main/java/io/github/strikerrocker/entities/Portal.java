package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Portal extends Creature {
    private String target;

    public Portal(Handler handler, float x, float y) {
        super(EntityTypes.portal, handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT * 2, 1);
        bounds = new Rectangle(0, 0, 1, 2);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    protected void initAITasks() {
    }

    @Override
    public void tick() {
        super.tick();
        if (handler.getCurrentLevel().getEntityManager().getEntities().contains(this)) {
            if (handler.getCurrentLevel().isConquered()
                    && getCollisionBounds().intersects(handler.getCurrentLevel().getEntityManager().getPlayer().getCollisionBounds()) && target != null) {
                handler.teleportPlayerTo(target);
            }
        }
    }

    @Override
    public void hurt(int amt) {
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return Assets.rock;
    }

    @Override
    public void onKilled() {
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        object.addProperty("target", target);
        return object;
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        target = !element.getAsJsonObject().get("target").isJsonNull() ? element.getAsJsonObject().get("target").getAsString() : null;
        return this;
    }
}
