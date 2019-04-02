package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.ui.UIButton;
import io.github.strikerrocker.ui.UIManager;

import java.awt.*;

public class DeathState extends State {
    private UIManager uiManager;
    private UIButton menu;

    public DeathState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getGame().getMouseManager().setUIManager(uiManager);
        menu = new UIButton(handler.getWidth() / 2 - 64, handler.getWidth() / 2 - 64, 128, 64, "MENU", () -> {
            setCurrentState(new MenuState(handler));
            handler.getGame().getMouseManager().setUIManager(null);
        });
        uiManager.add(menu);
    }

    @Override
    public void tick() {
        menu.resize(handler.getWidth() / 2 - 64, handler.getHeight() / 2 - 128, 128, 64);
        uiManager.tick();
    }

    @Override
    public void render(Graphics graphics) {
        uiManager.render(graphics);
    }
}