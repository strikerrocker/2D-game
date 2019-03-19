package main.java.io.github.strikerrocker;

public class Main {
    public static Game game;

    public static void main(String[] args) {
        game = new Game("test", 640, 480);
        game.start();
    }
}
