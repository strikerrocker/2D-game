package main.java.io.github.strikerrocker.states;

import main.java.io.github.strikerrocker.Handler;
import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.ui.UIImageButton;
import main.java.io.github.strikerrocker.ui.UIManager;

import java.awt.*;

public class MenuState extends State {
    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getGame().getMouseManager().setUIManager(uiManager);

        uiManager.add(new UIImageButton(handler.getWidth() / 2 - 64, handler.getHeight() / 2 - 32, 128, 64, Assets.startBtn, () -> {
            handler.getGame().getMouseManager().setUIManager(null);
            State.setCurrentState(handler.getGame().gameState);
        }));
    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics graphics) {
        uiManager.render(graphics);
    }
}
