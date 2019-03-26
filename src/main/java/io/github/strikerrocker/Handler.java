package io.github.strikerrocker;

import io.github.strikerrocker.gfx.GameCamera;
import io.github.strikerrocker.input.KeyManager;
import io.github.strikerrocker.input.MouseManager;
import io.github.strikerrocker.world.World;

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

    public World getWorld() {
        return game.gameState.getWorld();
    }

    public void setWorld(String world) {
        game.gameState.setWorld(world);
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }
}
