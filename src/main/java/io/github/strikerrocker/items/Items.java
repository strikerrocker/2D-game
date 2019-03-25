package io.github.strikerrocker.items;

import io.github.strikerrocker.gfx.Assets;

public class Items {


    public static Item wood;
    public static Item rock;
    public static Item apple;

    public static void init() {
        wood = new Item(Assets.wood, "Wood", 0);
        rock = new Item(Assets.rock, "Rock", 1);
        apple = new Item(Assets.apple, "Apple", 2);
    }
}
