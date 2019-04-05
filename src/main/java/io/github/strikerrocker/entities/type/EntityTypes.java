package io.github.strikerrocker.entities.type;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.*;
import io.github.strikerrocker.entities.player.Player;

import java.util.ArrayList;

public class EntityTypes {
    public static EntityType zombie;
    public static EntityType tree;
    public static EntityType item;
    public static EntityType portal;
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
        };
        tree = new EntityType("tree") {
            @Override
            public Entity createNew(Handler handler, float x, float y) {
                return new Tree(handler, x, y);
            }
        };
        portal = new EntityType("portal") {
            @Override
            public Entity createNew(Handler handler, float x, float y) {
                return new Portal(handler, x, y);
            }
        };
    }
}