package io.github.strikerrocker.ui;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UIManager {
    private ArrayList<UIBase> objects;

    public UIManager() {
        objects = new ArrayList<>();
    }

    public void tick() {
        for (UIBase ui : objects)
            ui.tick();
    }

    public void render(Graphics graphics) {
        for (UIBase ui : objects)
            ui.render(graphics);
    }

    public void onMouseMove(MouseEvent e) {
        for (UIBase ui : objects)
            ui.onMouseMove(e);
    }

    public void onMouseRelease(MouseEvent e) {
        for (UIBase ui : objects)
            ui.onMouseRelease(e);
    }

    public void add(UIBase ui) {
        objects.add(ui);
    }

    public void remove(UIBase ui) {
        objects.remove(ui);
    }
}
