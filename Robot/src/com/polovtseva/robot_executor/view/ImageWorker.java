package com.polovtseva.robot_executor.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Image Worker.
 */
public class ImageWorker {
    public static final String RUN_ICON_PATH = "/res/run_icon.png";
    public static final String STOP_ICON_PATH = "/res/stop.png";
    public static final String PLAY_ALL_ICON_PATH = "/res/run_icon_pnred_g.png";
    public static final String RESET_ICON_PATH = "/res/reset.png";
    public static final String APPLICATION_ICON = "/res/icon.png";
    public static final String[] ROBOT_IMAGES = {"/res/icon.png",
            "/res/icon_robot_north.png",
            "/res/icon_robot_west.png",
            "/res/icon_robot_east.png"};

    public static final int NORTH = 1;
    public static final int SOUTH = 0;
    public static final int WEST = 2;
    public static final int EAST = 3;

    public static JLabel scaleRobotImage(ImageIcon robotImage, int columnWidth, int rowHeight) {
        JLabel label = null;
        BufferedImage bi = new BufferedImage(robotImage.getIconWidth(), robotImage.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.createGraphics();
        if (columnWidth > 0 && rowHeight > 0) {
            g.drawImage(robotImage.getImage(), 0, 0, columnWidth, rowHeight, null);
            Image scaledRobotImage = robotImage.getImage().getScaledInstance(columnWidth, rowHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledRobot = new ImageIcon(scaledRobotImage);
            label = new JLabel(scaledRobot);
            return label;
        }
        return label;
    }
}
