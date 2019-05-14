package io.github.strikerrocker.gfx;

import javax.swing.*;
import java.awt.*;

public class Display {
    private JFrame frame;
    private String title;
    private int height;
    private int width;
    private Canvas canvas;

    public Display(String title, int width, int height) {
        this.title = title;
        this.height = height;
        this.width = width;
        createDisplay();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    private void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();
    }

    public JFrame getFrame() {
        return frame;
    }
}