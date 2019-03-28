package io.github.strikerrocker.misc;

import com.google.gson.*;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.EntityManager;
import io.github.strikerrocker.entities.player.Inventory;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.entities.type.EntityType;
import io.github.strikerrocker.items.Item;
import io.github.strikerrocker.items.ItemStack;

public class Deserializers {
    public static JsonDeserializer<ItemStack> itemStackJsonDeserializer = (json, typeOfT, context) -> {
        JsonObject object = json.getAsJsonObject();
        Item item = Item.getFromId(object.get("item").getAsJsonObject().get("id").getAsInt());
        int count = object.get("count").getAsInt();
        return new ItemStack(item, count);
    };
    public static JsonDeserializer<Inventory> inventoryJsonDeserializer = (json, typeOfT, context) -> {
        Inventory inventory = new Inventory(null);
        Gson gson = new GsonBuilder().registerTypeAdapter(ItemStack.class, itemStackJsonDeserializer).create();
        for (JsonElement element : json.getAsJsonObject().get("inventoryItems").getAsJsonArray()) {
            inventory.addStack(gson.fromJson(element, ItemStack.class));
        }
        return inventory;
    };
    public static JsonDeserializer<Player> playerJsonDeserializer = (json, typeOfT, context) -> {
        JsonObject object = json.getAsJsonObject();
        float x = object.get("x").getAsFloat();
        float y = object.get("y").getAsFloat();
        float xMove = object.get("xMove").getAsFloat();
        float yMove = object.get("yMove").getAsFloat();
        int health = object.get("health").getAsInt();
        Gson gson = new GsonBuilder().registerTypeAdapter(Inventory.class, inventoryJsonDeserializer).create();
        Inventory inventory = gson.fromJson(object.get("inventory"), Inventory.class);
        Player player = new Player(null, x, y);
        player.setHealth(health);
        player.setXMove(xMove);
        player.setYMove(yMove);
        player.setInventory(inventory);
        return player;
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
}
