package com.polovtseva.robot_executor.main;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.view.ChooseFieldSizeFrame;
import org.apache.log4j.Logger;

import javax.swing.*;

/**
 * Created by User on 15.12.2015.
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.init();
        controller.start();
    }
}
