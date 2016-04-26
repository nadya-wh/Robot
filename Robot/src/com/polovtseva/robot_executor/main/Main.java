package com.polovtseva.robot_executor.main;

import com.polovtseva.robot_executor.controller.Controller;
import org.apache.log4j.Logger;

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
