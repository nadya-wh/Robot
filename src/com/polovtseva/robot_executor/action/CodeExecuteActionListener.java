package com.polovtseva.robot_executor.action;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.exception.CodeExecutionException;
import com.polovtseva.robot_executor.view.MainFieldFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class CodeExecuteActionListener implements ActionListener {

    private boolean isExecuting = false;
    private Interpreter interpreter;

    public  CodeExecuteActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFieldFrame frame = Controller.getInstance().getFrame();
        if (!isExecuting) {
            frame.getLogArea().append("\nThe program started.");
            isExecuting = true;
            interpreter = new Interpreter(frame.getCode(), Controller.getInstance().getRobot(), frame);
            frame.setCodePaneNotEditable();
            execute();
        } else {
            execute();
        }
        frame.refreshTable();
    }

    public void execute() {
        MainFieldFrame frame = Controller.getInstance().getFrame();
        try {
            if (!interpreter.execute()) {
                JOptionPane.showMessageDialog(frame, "The program is finished.");
                frame.stopExecutingCode();
            }
        } catch (CodeExecutionException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
            frame.stopExecutingCode();
        } catch (IllegalArgumentException exc) {
            JOptionPane.showMessageDialog(frame, exc.getMessage());
            frame.stopExecutingCode();
        }
    }

    public boolean isExecuting() {
        return isExecuting;
    }

    public void setExecuting(boolean executing) {
        isExecuting = executing;
    }
}

