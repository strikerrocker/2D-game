package io.github.strikerrocker.ui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UIImageButton extends UIBase {
    private BufferedImage[] frames;
    private ClickListener clickListener;

    public UIImageButton(float x, float y, int width, int height, BufferedImage[] frames, ClickListener listener) {
        super(x, y, width, height);
        this.frames = frames;
        this.clickListener = listener;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        if (hovering)
            graphics.drawImage(frames[1], (int) x, (int) y, width, height, null);
        else
            graphics.drawImage(frames[0], (int) x, (int) y, width, height, null);
    }

    @Override
    public void onClick() {
        clickListener.onClick();
    }
}
