package io.github.strikerrocker.world;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.Utils;
import io.github.strikerrocker.blocks.Block;
import io.github.strikerrocker.blocks.Blocks;
import io.github.strikerrocker.entities.EntityManager;
import io.github.strikerrocker.entities.Player;

import java.awt.*;
import java.util.logging.Level;

public class World {
    protected Handler handler;
    private int width, height;
    private float spawnX, spawnY;
    private int[][] blocks;
    private EntityManager entityManager;

    public World(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 2.5f, 2.5f));
        handler.setWorld(this);
        loadWorld(path);
        entityManager.getPlayer().setPos(new BlockPos(spawnX, spawnY));
        //entityManager.addEntity(new Zombie(handler, spawnX, spawnY + 2));
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
        int xStart = (int) Math.max(0, handler.getGameCamera().getXOffset() / Block.BLOCKWIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getXOffset() + handler.getWidth()) / Block.BLOCKWIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getYOffset() / Block.BLOCKHEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getYOffset() + handler.getHeight()) / Block.BLOCKHEIGHT + 1);
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBlock(x, y).render(graphics, (int) (x * Block.BLOCKWIDTH - handler.getGameCamera().getXOffset()),
                        (int) (y * Block.BLOCKHEIGHT - handler.getGameCamera().getYOffset()));
            }
        }
        entityManager.render(graphics);
    }

    public Block getBlock(float x, float y) {
        if (x < 0 || x > width || y < 0 || y > height) return Blocks.grass;
        Block block = Block.blocks[blocks[(int) x][(int) y]];
        if (block == null) return Blocks.dirt;
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
                try {
                    blocks[x][y] = Integer.parseInt(tokens[(x + y * width) + 4]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    handler.getGame().getLogger().log(Level.INFO, e.getMessage());
                    blocks[x][y] = 0;
                }
            }
        }
    }
}