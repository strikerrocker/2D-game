package main.java.io.github.strikerrocker.pathfinding;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.entities.Entity;
import main.java.io.github.strikerrocker.gfx.PixelPos;
import main.java.io.github.strikerrocker.world.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;

public class PathFinder {
    private ArrayList<PathStep> openList, closedList;
    private BlockPos start, target;
    private Handler handler;
    private Entity entity;

    public PathFinder(Handler handler, Entity entity, BlockPos target) {
        this.handler = handler;
        this.start = new PixelPos(entity.getX(), entity.getY()).toBlockPos();
        this.target = target;
        this.entity = entity;
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
    }

    private static int computeH(BlockPos start, BlockPos end) {
        return (int) (Math.abs(end.getX() - start.getX()) + Math.abs(end.getY() - start.getY()));
    }

    private static int computeAdjMoveCost(PathStep start, PathStep end) {
        return 1;
    }

    public ArrayList<BlockPos> getPath() {
        boolean pathFound = false;
        ArrayList<BlockPos> posArrayList = new ArrayList<>();
        openList.add(new PathStep(start));
        do {
            openList.sort(Comparator.comparingInt(PathStep::getF));
            PathStep currentStep = openList.get(0);
            closedList.add(currentStep);
            openList.remove(0);
            if (currentStep.getPos().equals(target)) {
                pathFound = true;
                PathStep tmpStep = currentStep;
                do {
                    posArrayList.add(tmpStep.getPos());
                    tmpStep = tmpStep.getParent();
                } while (tmpStep != null);
                System.out.println(posArrayList);
                openList = new ArrayList<>();
                closedList = new ArrayList<>();
                break;
            }
            ArrayList<BlockPos> adjMovable = adjWalkableTile(currentStep.getPos());
            for (BlockPos pos : adjMovable) {
                PathStep step = new PathStep(pos);
                int moveCost = 1;// computeAdjMoveCost(currentStep, step);
                int index = openList.indexOf(step);
                if (index == -1) {
                    step.setParent(currentStep);
                    step.setG(currentStep.getG() + moveCost);
                    step.setH(computeH(step.getPos(), target));
                    insertIntoOpen(step);
                } else {
                    step = openList.get(index);
                    if ((currentStep.getG() + moveCost) < step.getG()) {
                        step.setG(currentStep.getG() + moveCost);
                        openList.remove(index);
                        insertIntoOpen(step);
                    }
                }
            }
            openList.size();
            closedList.size();
        } while (!openList.isEmpty());
        if (!pathFound) {
            System.out.println("Path not found");
        } else System.out.println("found");
        return posArrayList;
    }

    private void insertIntoOpen(PathStep step) {
        int fScore = step.getF();
        int i = 0;
        for (; i < openList.size(); i++) {
            if (fScore <= openList.get(i).getF()) {
                return;
            }
        }
        openList.add(i, step);
    }

    private ArrayList<BlockPos> adjWalkableTile(BlockPos pos) {
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
        return entity.entityColliding(0, 0) || handler.getWorld().getBlock(pos.getX(), pos.getY()).isSolid();
    }
}