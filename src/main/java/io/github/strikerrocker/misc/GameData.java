package io.github.strikerrocker.misc;

import com.google.gson.Gson;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.EntityManager;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.states.GameState;
import io.github.strikerrocker.world.Level;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class GameData {

    private static void saveEntityData(GameState gameState, File worldDir, Gson gson) {
        try {
            if (gameState.getPlayer() != null) {
                Player player = gameState.getPlayer();
                Path playerData = Paths.get(worldDir.getPath() + "/player.json");
                Files.deleteIfExists(playerData);
                playerData.toFile().createNewFile();
                PrintWriter playerDataWriter = new PrintWriter(new FileWriter(playerData.toFile()));
                playerDataWriter.println(gson.toJson(player, Player.class));
                playerDataWriter.close();
            }
            Paths.get(worldDir.getPath() + "/entities").toFile().mkdirs();
            for (Level lvl : gameState.getLevels()) {
                Path lvlEntityData = Paths.get(worldDir.getPath() + "/entities/" + lvl.getName() + "Entities.json");
                Files.deleteIfExists(lvlEntityData);
                lvlEntityData.toFile().createNewFile();
                PrintWriter writer = new PrintWriter(new FileWriter(lvlEntityData.toFile()));
                EntityManager manager = lvl.getEntityManager();
                Iterator<Entity> iterator = manager.getEntities().iterator();
                while (iterator.hasNext()) {
                    Entity entity1 = iterator.next();
                    entity1.tick();
                    if (entity1 instanceof Player) {
                        iterator.remove();
                    }
                }
                writer.println(gson.toJson(manager));
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConcurrentModificationException ignored) {
        }
    }

    public static void readEntityData(File worldDir, Gson gson, Handler handler) {
        try {
            Path playerData = Paths.get(worldDir.getPath() + "/player.json");
            if (playerData.toFile().exists()) {
                FileReader reader = new FileReader(playerData.toFile());
                Player player = gson.fromJson(reader, Player.class);
                player.setHandler(handler);
                player.getInventory().setHandler(handler);
                handler.getGame().getGameState().setPlayer(player);
            } else {
                handler.getGame().getGameState().setPlayer(new Player(handler, 3, 3));
            }
            for (Level lvl : handler.getGame().getGameState().getLevels()) {
                Path lvlEntityData = Paths.get(worldDir.getPath() + "/entities/" + lvl.getName() + "Entities.json");
                if (lvlEntityData.toFile().exists()) {
                    EntityManager manager = gson.fromJson(new FileReader(lvlEntityData.toFile()), EntityManager.class);
                    if (manager != null) {
                        manager.getEntities().forEach(entity -> entity.setHandler(handler));
                    } else {
                        manager = new EntityManager();
                    }
                    lvl.setEntityManager(manager);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void save(GameState gameState, Gson gson) {
        File worldDir = gameState.getWorldDirectory();
        for (Level level : gameState.getLevels()) level.save();
        saveEntityData(gameState, worldDir, gson);
    }

    public static void setupNewWorld(File worldDir) {
        File world = new File(ClassLoader.getSystemClassLoader().getResource("worlds/").getFile());
        Utils.copyFolder(world, worldDir);
    }
}