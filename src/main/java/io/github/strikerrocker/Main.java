package io.github.strikerrocker;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static Game game;

    public static void main(String[] args) {
        game = new Game("test", 640, 480);
        if (new ArrayList<>(Arrays.asList(args)).contains("-dev")) {
            game.setDev();
        }
        game.start();
    }
}