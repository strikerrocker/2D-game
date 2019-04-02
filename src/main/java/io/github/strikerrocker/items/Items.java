package io.github.strikerrocker.items;

import io.github.strikerrocker.blocks.Blocks;
import io.github.strikerrocker.gfx.Assets;

public class Items {

    public static ItemData wood;
    public static ItemData rock;
    public static ItemData apple;
    public static ItemData grass;
    public static ItemData pick;
    public static ItemData sword;

    public static void init() {
        wood = new ItemData(Assets.wood, "Wood");
        rock = new ItemData(Assets.rock, "Rock");
        apple = new ItemData(Assets.apple, "Apple").setAsFood(2);
        grass = new ItemData(Assets.grass, "grass").setAsItemBlock(Blocks.grass);
        pick = new ItemData(Assets.rock, "Pick").setAsPickaxe();
        sword = new ItemData(Assets.rock, "Sword").setAttackDamage(4);
    }
}
