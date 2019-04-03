package io.github.strikerrocker.states;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.ui.UIButton;
import io.github.strikerrocker.ui.UIManager;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MenuState extends State {
    private static File worldsFolder = new File("run/worlds");
    private UIManager uiManager;
    private ArrayList<UIButton> worldButtons;


    public MenuState(Handler handler) {
        super(handler);
        uiManager = new UIManager();
        worldButtons = new ArrayList<>();
        handler.getGame().getMouseManager().setUIManager(uiManager);
        File[] listFiles = worldsFolder.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            File world = listFiles[i];
            if (world.getName().startsWith("world")) {
                UIButton worldButton = new UIButton(handler.getWidth() / 2 - 64, (i) * 64, 128, 64, world.getName(), () -> {
                    handler.getGame().getGameState().setWorldDirectory(world);
                    setCurrentState(handler.getGame().getGameState());
                    handler.getGame().getMouseManager().setUIManager(null);
                });
                worldButtons.add(worldButton);
                uiManager.add(worldButton);
            }
        }
    }

    @Override
    public void tick() {
        for (int i = 0; i < worldButtons.size(); i++) {
            UIButton button = worldButtons.get(i);
            button.resize(handler.getWidth() / 2 - 64, (i) * 64, 128, 64);
        }
        uiManager.tick();
    }

    @Override
    public void render(Graphics graphics) {
        uiManager.render(graphics);
    }
}
