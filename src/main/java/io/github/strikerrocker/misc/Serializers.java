package io.github.strikerrocker.misc;

import com.google.gson.*;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.player.Inventory;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.entities.type.EntityType;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.items.Item;

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
    public static JsonSerializer<Player> playerJsonSerialize = (src, typeOfSrc, context) -> EntityTypes.player.serialize(src);
    public static JsonSerializer<Inventory> inventoryJsonSerializer = (src, typeOfSrc, context) -> {
        JsonObject object = new JsonObject();
        JsonArray items = new JsonArray();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        for (Item item : src.getInventoryItems()) {
            items.add(gson.toJsonTree(item, Item.class));
        }
        object.add("inventoryItems", items);
        return object;
    };

    private Serializers() {
    }
}