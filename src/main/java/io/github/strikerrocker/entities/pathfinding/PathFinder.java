package io.github.strikerrocker.entities.pathfinding;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.misc.Utils;
import io.github.strikerrocker.world.BlockPos;

import java.util.ArrayList;
import java.util.logging.Level;

public class PathFinder {
    private ArrayList<PathStep> openList, closedList;
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
            openList.sort((o1, o2) -> Integer.compare(o2.getF(), o1.getF()));
            PathStep currentStep = openList.get(0);
            closedList.add(currentStep);
            openList.remove(0);
            //If path found
            if (currentStep.getPos().equals(target)) {
                pathFound = true;
                getPathFromSteps(currentStep, posArrayList);
                break;
            }
            //If not found
            tryNewPath(currentStep);
        }
        while (!openList.isEmpty());
        if (!pathFound) {
            handler.getGame().getLogger().log(Level.INFO, "Path not found for " + entity);
        } //else handler.getGame().getLogger().log(Level.INFO, "Path found for entity");
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
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
    }

    private void tryNewPath(PathStep currentStep) {
        ArrayList<BlockPos> adjMovable = adjMovablePos(currentStep.getPos());
        for (BlockPos pos : adjMovable) {
            PathStep step = new PathStep(pos);
            if (isBlocked(pos)) continue;
            int moveCost = computeAdjMoveCost(currentStep, step);
            int index = openList.indexOf(step);
            if (index == -1) {
                step.setParent(currentStep);
                step.setG(currentStep.getG() + moveCost);
                step.setH(computeH(step.getPos(), target));
                insertInOpen(step);
            } else {
                step = openList.get(index);
                if ((currentStep.getG() + moveCost) < step.getG()) {
                    step.setG(currentStep.getG() + moveCost);
                    openList.remove(index);
                    insertInOpen(step);
                }
            }
        }
    }

    private void insertInOpen(PathStep step) {
        openList.stream().filter(pathStep -> step.getF() <= pathStep.getF()).findFirst().ifPresent(pathStep -> openList.add(openList.indexOf(pathStep), step));
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
        return entity.hasEntityCollisionExceptPlayer(0, 0) || Utils.hasCollision(handler, pos);
    }

    private boolean isBlocked(BlockPos pos) {
        for (PathStep step : closedList) {
            if (step.getPos().equals(pos)) {
                return true;
            }
        }
        return false;
    }
}