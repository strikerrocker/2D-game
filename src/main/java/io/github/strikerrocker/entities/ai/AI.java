package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;

public abstract class AI {
    public abstract boolean canExecute(Creature creature);

    public abstract void execute(Creature creature);
}
