package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.Zombie;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.misc.Rectangle;

public class AttackAI extends AI {
    private int rangeFactor;
    private int hurtAmt;

    public AttackAI(int rangeFactor, int hurtAmt) {
        this.rangeFactor = rangeFactor;
        this.hurtAmt = hurtAmt;
    }

    @Override
    public boolean canExecute(Creature creature) {
        Rectangle visibleArea = creature.getCollisionBounds().grow(rangeFactor, rangeFactor);
        for (Entity e : creature.getHandler().getCurrentLevel().getEntityManager().getEntities()) {
            if (e.getCollisionBounds().intersects(visibleArea) && !(e instanceof Zombie) && e instanceof Creature) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(Creature creature) {
        Rectangle visibleArea = creature.getCollisionBounds().grow(rangeFactor, rangeFactor);
        for (Entity e : creature.getHandler().getCurrentLevel().getEntityManager().getEntities()) {
            if (e.getCollisionBounds().intersects(visibleArea) && e instanceof Player && creature.getAttackTimer() > creature.getAttackCooldown()) {
                ((Creature) e).hurt(hurtAmt);
                creature.setAttackTimer(0);
            }
        }
    }
}