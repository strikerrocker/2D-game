package io.github.strikerrocker.ui;

import java.awt.*;
import java.awt.event.MouseEvent;

public abstract class UIBase {
    protected float x;
    protected float y;
    protected int width;
    protected int height;
    boolean hovering = false;
    private Rectangle bounds;

    UIBase(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    public abstract void tick();

    public abstract void render(Graphics graphics);

    public abstract void onClick();

    public void onMouseMove(MouseEvent e) {
        hovering = bounds.contains(e.getX(), e.getY());
    }

    public void onMouseRelease(MouseEvent e) {
        if (hovering)
            onClick();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void resize(float x, float y, int width, int height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
}
