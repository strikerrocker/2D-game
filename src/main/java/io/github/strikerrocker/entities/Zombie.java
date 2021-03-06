package io.github.strikerrocker.entities;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.entities.ai.AttackAI;
import io.github.strikerrocker.entities.ai.FollowPlayerAI;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Animation;
import io.github.strikerrocker.gfx.Assets;

import java.awt.image.BufferedImage;

public class Zombie extends Creature {

    private Animation down;
    private Animation up;
    private Animation right;
    private Animation left;

    public Zombie(Handler handler, float x, float y) {
        super(EntityTypes.zombie, handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_HEALTH);
        down = new Animation(500, Assets.zombieDown);
        up = new Animation(500, Assets.zombieUp);
        right = new Animation(500, Assets.zombieRight);
        left = new Animation(500, Assets.zombieLeft);
    }

    @Override
    public BufferedImage getCurrentFrame() {
        if (xMove < 0) {
            return left.getCurrentFrame();
        } else if (xMove > 0) {
            return right.getCurrentFrame();
        } else if (yMove < 0) {
            return up.getCurrentFrame();
        } else if (yMove > 0) {
            return down.getCurrentFrame();
        } else {
            return Assets.zombieDown[0];
        }
    }

    @Override
    protected void initAITasks() {
        aiTasks.put(new AttackAI(1, 1), 0);
//        aiTasks.put(new MoveToAI(new BlockPos(3, 4)), 1);
        aiTasks.put(new FollowPlayerAI(), 1);
    }

    @Override
    public boolean isHostile() {
        return true;
    }

    @Override
    public void onKilled() {
        System.out.println("You killed a Zombie");
    }

    @Override
    public boolean canMove() {
        return true;
    }
}