package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.world.BlockPos;

public class FollowPlayerAI extends MoveToAI {
    private BlockPos lastPlayerPos;

    public FollowPlayerAI(Creature creature) {
        super(creature, null);
        setConsumer(creature1 -> setTarget(creature1.getHandler().getPlayer().getPos().intForm()));
    }

    private void setTarget(BlockPos lastPlayerPos) {
        this.lastPlayerPos = lastPlayerPos;
        setTargetPos(lastPlayerPos, creature);
    }

    @Override
    public boolean canExecute(Creature creature) {
        int factor = 2;
        Player player = creature.getHandler().getCurrentLevel().getEntityManager().getPlayer();
        if (lastPlayerPos == null) setTarget(player.getPos());
        creature.setMoveTimer(creature.getMoveTimer() + System.currentTimeMillis() - creature.getLastMoveTimer());
        creature.setLastMoveTimer(System.currentTimeMillis());
        if (creature.getMoveTimer() > creature.getMoveCooldown()
                && player.getCollisionBounds().intersects(creature.getCollisionBounds().grow(factor, factor)) &&
                player.isActive()) {
            return super.canExecute(creature);
        }
        return false;
    }

    @Override
    public void execute(Creature creature) {
        //TODO fix the random hang up in game
        super.execute(creature);
    }
}