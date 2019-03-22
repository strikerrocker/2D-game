package io.github.strikerrocker;

import io.github.strikerrocker.blocks.Blocks;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Display;
import io.github.strikerrocker.gfx.GameCamera;
import io.github.strikerrocker.input.KeyManager;
import io.github.strikerrocker.input.MouseManager;
import io.github.strikerrocker.items.Items;
import io.github.strikerrocker.states.GameState;
import io.github.strikerrocker.states.MenuState;
import io.github.strikerrocker.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;

public class Game implements Runnable {
    public int fps = 0;
    public GameState gameState;
    private Logger logger;
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

        logger = Logger.getLogger("Game : ");
    }

    public Logger getLogger() {
        return logger;
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
        Blocks.init();
        Items.init();
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
                width = display.getFrame().getWidth();
                height = display.getFrame().getHeight();
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
