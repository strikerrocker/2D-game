package main.java.io.github.strikerrocker.entities.ai;

import main.java.io.github.strikerrocker.entities.Creature;

public abstract class AI {
    public abstract boolean canExecute(Creature creature);

    public abstract void execute(Creature creature);
}
