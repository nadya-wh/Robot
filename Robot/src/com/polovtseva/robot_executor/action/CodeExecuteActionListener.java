package com.polovtseva.robot_executor.action;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.entity.Robot;
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

    public CodeExecuteActionListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (e.getSource().equals(Controller.getInstance().getFrame().getExecuteCodeButton())) {
            //System.out.println(e.getSource());
            MainFieldFrame frame = Controller.getInstance().getFrame();
            if (!isExecuting) {
                EditorPaneAction.addAlertText(frame.getLogPane(),  "The program started.\n");
                Controller.getInstance().getField().setRobotColumn(0);
                Controller.getInstance().getField().setRobotRow(0);
                Controller.getInstance().getRobot().setDirection(Robot.Direction.SOUTH);
                Controller.getInstance().getRobot().setChangedDirection(true);
                Controller.getInstance().getFrame().refreshTable();
                isExecuting = true;
                interpreter = new Interpreter(frame.getCode(), Controller.getInstance().getRobot(), frame);
                frame.setCodePaneNotEditable();

            } else {
                if (e.getSource().equals(Controller.getInstance().getFrame().getExecuteCodeButton())) {
                    execute();
                } else {
                    executeAll();
                }
            }
            frame.refreshTable();
        }
//    else {
//            System.out.println("2");
//
//        }
//    }

    public void execute() {
        MainFieldFrame frame = Controller.getInstance().getFrame();
        try {
            if (!interpreter.execute()) {
                JOptionPane.showMessageDialog(frame, "The program has finished.");
                frame.stopExecutingCode();
            }
        } catch (CodeExecutionException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            frame.stopExecutingCode();
        } catch (IllegalArgumentException exc) {
            JOptionPane.showMessageDialog(frame, exc.getMessage());
            frame.stopExecutingCode();
        }
    }

    public void executeAll() {
        MainFieldFrame frame = Controller.getInstance().getFrame();
        try {
            interpreter.executeAll();
            JOptionPane.showMessageDialog(frame, "The program has finished.");
            frame.stopExecutingCode();
        } catch (CodeExecutionException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

