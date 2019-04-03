package io.github.strikerrocker.misc;

import io.github.strikerrocker.Handler;
import io.github.strikerrocker.world.BlockPos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {
        List<Map.Entry<K, V>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
    }

    public static String loadFilesAsString(File path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static BufferedImage tintRed(BufferedImage image) {
        BufferedImage tinted = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color original = new Color(image.getRGB(x, y), true);
                if (original.getAlpha() != 0) {
                    tinted.setRGB(x, y, new Color(255, original.getGreen(), original.getBlue(), 255).getRGB());
                }
            }
        }
        return tinted;
    }

    public static boolean hasCollision(Handler handler, BlockPos pos) {
        return handler.getCurrentLevel().getBlock(pos.getX(), pos.getY()).isSolid();
    }
}