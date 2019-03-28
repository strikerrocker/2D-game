package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.ui.UIImageButton;
import io.github.strikerrocker.ui.UIManager;

import java.awt.*;

public class DeathScreen extends State {
    private UIManager uiManager;

    public DeathScreen(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getGame().getMouseManager().setUIManager(uiManager);

        uiManager.add(new UIImageButton(handler.getWidth() / 2 - 64, handler.getHeight() / 2 - 32, 128, 64, Assets.startBtn, () -> {
            handler.getGame().getMouseManager().setUIManager(null);
            setCurrentState(handler.getGame().getGameState());
        }));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {

    }
}
