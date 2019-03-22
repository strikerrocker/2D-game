package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.Main;
import io.github.strikerrocker.entities.Player;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.world.World;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameState extends State {
    private World world;
    private List<World> worlds;
    private Player player;

    public GameState(Handler handler) {
        super(handler);
        File worldFolder = new File("run/worlds");
        worlds = new ArrayList<>();
        player = new Player(handler, 2.5f, 2.5f);
        for (File file : worldFolder.listFiles()) {
            if (file.getName().endsWith(".txt")) {
                worlds.add(new World(handler, file));
            }
        }
        world = getWorld("world1");
        setPlayer(world, player);
    }

    private void setPlayer(World world, Player player) {
        world.setPlayer(player);
    }

    public World getWorld(String string) {
        for (World world : worlds) {
            if (world.getName().equals(string)) {
                return world;
            }
        }
        return null;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        setPlayer(world, null);
        this.world = world;
        setPlayer(world, player);
    }

    public void setWorld(String world) {
        setWorld(getWorld(world));
    }

    @Override
    public void tick() {
        world.tick();
    }

    @Override
    public void render(Graphics graphics) {
        world.render(graphics);
        drawPlayerInfo(graphics);
    }

    private void drawPlayerInfo(Graphics graphics) {
        Text.drawString(graphics, "FPS : " + Main.game.fps, 0, 20, false, Color.WHITE, Assets.font28);
        Text.drawString(graphics, "Health : " + player.getHealth(), 0, 40, false, Color.WHITE, Assets.font28);
    }
}
