package io.github.strikerrocker.ui;

import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Text;

import java.awt.*;

public class UIButton extends UIBase {
    private ClickListener clickListener;
    private String text;
    private Color textColor;

    public UIButton(float x, float y, int width, int height, String text, ClickListener listener) {
        this(x, y, width, height, text, listener, Color.WHITE);
    }

    public UIButton(float x, float y, int width, int height, String text, ClickListener listener, Color textColor) {
        super(x, y, width, height);
        this.text = text;
        this.clickListener = listener;
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.button, (int) x, (int) y, width, height, null);
        Text.drawString(graphics, text, (int) (x + width / 2), (int) (y + height / 2), true, textColor, Assets.font28);
    }

    @Override
    public void onClick() {
        clickListener.onClick();
    }
}