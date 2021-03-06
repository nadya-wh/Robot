package com.polovtseva.robot_executor.util;

import com.polovtseva.robot_executor.view.ImageWorker;
import com.polovtseva.robot_executor.view.MainFieldFrame;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by User on 30.11.2015.
 */
public class ResizeAdapter extends ComponentAdapter {

    private MainFieldFrame frame;

    public ResizeAdapter(MainFieldFrame frame) {
        this.frame = frame;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        frame.resetPanes();
        frame.refreshTable();
        frame.setLabel(ImageWorker.scaleRobotImage(frame.getRobotImage(), frame.getColumnWidth(), frame.getRowHeight()));
        frame.revalidate();
    }
}
