package main.java.io.github.strikerrocker.states;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.Main;
import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.gfx.Text;
import main.java.io.github.strikerrocker.world.World;

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
        Text.drawString(graphics, "Health : " + State.getCurrentState().handler.getWorld().getEntityManager().getPlayer().getHealth(), 0, 40, false, Color.WHITE, Assets.font28);
    }
}
