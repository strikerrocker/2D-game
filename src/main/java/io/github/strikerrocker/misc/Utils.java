package io.github.strikerrocker.misc;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    private Utils() {
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> unsortMap) {
        List<Map.Entry<K, V>> list = new LinkedList<>(unsortMap.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, HashMap::new));
    }

    public static String loadFilesAsString(File path) {
        StringBuilder builder = new StringBuilder();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line).append("\n");
                }
            }
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

    public static void copyFolder(File src, File dest) {
        try {
            if (src.isDirectory()) {
                if (!dest.exists()) {
                    dest.mkdir();
                }
                String[] files = src.list();

                for (String file : files) {
                    copyFolder(new File(src, file), new File(dest, file));
                }
            } else {
                try (InputStream in = new FileInputStream(src)) {
                    try (OutputStream out = new FileOutputStream(dest)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        //copy the file content in bytes
                        while ((length = in.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}