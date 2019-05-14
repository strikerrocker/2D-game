package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;

public interface AI {
    boolean canExecute(Creature creature);

    void execute(Creature creature);
}
