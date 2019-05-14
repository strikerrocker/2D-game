package io.github.strikerrocker.gfx;

import io.github.strikerrocker.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Assets {
    public static BufferedImage player;
    public static BufferedImage dirt;
    public static BufferedImage grass;
    public static BufferedImage wall;
    public static BufferedImage tree;
    public static BufferedImage rock;
    public static BufferedImage wood;
    public static BufferedImage apple;
    public static BufferedImage gun;
    public static BufferedImage appleTree;
    public static BufferedImage portal;
    public static BufferedImage bullet;
    public static BufferedImage invertedBullet;
    public static BufferedImage button;
    public static BufferedImage inventoryScreen;
    public static BufferedImage hotBar;
    public static BufferedImage[] playerDown = new BufferedImage[2];
    public static BufferedImage[] zombieDown = new BufferedImage[2];
    public static BufferedImage[] zombieUp = new BufferedImage[2];
    public static BufferedImage[] zombieLeft = new BufferedImage[2];
    public static BufferedImage[] zombieRight = new BufferedImage[2];
    public static BufferedImage[] playerUp = new BufferedImage[2];
    public static BufferedImage[] playerLeft = new BufferedImage[2];
    public static BufferedImage[] playerRight = new BufferedImage[2];
    public static BufferedImage[] startButtton = new BufferedImage[2];
    public static Font font28;

    private Assets() {
    }

    public static void init(Handler handler) {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png", handler));
        font28 = loadFont(Assets.class.getResourceAsStream("/fonts/slkscr.ttf"), 28);
        int width = 32;
        int height = 32;
        //Entities Texture
        tree = sheet.crop(0, 0, width, height * 2);
        rock = sheet.crop(0, height * 2, width, height);
        appleTree = sheet.crop(0, height * 3, width, height * 2);
        portal = sheet.crop(width, height * 3, width, height * 2);
        bullet = sheet.crop(width, height * 2, width, height);
        invertedBullet = sheet.crop(width * 2, height * 3, width, height);
        //Player textures
        playerDown[0] = sheet.crop(width * 4, 0, width, height);
        playerDown[1] = sheet.crop(width * 5, 0, width, height);
        playerUp[0] = sheet.crop(width * 6, 0, width, height);
        playerUp[1] = sheet.crop(width * 7, 0, width, height);
        playerRight[0] = sheet.crop(width * 4, height, width, height);
        playerRight[1] = sheet.crop(width * 5, height, width, height);
        playerLeft[0] = sheet.crop(width * 6, height, width, height);
        playerLeft[1] = sheet.crop(width * 7, height, width, height);
        player = sheet.crop(width * 4, 0, width, height);
        //Zombie
        zombieDown[0] = sheet.crop(width * 4, height * 2, width, height);
        zombieDown[1] = sheet.crop(width * 5, height * 2, width, height);
        zombieUp[0] = sheet.crop(width * 6, height * 2, width, height);
        zombieUp[1] = sheet.crop(width * 7, height * 2, width, height);
        zombieRight[0] = sheet.crop(width * 4, height * 3, width, height);
        zombieRight[1] = sheet.crop(width * 5, height * 3, width, height);
        zombieLeft[0] = sheet.crop(width * 6, height * 3, width, height);
        zombieLeft[1] = sheet.crop(width * 7, height * 3, width, height);

        //Blocks Texture
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width * 2, 0, width, height);
        wall = sheet.crop(width * 3, 0, width, height);

        //Button Texture
        startButtton[0] = sheet.crop(width * 6, height * 4, width * 2, height);
        startButtton[1] = sheet.crop(width * 6, height * 5, width * 2, height);
        button = sheet.crop(width * 4, height * 4, width * 2, height);

        //Items
        apple = sheet.crop(width * 2, height, width, height);
        wood = sheet.crop(width, height, width, height);
        gun = sheet.crop(width * 2, height * 2, width, height);

        inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png", handler);
        hotBar = sheet.crop(width * 3, height, width, height);

    }

    private static Font loadFont(InputStream inputStream, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
