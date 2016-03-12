package com.polovtseva.robot_executor.logic;

import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.HashMap;

/**
 * Created by User on 27.01.2016.
 */
public interface Command {
    boolean execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException;
}
