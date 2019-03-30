package io.github.strikerrocker.entities.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
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
        JsonObject object = json.getAsJsonObject();
        float x = object.get("x").getAsFloat();
        float y = object.get("y").getAsFloat();
        return this.createNew(null, x, y).deserialize(json);
    }

    public JsonElement serialize(Entity entity) {
        return entity.serialize();
    }
}