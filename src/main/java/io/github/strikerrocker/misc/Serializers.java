package io.github.strikerrocker.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.type.EntityType;

public class Serializers {
    public static JsonSerializer<Entity> entityJsonSerializer = (src, typeOfSrc, context) -> {
        JsonElement element = null;
        for (EntityType type : EntityType.types) {
            if (src.getName().equals(type.getName())) {
                element = type.serialize(src);
            }
        }
        return element;
    };
}