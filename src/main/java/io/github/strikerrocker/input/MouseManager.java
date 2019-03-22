package io.github.strikerrocker.input;

import io.github.strikerrocker.ui.UIManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseManager implements MouseListener, MouseMotionListener {
    private boolean left, right;
    private int x, y;
    private UIManager uiManager;

    public void setUIManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            right = true;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            left = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            right = false;
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            left = false;
        }
        if (uiManager != null) {
            uiManager.onMouseRelease(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    public boolean isLeftPressed() {
        return left;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isRightPressed() {
        return right;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        if (uiManager != null) {
            uiManager.onMouseMove(e);
        }
    }
}
