package io.github.strikerrocker.entities;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.entities.player.Player;

import java.awt.*;
import java.util.List;
import java.util.*;

public class EntityManager {
    private Player player;
    @Expose
    private ArrayList<Entity> entities;
    private Comparator<Entity> sorter = (a, b) -> {
        if (a.getY() < b.getY()) {
            return -1;
        }
        return 1;
    };

    public EntityManager() {
        entities = new ArrayList<>();
    }

    public List<Entity> getEntities() {
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
            e.printStackTrace();
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
