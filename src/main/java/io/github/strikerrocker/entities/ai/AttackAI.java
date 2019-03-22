package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.Zombie;

import java.awt.*;

public class AttackAI extends AI {
    private float growFactor;
    private int hurtAmt;

    public AttackAI(float growFactor, int hurtAmt) {
        this.growFactor = growFactor;
        this.hurtAmt = hurtAmt;
    }

    @Override
    public boolean canExecute(Creature creature) {
        Rectangle visibleArea = new Rectangle((int) (creature.getBounds().x + creature.getX()), (int) (creature.getBounds().y + creature.getY()), creature.getBounds().width, creature.getBounds().height);
        visibleArea.grow((int) growFactor, (int) growFactor);
        for (Entity e : creature.getHandler().getWorld().getEntityManager().getEntities()) {
            if (e.getCollisionBounds(0, 0).intersects(visibleArea) && !(e instanceof Zombie) && e instanceof Creature) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void execute(Creature creature) {
        Rectangle visibleArea = new Rectangle((int) (creature.getBounds().x + creature.getX()), (int) (creature.getBounds().y + creature.getY()), creature.getBounds().width, creature.getBounds().height);
        visibleArea.grow((int) growFactor, (int) growFactor);
        for (Entity e : creature.getHandler().getWorld().getEntityManager().getEntities()) {
            if (e.getCollisionBounds(0, 0).intersects(visibleArea) && !(e instanceof Zombie) && e instanceof Creature) {
                if (creature.getAttackTimer() > creature.getAttackCooldown()) {
                    ((Creature) e).hurt(hurtAmt);
                    creature.setAttackTimer(0);
                }
            }
        }
    }
}