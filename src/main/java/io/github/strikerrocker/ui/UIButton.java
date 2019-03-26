package io.github.strikerrocker.ui;

import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;

import java.awt.*;

public class UIButton extends UIBase {
    protected ClickListener clickListener;
    protected String text;

    public UIButton(float x, float y, int width, int height, String text, ClickListener listener) {
        super(x, y, width, height);
        this.text = text;
        this.clickListener = listener;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.button, (int) x, (int) y, width, height, null);
        Text.drawString(graphics, text, (int) (x + width / 2), (int) (y + height / 2), true, Color.WHITE, Assets.font28);
    }

    @Override
    public void onClick() {
        clickListener.onClick();
    }
}
