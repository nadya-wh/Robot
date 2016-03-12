package com.polovtseva.robot_executor.controller;

import com.polovtseva.robot_executor.action.CodeExecuteActionListener;
import com.polovtseva.robot_executor.entity.Field;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.view.ChooseFieldSizeFrame;
import com.polovtseva.robot_executor.view.MainFieldFrame;

/**
 * Created by User on 26.01.2016.
 */
public class Controller {
    private static Controller INSTANCE = new Controller();
    private Robot robot;
    private Field field;
    private CodeExecuteActionListener codeExecuteActionListener;
    private MainFieldFrame frame;

    private Controller() {

    }

    public static Controller getInstance() {
        return INSTANCE;
    }

    public Robot getRobot() {
        return robot;
    }

    public Field getField() {
        return field;
    }

    public MainFieldFrame getFrame() {
        return frame;
    }

    public CodeExecuteActionListener getCodeExecuteActionListener() {
        return codeExecuteActionListener;
    }

    public void setExecuting(boolean value) {
        codeExecuteActionListener.setExecuting(value);
    }

    public boolean isExecuting() {
        return codeExecuteActionListener.isExecuting();
    }

    public void init() {
        ChooseFieldSizeFrame frame = new ChooseFieldSizeFrame();
        frame.setModal(true);
        frame.setVisible(true);
        int rowCount = frame.getRowCount();
        int columnCount = frame.getColumnCount();
//        int rowCount = 5;
//        int columnCount = 5;
        if (rowCount > 0 && columnCount > 0) {
            field = new Field(rowCount, columnCount);
            robot = new Robot(field);
            codeExecuteActionListener = new CodeExecuteActionListener();
        }
    }
    public void start() {
        if (robot != null) {
            frame = new MainFieldFrame();
            frame.setVisible(true);
        }
    }
}
