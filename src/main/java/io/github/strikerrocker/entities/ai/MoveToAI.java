package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.pathfinding.PathFinder;
import io.github.strikerrocker.world.BlockPos;

import java.util.List;

public class MoveToAI implements AI {
    protected BlockPos targetPos;
    private BlockPos tempTarget;
    private List<BlockPos> path;

    public MoveToAI(BlockPos targetPos) {
        this.targetPos = targetPos;
    }

    public void setTargetPos(BlockPos targetPos, Creature creature) {
        this.targetPos = targetPos.intForm();
        updatePath(creature);
    }

    @Override
    public boolean canExecute(Creature creature) {
        if (creature.getPos().intForm().equals(targetPos.intForm())) {
            creature.setXMove(0);
            creature.setYMove(0);
            return false;
        }
        return true;
    }

    @Override
    public void execute(Creature creature) {
        if (path == null || path.isEmpty()) updatePath(creature);
        if ((tempTarget == null || tempTarget.intForm().equals(creature.getPos().intForm())) && !path.isEmpty()) {
            BlockPos movePos = path.get(0);
            double x = (movePos.getX() - creature.getX());
            double y = (movePos.getY() - creature.getY());
            double total = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            BlockPos move = new BlockPos((float) ((x / total) * creature.getSpeed()), (float) ((y / total) * creature.getSpeed()));
            creature.setXMove(move.getX());
            creature.setYMove(move.getY());
            tempTarget = movePos;
            path.remove(tempTarget);
        }
    }

    private void updatePath(Creature creature) {
        tempTarget = creature.getPos().intForm();
        List<BlockPos> list = new PathFinder(creature.getPos(), targetPos).setEntity(creature).setHandler(creature.getHandler()).tryGetPath();
        if (!list.isEmpty()) list.remove(0);
        path = list;
        System.out.println(path);
    }
}