package io.github.strikerrocker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.strikerrocker.blocks.Blocks;
import io.github.strikerrocker.entities.Entity;
import io.github.strikerrocker.entities.EntityManager;
import io.github.strikerrocker.entities.player.Player;
import io.github.strikerrocker.entities.type.EntityTypes;
import io.github.strikerrocker.gfx.Assets;
import io.github.strikerrocker.gfx.Display;
import io.github.strikerrocker.gfx.GameCamera;
import io.github.strikerrocker.input.KeyManager;
import io.github.strikerrocker.input.MouseManager;
import io.github.strikerrocker.items.Items;
import io.github.strikerrocker.misc.Deserializers;
import io.github.strikerrocker.misc.GameData;
import io.github.strikerrocker.misc.Serializers;
import io.github.strikerrocker.states.GameState;
import io.github.strikerrocker.states.MenuState;
import io.github.strikerrocker.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Logger;

public class Game implements Runnable {
    private GameState gameState;
    private int fps = 0;
    private Logger logger;
    private String title;
    private int height;
    private int width;
    private Display display;
    private Thread thread;
    private boolean running = false;
    private KeyManager keyManager;
    private GameCamera camera;
    private MouseManager mouseManager;
    private boolean dev = false;
    private Gson gson;

    Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
        logger = Logger.getLogger("Game : ");
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getFps() {
        return fps;
    }

    public boolean isDev() {
        return dev;
    }

    void setDev() {
        this.dev = true;
    }

    public Logger getLogger() {
        return logger;
    }

    GameCamera getCamera() {
        return camera;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    KeyManager getKeyManager() {
        return keyManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public Gson getGson() {
        return gson;
    }

    private void init() {
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);

        Handler handler = new Handler(this);
        Assets.init(handler);
        camera = new GameCamera(handler, 0, 0);
        Blocks.init();
        Items.init();
        EntityTypes.init();
        gson = new GsonBuilder().disableHtmlEscaping()
                .excludeFieldsWithoutExposeAnnotation().serializeNulls()
                .registerTypeAdapter(Player.class, Deserializers.playerJsonDeserializer)
                .registerTypeAdapter(Player.class, Serializers.playerJsonSerialize)
                .registerTypeAdapter(EntityManager.class, Deserializers.managerJsonDeserializer)
                .registerTypeAdapter(Entity.class, Serializers.entityJsonSerializer)
                .create();
        gameState = new GameState(handler);
        State.setCurrentState(new MenuState(handler));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> GameData.save(gameState, gson)));
    }

    private void tick() {
        keyManager.tick();
        if (State.getCurrentState() != null) State.getCurrentState().tick();
    }

    private void render() {
        BufferStrategy bufferStrategy = display.getCanvas().getBufferStrategy();
        if (bufferStrategy == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        Graphics graphics = bufferStrategy.getDrawGraphics();
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
        int intendedFPS = 60;
        double tps = 1000000000 / intendedFPS;
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
