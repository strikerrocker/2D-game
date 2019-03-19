package main.java.io.github.strikerrocker;

import main.java.io.github.strikerrocker.gfx.Assets;
import main.java.io.github.strikerrocker.gfx.Display;
import main.java.io.github.strikerrocker.gfx.GameCamera;
import main.java.io.github.strikerrocker.input.KeyManager;
import main.java.io.github.strikerrocker.input.MouseManager;
import main.java.io.github.strikerrocker.states.GameState;
import main.java.io.github.strikerrocker.states.MenuState;
import main.java.io.github.strikerrocker.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    public int fps = 0;
    public State gameState;
    private String title;
    private int height, width;
    private Display display;
    private Thread thread;
    private boolean running = false;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private State menuState;
    private KeyManager keyManager;
    private GameCamera camera;
    private Handler handler;
    private MouseManager mouseManager;

    Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }

    public GameCamera getCamera() {
        return camera;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        Assets.init();

        handler = new Handler(this);
        camera = new GameCamera(handler, 0, 0);
        menuState = new MenuState(handler);
        gameState = new GameState(handler);
        //State.setCurrentState(menuState);
        State.setCurrentState(gameState);
    }

    private void tick() {
        keyManager.tick();
        if (State.getCurrentState() != null) State.getCurrentState().tick();
    }

    private void render() {
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        graphics = bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);
        if (State.getCurrentState() != null) {
            State.getCurrentState().render(graphics);
        }
        bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void run() {
        init();
        int fps = 60;
        double tps = 1000000000 / fps;
        double delta = 0;
        double now;
        double lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / tps;
            timer += now - lastTime;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                this.fps = ticks;
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
