package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.player.Player;

public class FollowPlayerAI extends MoveToAI {
    private long lastMoveTimer;
    private long moveCooldown = 1000;
    private long moveTimer = moveCooldown;

    public FollowPlayerAI() {
        super(null);
    }

    @Override
    public boolean canExecute(Creature creature) {
        int factor = 2;
        Player player = creature.getHandler().getCurrentLevel().getEntityManager().getPlayer();
        if (targetPos == null) setTargetPos(player.getPos().intForm(), creature);
        if (player.getCollisionBounds().intersects(creature.getCollisionBounds().grow(factor, factor)) &&
                player.isActive()) {
            return super.canExecute(creature);
        }
        return false;
    }

    @Override
    public void execute(Creature creature) {
        moveTimer += System.currentTimeMillis() - lastMoveTimer;
        lastMoveTimer = System.currentTimeMillis();
        Player player = creature.getHandler().getCurrentLevel().getEntityManager().getPlayer();
        if (moveTimer > moveCooldown) {
            moveTimer = 0;
            if (!player.getPos().equals(targetPos)) {
                setTargetPos(player.getPos().intForm(), creature);
            }
        }
        //TODO fix the random hang up in game
        super.execute(creature);
    }
}