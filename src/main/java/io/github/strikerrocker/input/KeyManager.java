package io.github.strikerrocker.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    public boolean up, down, left, right;
    public boolean attackUp, attackDown, attackLeft, attackRight;
    private boolean[] keys, justPressed, cantPress;

    public KeyManager() {
        this.keys = new boolean[256];
        this.justPressed = new boolean[keys.length];
        this.cantPress = new boolean[keys.length];
    }

    public boolean keyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }

    public void tick() {
        for (int i = 0; i < keys.length; i++) {
            if (cantPress[i] && !keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if (!cantPress[i] && keys[i]) {
                justPressed[i] = true;
            }
        }
        up = keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];

        attackUp = keys[KeyEvent.VK_UP];
        attackDown = keys[KeyEvent.VK_DOWN];
        attackLeft = keys[KeyEvent.VK_LEFT];
        attackRight = keys[KeyEvent.VK_RIGHT];
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = false;
    }
}
