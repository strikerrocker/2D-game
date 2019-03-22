package io.github.strikerrocker;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Utils {
    public static String loadFilesAsFile(String path) {
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

    public static <K, V extends Comparable> Map<K, V> sortByValue(Map<K, V> unsortMap) {
        CustomComparator comparator = new CustomComparator(unsortMap);
        Map<K, V> map = new TreeMap<>(comparator);
        map.putAll(unsortMap);
        return map;
    }

    private static class CustomComparator<K, V extends Comparable> implements Comparator<K> {
        private Map<K, V> map;

        CustomComparator(Map<K, V> map) {
            this.map = new HashMap<>(map);
        }

        @Override
        public int compare(K s1, K s2) {
            return map.get(s1).compareTo(map.get(s2));
        }
    }
}