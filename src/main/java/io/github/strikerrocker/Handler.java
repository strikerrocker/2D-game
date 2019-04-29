package io.github.strikerrocker;

import com.google.gson.Gson;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.gfx.GameCamera;
import io.github.strikerrocker.input.KeyManager;
import io.github.strikerrocker.input.MouseManager;
import io.github.strikerrocker.world.Level;

public class Handler {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public GameCamera getGameCamera() {
        return game.getCamera();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public int getWidth() {
        return game.getWidth();
    }

    public int getHeight() {
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public Level getCurrentLevel() {
        return game.getGameState().getCurrentLevel();
    }

    public void teleportPlayerTo(String world) {
        game.getGameState().teleportPlayerTo(world);
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public Gson getGson() {
        return game.getGson();
    }

    public Player getPlayer() {
        return game.getGameState().getPlayer();
    }
}