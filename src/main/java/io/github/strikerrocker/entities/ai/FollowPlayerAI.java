package main.java.io.github.strikerrocker.entities.ai;

import main.java.io.github.strikerrocker.entities.Creature;
import main.java.io.github.strikerrocker.entities.Player;
import main.java.io.github.strikerrocker.gfx.PixelPos;
import main.java.io.github.strikerrocker.pathfinding.PathFinder;
import main.java.io.github.strikerrocker.world.BlockPos;

import java.util.ArrayList;

public class FollowPlayerAI extends AI {
    private BlockPos playerPos;
    private PathFinder finder;
    private ArrayList<BlockPos> path;
    private int i = 0;

    public FollowPlayerAI(Creature creature) {
        Player player = creature.getHandler().getWorld().getEntityManager().getPlayer();
        playerPos = new PixelPos(player.getX(), player.getY()).toBlockPos();
        finder = new PathFinder(creature.getHandler(), creature, playerPos);
        path = finder.getPath();
    }


    @Override
    public boolean canExecute(Creature creature) {
        /**int factor = 128;
         Player player = creature.getHandler().getWorld().getEntityManager().getPlayer();
         Rectangle visibleArea = creature.getCollisionBounds(0, 0);
         visibleArea.grow(factor, factor);
         Rectangle attackRange = creature.getCollisionBounds(0, 0);
         attackRange.grow(1, 1);
         if (player.getCollisionBounds(0, 0).intersects(visibleArea) && player.isActive()) {
         return !player.getCollisionBounds(0, 0).intersects(attackRange);
         }**/
        return i++ == 0;
    }

    @Override
    public void execute(Creature creature) {
        Player player = creature.getHandler().getWorld().getEntityManager().getPlayer();
        BlockPos tempPlayerPos = new PixelPos(player.getX(), player.getY()).toBlockPos();
        if (playerPos.equals(tempPlayerPos)) {
            //          double x = (player.getCollisionBounds(0, 0).getCenterX() - creature.getCollisionBounds(0, 0).getCenterX());
//            double y = (player.getCollisionBounds(0, 0).getCenterY() - creature.getCollisionBounds(0, 0).getCenterY());
            //double total = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            //creature.setXMove((float) ((x / total) * creature.getSpeed()));
            //creature.setYMove((float) ((y / total) * creature.getSpeed()));
        } else {
            finder = new PathFinder(creature.getHandler(), creature, playerPos);
            path = finder.getPath();
        }
    }
}