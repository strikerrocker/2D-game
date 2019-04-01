package io.github.strikerrocker.world;

import com.google.gson.annotations.Expose;
import io.github.strikerrocker.Handler;
import io.github.strikerrocker.blocks.Block;
import io.github.strikerrocker.blocks.Blocks;
import io.github.strikerrocker.entities.EntityManager;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.misc.Utils;

import java.awt.*;
import java.io.File;

public class Level {
    protected Handler handler;
    @Expose
    private String name;
    @Expose
    private int worldWidth, worldHeight;
    private BlockPos spawn;
    @Expose
    private int[][] blocks;
    private EntityManager entityManager;
    private boolean isConquered = true;

    public Level(Handler handler, File path) {
        this.handler = handler;
        this.name = path.getName().replaceFirst(".txt", "");
        entityManager = new EntityManager();
        loadWorld(path);
    }

    public boolean isConquered() {
        return isConquered;
    }

    public String getName() {
        return name;
    }

    public BlockPos getSpawn() {
        return spawn;
    }

    public void setPlayer(Player player) {
        entityManager.setPlayer(player);
        if (player != null) {
            player.setPos(spawn);
        }
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void tick() {
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                getBlock(x, y).tick();
            }
        }
        entityManager.tick();
    }

    public void render(Graphics graphics) {
        int xStart = (int) Math.max(0, handler.getGameCamera().getXOffset() / Block.BLOCKWIDTH);
        int xEnd = (int) Math.min(worldWidth, (handler.getGameCamera().getXOffset() + handler.getWidth()) / Block.BLOCKWIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getYOffset() / Block.BLOCKHEIGHT);
        int yEnd = (int) Math.min(worldHeight, (handler.getGameCamera().getYOffset() + handler.getHeight()) / Block.BLOCKHEIGHT + 1);
        for (int y = yStart; y < yEnd; y++) {
            for (int x = xStart; x < xEnd; x++) {
                getBlock(x, y).render(graphics, (int) (x * Block.BLOCKWIDTH - handler.getGameCamera().getXOffset()),
                        (int) (y * Block.BLOCKHEIGHT - handler.getGameCamera().getYOffset()));
            }
        }
        entityManager.render(graphics);
    }

    public Block getBlock(float x, float y) {
        if (x < 0 || x > worldWidth || y < 0 || y > worldHeight) return Blocks.grass;
        Block block;
        try {
            block = Block.blocks[blocks[(int) x][(int) y]];
        } catch (ArrayIndexOutOfBoundsException e) {
            block = null;
        }
        if (block == null) return Blocks.dirt;
        return block;
    }

    public void setBlock(int x, int y, Block block) {
        if (x < 0 || x > worldWidth || y < 0 || y > worldHeight) return;
        blocks[x][y] = block.getId();
    }

    private void loadWorld(File path) {
        String file = Utils.loadFilesAsString(path);
        String[] tokens = file.split("\\s+");
        worldWidth = Integer.parseInt(tokens[0]);
        worldHeight = Integer.parseInt(tokens[1]);
        spawn = new BlockPos(Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
        blocks = new int[worldWidth][worldHeight];
        for (int y = 0; y < worldHeight; y++) {
            for (int x = 0; x < worldWidth; x++) {
                try {
                    blocks[x][y] = Integer.parseInt(tokens[(x + y * worldHeight) + 4]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    handler.getGame().getLogger().log(java.util.logging.Level.INFO, e.getMessage());
                    blocks[x][y] = 0;
                }
            }
        }
    }
}