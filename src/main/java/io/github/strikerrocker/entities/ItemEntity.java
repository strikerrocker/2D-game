package io.github.strikerrocker.entities;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.items.Item;
import io.github.strikerrocker.misc.Deserializers;
import io.github.strikerrocker.misc.Rectangle;

import java.awt.image.BufferedImage;

public class ItemEntity extends Creature {
    @Expose
    private Item item;

    public ItemEntity(Handler handler, float x, float y) {
        super(EntityTypes.item, handler, x, y, 32, 32, 1);
        bounds = new Rectangle(0, 0, 0.5f, 0.5f);
    }

    @Override
    protected void initAITasks() {
    }

    @Override
    public BufferedImage getCurrentFrame() {
        return item.getTexture();
    }

    @Override
    public void onKilled() {
        handler.getCurrentLevel().getEntityManager().getPlayer().getInventory().addStack(item);
    }

    public ItemEntity setItem(Item item) {
        this.item = item;
        return this;
    }

    @Override
    public JsonElement serialize() {
        JsonObject object = super.serialize().getAsJsonObject();
        JsonObject itemObject = new JsonObject();
        JsonObject itemDataObject = new JsonObject();
        itemDataObject.addProperty("id", item.getItemData().getId());
        itemObject.addProperty("count", item.getCount());
        itemObject.add("itemData", itemDataObject);
        object.add("item", itemObject);
        return object;
    }

    @Override
    public Entity deserialize(JsonElement element) {
        super.deserialize(element);
        setItem(new GsonBuilder().registerTypeAdapter(Item.class, Deserializers.itemJsonDeserializer).create().
                fromJson(element.getAsJsonObject().get("item"), Item.class));
        return this;
    }
}