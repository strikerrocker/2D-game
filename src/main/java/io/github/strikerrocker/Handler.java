package io.github.strikerrocker;

import io.github.strikerrocker.gfx.GameCamera;
import io.github.strikerrocker.input.KeyManager;
import io.github.strikerrocker.input.MouseManager;
import io.github.strikerrocker.world.Level;

import java.io.File;

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

    public Level getWorld() {
        return game.gameState.getLevel();
    }

    public void setWorld(String world) {
        game.gameState.LevelWorld(world);
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public File getWorldDirectory() {
        return game.gameState.getWorldDirectory();
    }
}
