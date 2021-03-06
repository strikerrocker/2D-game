package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.misc.GameData;
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
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void loadWorld() {
        for (File file1 : worldDirectory.listFiles()) {
            if (file1.getName().endsWith(".txt")) {
                levels.add(new Level(handler, file1));
            }
        }
        GameData.readEntityData(worldDirectory, handler.getGson(), handler);
        level = getLevel(player.getLevel());
    }

    public File getWorldDirectory() {
        return worldDirectory;
    }

    void setWorldDirectory(File worldDirectory) {
        this.worldDirectory = worldDirectory;
        loadWorld();
        setPlayerAtLvl(level, player);
    }

    private void setPlayerAtLvl(Level level, Player player) {
        level.setPlayer(player);
    }

    public Level getLevel(String string) {
        return levels.stream().filter(lvl -> lvl.getName().equals(string)).findFirst().orElse(null);
    }

    public Level getCurrentLevel() {
        return level;
    }

    public void teleportPlayerTo(Level level) {
        setPlayerAtLvl(level, null);
        this.level = level;
        setPlayerAtLvl(level, player);
        player.setLevel(level.getName());
    }

    public void teleportPlayerTo(String world) {
        teleportPlayerTo(getLevel(world));
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
        Text.drawString(graphics, "FPS : " + handler.getGame().getFps(), 0, 20, false, Color.WHITE, Assets.font28);
        Text.drawString(graphics, "Health : " + player.getHealth(), 0, 40, false, Color.WHITE, Assets.font28);
    }
}
