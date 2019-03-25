package io.github.strikerrocker.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {
    public static BufferedImage player, dirt, grass, stone, tree, rock, wood, apple, appleTree;
    public static BufferedImage[] player_down = new BufferedImage[2];
    public static BufferedImage[] zombie_down = new BufferedImage[2];
    public static BufferedImage[] zombie_up = new BufferedImage[2];
    public static BufferedImage[] zombie_left = new BufferedImage[2];
    public static BufferedImage[] zombie_right = new BufferedImage[2];
    public static BufferedImage[] player_up = new BufferedImage[2];
    public static BufferedImage[] player_left = new BufferedImage[2];
    public static BufferedImage[] player_right = new BufferedImage[2];
    public static BufferedImage[] startBtn = new BufferedImage[2];
    public static BufferedImage inventoryScreen;
    public static Font font28;
    private static int width = 32;
    private static int height = 32;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
        font28 = loadFont("run/fonts/slkscr.ttf", 28);
        //Player textures
        player_down[0] = sheet.crop(width * 4, 0, width, height);
        player_down[1] = sheet.crop(width * 5, 0, width, height);
        player_up[0] = sheet.crop(width * 6, 0, width, height);
        player_up[1] = sheet.crop(width * 7, 0, width, height);
        player_right[0] = sheet.crop(width * 4, height, width, height);
        player_right[1] = sheet.crop(width * 5, height, width, height);
        player_left[0] = sheet.crop(width * 6, height, width, height);
        player_left[1] = sheet.crop(width * 7, height, width, height);
        player = sheet.crop(width * 4, 0, width, height);
        //Zombie
        zombie_down[0] = sheet.crop(width * 4, height * 2, width, height);
        zombie_down[1] = sheet.crop(width * 5, height * 2, width, height);
        zombie_up[0] = sheet.crop(width * 6, height * 2, width, height);
        zombie_up[1] = sheet.crop(width * 7, height * 2, width, height);
        zombie_right[0] = sheet.crop(width * 4, height * 3, width, height);
        zombie_right[1] = sheet.crop(width * 5, height * 3, width, height);
        zombie_left[0] = sheet.crop(width * 6, height * 3, width, height);
        zombie_left[1] = sheet.crop(width * 7, height * 3, width, height);

        //Blocks Texture
        dirt = sheet.crop(width, 0, width, height);
        grass = sheet.crop(width * 2, 0, width, height);
        stone = sheet.crop(width * 3, 0, width, height);
        wood = sheet.crop(width, height, width, height);

        //Entities Texture
        tree = sheet.crop(0, 0, width, height * 2);
        rock = sheet.crop(0, height * 2, width, height);
        appleTree = sheet.crop(0, height * 3, width, height * 2);

        //Button Texture
        startBtn[0] = sheet.crop(width * 6, height * 4, width * 2, height);
        startBtn[1] = sheet.crop(width * 6, height * 5, width * 2, height);

        //Items
        apple = sheet.crop(width * 2, height, width, height);

        inventoryScreen = ImageLoader.loadImage("/textures/inventoryScreen.png");
    }

    private static Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }
}
