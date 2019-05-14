package io.github.strikerrocker.entities.pathfinding;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.world.BlockPos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PathFinder {
    private ArrayList<PathStep> openList;
    private ArrayList<BlockPos> closedList;
    private BlockPos start;
    private BlockPos target;
    private Handler handler;
    private Entity entity;

    public PathFinder(BlockPos start, BlockPos target) {
        this.start = start.intForm();
        this.target = target.intForm();
        openList = new ArrayList<PathStep>() {

            @Override
            public void add(int index, PathStep element) {
                add(element);
            }

            @Override
            public boolean add(PathStep pathStep) {
                super.add(pathStep);
                this.sort(Comparator.comparingInt(PathStep::getF));
                return true;
            }
        };
        closedList = new ArrayList<>();
    }

    private static int computeH(BlockPos start, BlockPos end) {
        return (int) (Math.abs(end.getX() - start.getX()) + Math.abs(end.getY() - start.getY()));
    }

    private static int computeAdjMoveCost(PathStep start, PathStep end) {
        return 1;
    }

    public PathFinder setHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public PathFinder setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public List<BlockPos> tryGetPath() {
        ArrayList<BlockPos> posArrayList = new ArrayList<>();
        PathStep startStep = new PathStep(start);
        openList.add(startStep);
        long lastTimer = 0;
        long cooldown = 1000;
        long timer = cooldown;
        do {
            timer += System.currentTimeMillis() - lastTimer;
            lastTimer = System.currentTimeMillis();
            PathStep currentStep = openList.get(0);
            closedList.add(currentStep.getPos());
            openList.remove(0);
            //If path found
            if (currentStep.getPos().equals(target)) {
                getPathFromSteps(currentStep, posArrayList);
                break;
            }
            tryNewPath(currentStep);
        } while (!openList.isEmpty() || timer < cooldown);
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
            if (closedList.contains(pos)) continue;
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
        adj = pos.left();
        if (isValidPathStep(adj)) {
            arrayList.add(adj);
        }
        adj = pos.right();
        if (isValidPathStep(adj)) {
            arrayList.add(adj);
        }
        adj = pos.up();
        if (isValidPathStep(adj)) {
            arrayList.add(adj);
        }
        adj = pos.down();
        if (isValidPathStep(adj)) {
            arrayList.add(adj);
        }

        return arrayList;
    }

    private boolean hasCollision(BlockPos pos) {
        return entity.hasEntityCollision(0, 0, entity.getHandler().getCurrentLevel().getEntityManager().getPlayer())
                || handler.getCurrentLevel().getBlock(pos.getX(), pos.getY()).isSolid();
    }

    private boolean isValidPathStep(BlockPos pos) {
        if (entity == null || handler == null) return true;
        return !hasCollision(pos) && pos.getX() >= 0 && pos.getX() <= handler.getCurrentLevel().getWorldWidth() &&
                pos.getY() >= 0 && pos.getY() <= handler.getCurrentLevel().getWorldHeight();
    }
}