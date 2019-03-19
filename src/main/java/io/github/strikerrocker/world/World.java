package main.java.io.github.strikerrocker.world;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.Utils;
import main.java.io.github.strikerrocker.blocks.Block;
import main.java.io.github.strikerrocker.entities.EntityManager;
import main.java.io.github.strikerrocker.entities.Player;
import main.java.io.github.strikerrocker.entities.Zombie;

import java.awt.*;

import static main.java.io.github.strikerrocker.blocks.Block.BLOCKHEIGHT;
import static main.java.io.github.strikerrocker.blocks.Block.BLOCKWIDTH;

public class World {
    protected Handler handler;
    private int width, height;
    private float spawnX, spawnY;
    private int[][] blocks;
    private EntityManager entityManager;

    public World(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 2.5f, 2.5f));
        loadWorld(path);
        entityManager.addEntity(new Zombie(handler, spawnX, spawnY + 1));
        entityManager.getPlayer().setPos(new BlockPos(spawnX, spawnY));
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void tick() {
        for (int y = 0; y > height; y++) {
            for (int x = 0; x > width; x++) {
                getBlock(x, y).tick();
            }
        }
        entityManager.tick();
    }

    public void render(Graphics graphics) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getXOffset() / BLOCKWIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getXOffset() + handler.getWidth()) / BLOCKWIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getYOffset() / BLOCKHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getYOffset() + handler.getHeight()) / BLOCKHEIGHT + 1);
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBlock(x, y).render(graphics, (int) (x * BLOCKWIDTH - handler.getGameCamera().getXOffset()),
                        (int) (y * BLOCKHEIGHT - handler.getGameCamera().getYOffset()));
            }
        }
        entityManager.render(graphics);
    }

    public Block getBlock(float x, float y) {
        if (x < 0 || x > width || y < 0 || y > height) return Block.grass;
        Block block = Block.blocks[blocks[(int) x][(int) y]];
        if (block == null) return Block.dirt;
        return block;
    }

    private void loadWorld(String path) {
        String file = Utils.loadFilesAsFile(path);
        String[] tokens = file.split("\\s+");
        width = Integer.parseInt(tokens[0]);
        height = Integer.parseInt(tokens[1]);
        spawnX = Float.parseFloat(tokens[2]);
        spawnY = Float.parseFloat(tokens[3]);
        blocks = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                blocks[x][y] = Integer.parseInt(tokens[(x + y * width) + 4]);
            }
        }
    }
}