package io.github.strikerrocker.entities;

import io.github.strikerrocker.Handler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class EntityManager {
    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    private Comparator<Entity> sorter = (a, b) -> {
        if (a.getY() < b.getY()) {
            return -1;
        }
        return 1;
    };

    public EntityManager(Handler handler) {
        this.handler = handler;
        entities = new ArrayList<>();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        if (player != null) {
            addEntity(player);
        } else {
            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    removeEntity(entity);
                }
            }
        }
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public void tick() {
        try {
            Iterator<Entity> iterator = entities.iterator();
            while (iterator.hasNext()) {
                Entity entity1 = iterator.next();
                entity1.tick();
                if (!entity1.isActive()) {
                    iterator.remove();
                }
            }
        } catch (ConcurrentModificationException e) {
        }
        entities.sort(sorter);
    }

    public void render(Graphics graphics) {
        for (Entity entity : entities) {
            entity.render(graphics);
        }
        if (player != null) {
            player.postRender(graphics);
        }
    }
}
