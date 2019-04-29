package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.world.BlockPos;

public class FollowPlayerAI extends MoveToAI {
    private BlockPos lastPlayerPos;

    public FollowPlayerAI(Creature creature) {
        super(creature, null);
    }

    private void setLastPlayerPos(BlockPos lastPlayerPos) {
        this.lastPlayerPos = lastPlayerPos;
        setTargetPos(lastPlayerPos, creature);
    }

    @Override
    public boolean canExecute(Creature creature) {
        int factor = 2;
        Player player = creature.getHandler().getCurrentLevel().getEntityManager().getPlayer();
        if (lastPlayerPos == null) setLastPlayerPos(player.getPos());
        if (player.getCollisionBounds(0, 0).intersects(creature.getCollisionBounds(0, 0).grow(factor, factor)) &&
                player.isActive() && !player.getCollisionBounds(0, 0).intersects(creature.getCollisionBounds(0, 0).grow(1, 1))) {
            return super.canExecute(creature);
        }
        return false;
    }

    @Override
    public void execute(Creature creature) {
        Player player = creature.getHandler().getCurrentLevel().getEntityManager().getPlayer();
        //TODO fix the random hang up in game
        creature.setMoveTimer(creature.getMoveTimer() + System.currentTimeMillis() - creature.getLastMoveTimer());
        creature.setLastMoveTimer(System.currentTimeMillis());
        if (creature.getMoveTimer() > creature.getMoveCooldown()) {
            if (!lastPlayerPos.equals(player.getPos())) {
                setLastPlayerPos(player.getPos());
            } else {
                super.execute(creature);
            }
        }
    }
}