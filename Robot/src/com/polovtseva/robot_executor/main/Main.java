package com.polovtseva.robot_executor.main;

import com.polovtseva.robot_executor.controller.Controller;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 15.12.2015.
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        controller.init();
        controller.start();
//        List<String> allMatches = new ArrayList<String>();
//        Matcher m = Pattern.compile("go")
//                .matcher("go;\nturn;\ngo;");
//        while (m.find()) {
//            allMatches.add(m.group());
//        }
//        System.out.println(allMatches);
    }
}
