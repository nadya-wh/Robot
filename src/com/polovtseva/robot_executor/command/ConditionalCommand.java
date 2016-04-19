package com.polovtseva.robot_executor.command;

import com.polovtseva.robot_executor.action.ExpressionAction;
import com.polovtseva.robot_executor.entity.Robot;
import com.polovtseva.robot_executor.exception.CodeExecutionException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 27.01.2016.
 */
public class ConditionalCommand implements Command {
    private ExpressionCommand expressionCommand;
    private ArrayList<Command> commands;
    private CommandType commandType;

    public ConditionalCommand(CommandType commandType) {
        this.commandType = commandType;
        commands = new ArrayList<>();
    }

    public ConditionalCommand(ExpressionCommand expressionCommand, ArrayList<Command> commands, CommandType commandType) {
        this.expressionCommand = expressionCommand;
        this.commands = commands;
        this.commandType = commandType;
    }

    public ExpressionCommand getExpressionCommand() {
        return expressionCommand;
    }

    public void setExpressionCommand(ExpressionCommand expressionCommand) {
        this.expressionCommand = expressionCommand;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public CommandResult execute(HashMap<Integer, Integer> ids, Robot robot) throws CodeExecutionException {
        CommandResult result = CommandResult.FALSE;
        switch (getCommandType()) {
            case IF:
                if (ExpressionAction.countBooleanExpression(getExpressionCommand(), ids)) {
                    if (getCommands() != null) {
                        for (Command command : getCommands()) {
                            result = command.execute(ids, robot);
                        }
                    }
                }
                break;
            case WHILE:
                while (ExpressionAction.countBooleanExpression(getExpressionCommand(), ids)) {
                    for (Command command : getCommands()) {
                        result = command.execute(ids, robot);
                    }
                }
                break;
            default:
                throw new CodeExecutionException("Unsupported operation: " + getCommandType());
        }
        return result;
    }
}
