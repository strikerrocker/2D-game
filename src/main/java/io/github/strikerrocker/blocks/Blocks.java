package io.github.strikerrocker.blocks;

import io.github.strikerrocker.gfx.Assets;

public class Blocks {
    public static Block grass;
    public static Block dirt;
    public static Block wall;

    public static void init() {
        grass = new Block(Assets.grass);
        dirt = new Block(Assets.dirt);
        wall = new SolidBlock(Assets.wall);
    }
}
