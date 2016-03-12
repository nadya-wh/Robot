package com.polovtseva.robot_executor.main;

import com.polovtseva.robot_executor.controller.Controller;
import com.polovtseva.robot_executor.view.ChooseFieldSizeFrame;

/**
 * Created by User on 15.12.2015.
 */
public class Main {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.init();
        controller.start();
    }
}
