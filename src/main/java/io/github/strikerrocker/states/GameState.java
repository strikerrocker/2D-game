package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.world.Level;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameState extends State {
    private Level level;
    private List<Level> levels;
    private Player player;
    private File worldDirectory;

    public GameState(Handler handler) {
        super(handler);
        levels = new ArrayList<>();
        player = new Player(handler, 2.5f, 2.5f);
        if (worldDirectory != null) loadWorld();
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Player getPlayer() {
        return player;
    }

    public void loadWorld() {
        for (File file1 : worldDirectory.listFiles()) {
            if (file1.getName().endsWith(".txt")) {
                levels.add(new Level(handler, file1));
            }
        }
        level = getLevel("level1");
        setPlayer(level, player);
    }

    public File getWorldDirectory() {
        return worldDirectory;
    }

    public void setWorldDirectory(File worldDirectory) {
        this.worldDirectory = worldDirectory;
        loadWorld();
    }

    private void setPlayer(Level level, Player player) {
        level.setPlayer(player);
    }

    public Level getLevel(String string) {
        for (Level level : levels) {
            if (level.getName().equals(string)) {
                return level;
            }
        }
        return null;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        setPlayer(level, null);
        this.level = level;
        setPlayer(level, player);
    }

    public void LevelWorld(String world) {
        setLevel(getLevel(world));
    }

    @Override
    public void tick() {
        level.tick();
    }

    @Override
    public void render(Graphics graphics) {
        level.render(graphics);
        drawPlayerInfo(graphics);
    }

    private void drawPlayerInfo(Graphics graphics) {
        Text.drawString(graphics, "FPS : " + handler.getGame().fps, 0, 20, false, Color.WHITE, Assets.font28);
        Text.drawString(graphics, "Health : " + player.getHealth(), 0, 40, false, Color.WHITE, Assets.font28);
    }
}
