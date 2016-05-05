package com.polovtseva.robot_executor.command;

import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.HashMap;

/**
 * Command.
 */
public abstract class Command {

    protected CommandEnum commandEnum;

    public abstract CommandResult execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException;

    public CommandEnum getCommandEnum() {
        return commandEnum;
    }
}
