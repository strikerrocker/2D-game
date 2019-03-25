package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.misc.Rectangle;
import io.github.strikerrocker.world.BlockPos;

public class FollowPlayerAI extends MoveToAI {
    private BlockPos lastPlayerPos;

    public FollowPlayerAI(Creature creature) {
        super(creature, null);
        Player player = creature.getHandler().getWorld().getEntityManager().getPlayer();
        lastPlayerPos = player.getPos();
        setTargetPos(lastPlayerPos, creature);
    }


    @Override
    public boolean canExecute(Creature creature) {
        int factor = 128;
        Player player = creature.getHandler().getWorld().getEntityManager().getPlayer();
        Rectangle visibleArea = creature.getCollisionBounds(0, 0);
        visibleArea.grow(factor, factor);
        Rectangle attackRange = creature.getCollisionBounds(0, 0);
        attackRange.grow(1, 1);
        if (player.getCollisionBounds(0, 0).intersects(visibleArea) && player.isActive() && !player.getCollisionBounds(0, 0).intersects(attackRange) && super.canExecute(creature)) {
            return true;
        } else {
            creature.setXMove(0);
            creature.setYMove(0);
            return false;
        }
    }

    @Override
    public void execute(Creature creature) {
        Player player = creature.getHandler().getWorld().getEntityManager().getPlayer();
        //TODO fix the random hang up in game
        if (!lastPlayerPos.equals(player.getPos())) {
            lastPlayerPos = player.getPos();
            setTargetPos(lastPlayerPos, creature);
        } else {
            super.execute(creature);
        }
        lastPlayerPos = player.getPos();
    }
}