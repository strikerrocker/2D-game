package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.ui.UIButton;
import io.github.strikerrocker.ui.UIManager;

import java.awt.*;
import java.io.File;

public class MenuState extends State {
    private UIManager uiManager;

    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getGame().getMouseManager().setUIManager(uiManager);
        /*uiManager.add(new UIImageButton(handler.getWidth() / 2 - 64, handler.getHeight() / 2 - 32, 128, 64, Assets.startBtn, () -> {
            handler.getGame().getMouseManager().setUIManager(null);
            setCurrentState(handler.getGame().gameState);
        }));*/
        File worldsFolder = new File("run/world");
        File[] listFiles = worldsFolder.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            File world = listFiles[i];
            if (world.getName().startsWith("world")) {
                uiManager.add(new UIButton(handler.getWidth() / 2 - 64, (i) * 64, 128, 64, world.getName(), () -> {
                    handler.getGame().gameState.setWorldDirectory(world);
                    setCurrentState(handler.getGame().gameState);
                }));
            }
        }
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
