package io.github.strikerrocker.entities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.misc.Rectangle;
import io.github.strikerrocker.world.BlockPos;

import java.awt.image.BufferedImage;

public class Portal extends Creature {
    private String targetDim;
    private BlockPos targetPos;

    public Portal(Handler handler, float x, float y) {
        super(EntityTypes.portal, handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT * 2, 1);
        bounds = new Rectangle(0, 0, 1, 2);
    }

    public BlockPos getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    public String getTargetDim() {
        return targetDim;
    }

    public void setTargetDim(String targetDim) {
        this.targetDim = targetDim;
    }

    @Override
    protected void initAITasks() {
    }

    @Override
    public void tick() {
        super.tick();
        if (handler.getCurrentLevel().getEntityManager().getEntities().contains(this) && handler.getCurrentLevel().isConquered()
                && getCollisionBounds().intersects(handler.getCurrentLevel().getEntityManager().getPlayer().getCollisionBounds()) && targetDim != null) {
            handler.teleportPlayerTo(targetDim);
            if (targetPos != null) handler.getCurrentLevel().getEntityManager().getPlayer().setPos(targetPos);
        }
    }

    @Override
    public void hurt(int amt) {
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return Assets.portal;
    }

    @Override
    public void onKilled() {
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        object.addProperty("targetDim", targetDim);
        if (targetPos != null) {
            JsonObject pos = new JsonObject();
            pos.addProperty("targetX", targetPos.getX());
            pos.addProperty("targetY", targetPos.getY());
            object.add("targetPos", pos);
        }
        return object;
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        targetDim = element.getAsJsonObject().get("targetDim").getAsString();
        if (element.getAsJsonObject().has("targetPos")) {
            JsonObject pos = element.getAsJsonObject().get("targetPos").getAsJsonObject();
            targetPos = new BlockPos(pos.get("targetX").getAsFloat(), pos.get("targetY").getAsFloat());
        }
        return this;
    }
}