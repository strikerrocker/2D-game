package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.Main;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;
import io.github.strikerrocker.world.World;

import java.awt.*;

public class GameState extends State {
    private World world;

    public GameState(Handler handler) {
        super(handler);
        world = new World(handler, "run/worlds/world1.txt");
    }

    @Override
    public void tick() {
        world.tick();
    }

    @Override
    public void render(Graphics graphics) {
        world.render(graphics);
        Text.drawString(graphics, "FPS : " + Main.game.fps, 0, 20, false, Color.WHITE, Assets.font28);
        Text.drawString(graphics, "Health : " + getCurrentState().handler.getWorld().getEntityManager().getPlayer().getHealth(), 0, 40, false, Color.WHITE, Assets.font28);
    }
}
