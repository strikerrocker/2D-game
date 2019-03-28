package io.github.strikerrocker.entities.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.Entity;

import java.util.List;

public abstract class EntityType {

    public static List<EntityType> types;

    private String name;

    EntityType(String name) {
        this.name = name;
        types.add(this);
    }

    public String getName() {
        return name;
    }

    public abstract Entity createNew(Handler handler, float x, float y);

    public Entity deserialize(JsonElement json) {
        Entity entity = deserializeEntity(json);
        JsonObject object = json.getAsJsonObject();
        if (object.has("health")) {
            Creature creature = (Creature) entity;
            float xMove = object.get("xMove").getAsFloat();
            float yMove = object.get("yMove").getAsFloat();
            int health = object.get("health").getAsInt();
            creature.setXMove(xMove);
            creature.setYMove(yMove);
            creature.setHealth(health);
            return creature;
        }
        return entity;
    }

    private Entity deserializeEntity(JsonElement element) {
        JsonObject object = element.getAsJsonObject();
        float x = object.get("x").getAsFloat();
        float y = object.get("y").getAsFloat();
        return this.createNew(null, x, y);
    }

    public JsonElement serialize(Entity entity) {
        JsonElement element = serializeEntity(entity);
        if (entity instanceof Creature) {
            Creature creature = (Creature) entity;
            JsonObject object = serializeEntity(entity).getAsJsonObject();
            object.addProperty("health", creature.getHealth());
            object.addProperty("xMove", creature.getXMove());
            object.addProperty("yMove", creature.getYMove());
            return object;
        }
        return element;
    }

    private JsonElement serializeEntity(Entity entity) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", entity.getX());
        jsonObject.addProperty("y", entity.getY());
        jsonObject.addProperty("name", entity.getName());
        return jsonObject;
    }
}