package io.github.strikerrocker.entities.ai;

import io.github.strikerrocker.entities.Creature;
import io.github.strikerrocker.entities.pathfinding.PathFinder;
import io.github.strikerrocker.gfx.PixelPos;
import io.github.strikerrocker.world.BlockPos;

import java.util.ArrayList;

public class MoveToAI extends AI {
    private BlockPos targetPos;
    private BlockPos creaturePos;
    private BlockPos tempTarget;
    private ArrayList<BlockPos> path;

    public MoveToAI(Creature creature, BlockPos targetPos) {
        this.targetPos = targetPos;
        this.creaturePos = creature.getPos();
        tempTarget = creaturePos;
        if (targetPos != null) {
            updatePath(creature);
        }
    }

    public void setTargetPos(BlockPos targetPos, Creature creature) {
        this.targetPos = targetPos.intForm();
        updatePath(creature);
    }

    @Override
    public boolean canExecute(Creature creature) {
        if (!creature.getPos().equals(targetPos) || !(path.size() == 0)) {
            return true;
        } else {
            creature.setXMove(0);
            creature.setYMove(0);
            return false;
        }
    }

    @Override
    public void execute(Creature creature) {
        if (path == null) {
            updatePath(creature);
        }
        if ((tempTarget == null || tempTarget.intForm().equals(creaturePos.intForm())) && path.size() > 0) {
            PixelPos movePos = path.get(0).toPixelPos();
            double x = (movePos.getXPixel() - creature.getPixelPos().getXPixel());
            double y = (movePos.getYPixel() - creature.getPixelPos().getYPixel());
            double total = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            PixelPos move = new PixelPos((float) ((x / total) * creature.getSpeed()), (float) ((y / total) * creature.getSpeed()));
            creature.setXMove(move.getXPixel());
            creature.setYMove(move.getYPixel());
            tempTarget = movePos.toBlockPos();
            path.remove(tempTarget);
        }
        creaturePos = creature.getPos();
    }

    private void updatePath(Creature creature) {
        tempTarget = creaturePos;
        ArrayList<BlockPos> list = new PathFinder(creature.getHandler(), creature, targetPos).tryGetPath();
        if (list.size() > 0) list.remove(0);
        path = list;
    }
}
