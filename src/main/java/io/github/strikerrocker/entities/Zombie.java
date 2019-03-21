package main.java.io.github.strikerrocker.entities;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.entities.ai.AttackAI;
import main.java.io.github.strikerrocker.entities.ai.FollowPlayerAI;
import main.java.io.github.strikerrocker.gfx.Animation;
import main.java.io.github.strikerrocker.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Creature {

    private Animation down;
    private Animation up;
    private Animation right;
    private Animation left;

    public Zombie(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        down = new Animation(500, Assets.zombie_down);
        up = new Animation(500, Assets.zombie_up);
        right = new Animation(500, Assets.zombie_right);
        left = new Animation(500, Assets.zombie_left);
        setHealth(10);
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
            return Assets.zombie_down[0];
        }
    }

    @Override
    protected void initAITasks() {
        aiTasks.put(new AttackAI(5, 1), 0);
        aiTasks.put(new FollowPlayerAI(this), 1);
    }

    @Override
    public void render(Graphics graphics) {
        super.render(graphics);
        /**graphics.setColor(Color.RED);
         Rectangle visibleArea = new Rectangle((int) (getBounds().x + getX()), (int) (getBounds().y + getY()), getBounds().width, getBounds().height);
         int factor = 10;
         visibleArea.grow(factor, factor);
         graphics.fillRect((int) (visibleArea.getX() - handler.getGameCamera().getXOffset()), (int) (visibleArea.getY() - handler.getGameCamera().getYOffset()), visibleArea.width, visibleArea.height);**/
    }

    @Override
    public void onKilled() {
        System.out.println("You killed successfully");
    }
}
