package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityType;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.misc.Rectangle;
import io.github.strikerrocker.world.BlockPos;

import java.awt.*;

import static io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public abstract class Entity {
    @Expose
    protected float x, y;
    protected float width, height;
    protected Handler handler;
    boolean active = true;
    Rectangle bounds;
    @Expose
    private String name;

    public Entity(EntityType type, Handler handler, float x, float y, float width, float height) {
        this.name = type.getName();
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        bounds = new Rectangle(0, 0, width, height);
    }

    public String getName() {
        return name;
    }

    public BlockPos getPos() {
        return new BlockPos(x, y);
    }

    public void setPos(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public PixelPos getPixelPos() {
        return getPos().toPixelPos();
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
    }

    public Rectangle getCollisionBounds() {
        return getCollisionBounds(0, 0);
    }

    public Rectangle getPixelCollisionBounds(float xOffset, float yOffset) {
        PixelPos pos = getPixelPos();
        return new Rectangle((int) (pos.getX() + xOffset) + (bounds.x * BLOCKWIDTH) - handler.getGameCamera().getXOffset(), (int) (pos.getY() + yOffset) + (bounds.y * BLOCKHEIGHT) - handler.getGameCamera().getYOffset(), bounds.width * BLOCKWIDTH, bounds.height * BLOCKHEIGHT);
    }

    boolean hasEntityCollision(float xOffset, float yOffset) {
        for (Entity entity : handler.getCurrentLevel().getEntityManager().getEntities()) {
            if (entity != this && entity.getCollisionBounds().intersects(getCollisionBounds(xOffset, yOffset)) && !(entity instanceof Portal)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEntityCollision(float xOffset, float yOffset, Entity exceptEntity) {
        for (Entity entity : handler.getCurrentLevel().getEntityManager().getEntities()) {
            if (entity != this && entity != exceptEntity && entity.getCollisionBounds().intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public abstract void tick();

    public abstract void render(Graphics graphics);

    public boolean isActive() {
        return active;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public JsonElement serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", x);
        jsonObject.addProperty("y", y);
        jsonObject.addProperty("name", name);
        return jsonObject;
    }

    public Entity deserialize(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        this.x = object.get("x").getAsFloat();
        this.y = object.get("y").getAsFloat();
        return this;
    }
}
