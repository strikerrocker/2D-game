package io.github.strikerrocker.entities.pathfinding;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.world.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;

public class PathFinder {
    private ArrayList<PathStep> openList;
    private ArrayList<BlockPos> closedList;
    private BlockPos start, target;
    private Handler handler;
    private Entity entity;

    public PathFinder(Handler handler, Entity entity, BlockPos target) {
        this.handler = handler;
        this.start = entity.getPos().intForm();
        this.target = target;
        this.entity = entity;
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
    }

    private static int computeH(BlockPos start, BlockPos end) {
        return (int) (Math.abs(end.getX() - start.getX()) + Math.abs(end.getY() - start.getY()));
    }

    private static int computeAdjMoveCost(PathStep start, PathStep end) {
        return -1;
    }

    public ArrayList<BlockPos> tryGetPath() {
        boolean pathFound = false;
        ArrayList<BlockPos> posArrayList = new ArrayList<>();
        PathStep startStep = new PathStep(start);
        startStep.setG(1);
        startStep.setH(computeH(startStep.getPos(), target));
        openList.add(startStep);
        do {
            openList.sort(Comparator.comparingInt(PathStep::getF));
            PathStep currentStep = openList.get(0);
            closedList.add(currentStep.getPos());
            openList.remove(0);
            //If path found
            if (currentStep.getPos().equals(target)) {
                pathFound = true;
                getPathFromSteps(currentStep, posArrayList);
                break;
            }
            tryNewPath(currentStep);
        } while (openList.size() > 0);
        return posArrayList;
    }

    private void getPathFromSteps(PathStep step, ArrayList<BlockPos> posArrayList) {
        do {
            posArrayList.add(step.getPos());
            step = step.getParent();
        } while (step != null);
        for (int i = 0; i < posArrayList.size() / 2; i++) {
            BlockPos temp = posArrayList.get(i);
            posArrayList.set(i, posArrayList.get(posArrayList.size() - i - 1));
            posArrayList.set(posArrayList.size() - i - 1, temp);
        }
    }

    private void tryNewPath(PathStep currentStep) {
        ArrayList<BlockPos> adjMovable = adjMovablePos(currentStep.getPos());
        for (BlockPos pos : adjMovable) {
            if (isClosed(pos)) continue;
            PathStep step = new PathStep(pos);
            int moveCost = computeAdjMoveCost(currentStep, step);
            int index = openList.indexOf(step);
            if (index == -1) {
                step.setParent(currentStep);
                step.setG(currentStep.getG() + moveCost);
                step.setH(computeH(step.getPos(), target));
                openList.add(step);
            } else {
                step = openList.get(index);
                if ((currentStep.getG() + moveCost) < step.getG()) {
                    step.setG(currentStep.getG() + moveCost);
                    openList.remove(index);
                    openList.add(step);
                }
            }
        }
    }

    private ArrayList<BlockPos> adjMovablePos(BlockPos pos) {
        ArrayList<BlockPos> arrayList = new ArrayList<>();
        BlockPos adj;
        adj = new BlockPos(pos.getX(), pos.getY() - 1);
        if (!hasCollision(adj)) {
            arrayList.add(adj);
        }
        adj = new BlockPos(pos.getX() - 1, pos.getY());
        if (!hasCollision(adj)) {
            arrayList.add(adj);
        }
        adj = new BlockPos(pos.getX(), pos.getY() + 1);
        if (!hasCollision(adj)) {
            arrayList.add(adj);
        }
        adj = new BlockPos(pos.getX() + 1, pos.getY());
        if (!hasCollision(adj)) {
            arrayList.add(adj);
        }
        return arrayList;
    }

    private boolean hasCollision(BlockPos pos) {
        return entity.hasEntityCollision(0, 0, entity.getHandler().getCurrentLevel().getEntityManager().getPlayer()) || handler.getCurrentLevel().getBlock(pos.getX(), pos.getY()).isSolid();
    }

    private boolean isClosed(BlockPos pos) {
        return closedList.contains(pos);
    }
}