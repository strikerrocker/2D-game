package io.github.strikerrocker.entities.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.ItemEntity;
import io.github.strikerrocker.entities.Tree;
import io.github.strikerrocker.entities.Zombie;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.items.ItemStack;
import io.github.strikerrocker.misc.Deserializers;

import java.util.ArrayList;

public class EntityTypes {
    public static EntityType zombie;
    public static EntityType tree;
    public static EntityType item;
    public static EntityType player;

    public static void init() {
        EntityType.types = new ArrayList<>();
        zombie = new EntityType("zombie") {
            @Override
            public Entity createNew(Handler handler, float x, float y) {
                return new Zombie(handler, x, y);
            }
        };
        player = new EntityType("player") {
            @Override
            public Entity createNew(Handler handler, float x, float y) {
                return new Player(handler, x, y);
            }
        };
        item = new EntityType("item") {
            @Override
            public Entity createNew(Handler handler, float x, float y) {
                return new ItemEntity(handler, x, y);
            }

            @Override
            public Entity deserialize(JsonElement json) {
                ItemEntity entity = (ItemEntity) super.deserialize(json);
                Gson gson = new GsonBuilder().registerTypeAdapter(ItemStack.class, Deserializers.itemStackJsonDeserializer).create();
                entity.setStack(gson.fromJson(json.getAsJsonObject().get("stack"), ItemStack.class));
                return entity;
            }

            @Override
            public JsonElement serialize(Entity entity) {
                ItemEntity itemStack = (ItemEntity) entity;
                JsonObject object = super.serialize(entity).getAsJsonObject();
                JsonObject stackObject = new JsonObject();
                JsonObject itemObject = new JsonObject();
                itemObject.addProperty("id", itemStack.getStack().getItem().getId());
                stackObject.addProperty("count", itemStack.getStack().getCount());
                stackObject.add("item", itemObject);
                object.add("stack", stackObject);
                return object;
            }
        };
        tree = new EntityType("tree") {
            @Override
            public Entity createNew(Handler handler, float x, float y) {
                return new Tree(handler, x, y);
            }

            @Override
            public Entity deserialize(JsonElement json) {
                Tree tree = (Tree) super.deserialize(json);
                tree.setHasApple(json.getAsJsonObject().get("hasApple").getAsBoolean());
                return tree;
            }

            @Override
            public JsonElement serialize(Entity entity) {
                JsonObject object = super.serialize(entity).getAsJsonObject();
                object.addProperty("hasApple", ((Tree) entity).hasApple());
                return object;
            }
        };
    }
}
