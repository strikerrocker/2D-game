package io.github.strikerrocker.blocks;

import io.github.strikerrocker.gfx.Assets;

public class Blocks {
    public static Block grass;
    public static Block dirt;
    public static Block rock;

    public static void init() {
        grass = new Block(Assets.grass, 0);
        dirt = new Block(Assets.dirt, 1);
        rock = new SolidBlock(Assets.stone, 2);
    }
}
