package io.github.strikerrocker.items;

import io.github.strikerrocker.gfx.Assets;

public class Items {


    public static ItemData wood;
    public static ItemData rock;
    public static ItemData apple;

    public static void init() {
        wood = new ItemData(Assets.wood, "Wood", 0);
        rock = new ItemData(Assets.rock, "Rock", 1);
        apple = new ItemData(Assets.apple, "Apple", 2).setAsFood(2);
    }
}
