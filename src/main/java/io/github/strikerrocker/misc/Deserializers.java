package io.github.strikerrocker.misc;

import com.google.gson.*;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.EntityManager;
import io.github.strikerrocker.entities.player.Inventory;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.entities.type.EntityType;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.items.Item;
import io.github.strikerrocker.items.ItemData;

public class Deserializers {
    public static JsonDeserializer<Item> itemJsonDeserializer = (json, typeOfT, context) -> {
        JsonObject object = json.getAsJsonObject();
        ItemData itemData = ItemData.getFromId(object.get("itemData").getAsJsonObject().get("id").getAsInt());
        int count = object.get("count").getAsInt();
        return new Item(itemData, count);
    };
    public static JsonDeserializer<Inventory> inventoryJsonDeserializer = (json, typeOfT, context) -> {
        Inventory inventory = new Inventory(null);
        Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, itemJsonDeserializer).create();
        for (JsonElement element : json.getAsJsonObject().get("inventoryItems").getAsJsonArray()) {
            inventory.addStack(gson.fromJson(element, Item.class));
        }
        return inventory;
    };
    public static JsonDeserializer<Player> playerJsonDeserializer = (json, typeOfT, context) -> {
        JsonObject object = json.getAsJsonObject();
        float x = object.get("x").getAsFloat();
        float y = object.get("y").getAsFloat();
        return (Player) EntityTypes.player.createNew(null, x, y).deserialize(json);
    };
    public static JsonDeserializer<Entity> entityJsonDeserializer = (json, typeOfT, context) -> {
        Entity entity = null;
        for (EntityType type : EntityType.types) {
            if (type.getName().equals(json.getAsJsonObject().get("name").getAsString())) {
                entity = type.deserialize(json);
            }
        }
        return entity;
    };
    public static JsonDeserializer<EntityManager> managerJsonDeserializer = (json, typeOfT, context) -> {
        JsonObject object = json.getAsJsonObject();
        EntityManager manager = new EntityManager();
        JsonArray entities = object.get("entities").getAsJsonArray();
        Gson gson = new GsonBuilder().registerTypeAdapter(Entity.class, entityJsonDeserializer).create();
        for (JsonElement element : entities) {
            manager.addEntity(gson.fromJson(element, Entity.class));
        }
        return manager;
    };

    private Deserializers() {
    }
}
