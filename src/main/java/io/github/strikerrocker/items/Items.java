package io.github.strikerrocker.items;

import io.github.strikerrocker.gfx.Assets;

public class Items {


    public static Item wood;
    public static Item rock;
    public static Item apple;

    public static void init() {
        wood = new Item(Assets.wood, "Wood", 0);
        rock = new Item(Assets.rock, "Rock", 1);
        //TODO create texture for apple and apple tree
        apple = new Item(Assets.rock, "Apple", 2);
    }
}
