package io.github.strikerrocker.items;

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
        pick = new ItemData(Assets.rock, "Pick").setAsPick();
        sword = new ItemData(Assets.rock, "Sword").setAttackDamage(4);
    }
}
